package com.example.scrumer.csv.service;

import com.example.scrumer.csv.command.SelectedHeader;
import com.example.scrumer.csv.command.Value;
import com.example.scrumer.csv.model.ImportedData;
import com.example.scrumer.csv.model.Values;
import com.example.scrumer.csv.storage.ImportedDataStorage;
import com.example.scrumer.issue.command.ImportUserStoryCommand;
import com.example.scrumer.issue.entity.PriorityStatus;
import com.example.scrumer.issue.entity.StatusIssue;
import com.example.scrumer.issue.entity.UserStory;
import com.example.scrumer.issue.service.useCase.UserStoryUseCase;
import com.example.scrumer.project.service.useCase.ProjectUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ImportUserStoryService extends ImportCsvStrategy {
    private final UserStoryUseCase userStoryUseCase;

    @Override
    public List<String> getFields() {
        UserStory userStory = new UserStory();
        Class cls = userStory.getClass();

        Field[] fields = cls.getDeclaredFields();

        return Arrays.stream(fields).map(Field::getName).collect(Collectors.toList());
    }

    protected List<Value> preparationValue(ImportedData importedData) {

        List<Value> values = new ArrayList<>();

        for(SelectedHeader header: importedData.getSelectedHeaders()) {
            switch (header.getSuggestedField()) {
                case "priority" -> values.add(Value.builder()
                        .fields(header.getSuggestedField())
                        .uniqueValues(separateValue(importedData.getColumns().get(header.getReadField())))
                        .availableValue(Stream.of(PriorityStatus.values()).map(Enum::name).collect(Collectors.toList()))
                        .build());
                case "statusIssue" -> values.add(Value.builder()
                        .fields(header.getSuggestedField())
                        .uniqueValues(separateValue(importedData.getColumns().get(header.getReadField())))
                        .availableValue(Stream.of(StatusIssue.values()).map(Enum::name).collect(Collectors.toList()))
                        .build());
                case "team" -> values.add(Value.builder()
                        .fields(header.getSuggestedField())
                        .uniqueValues(separateValue(importedData.getColumns().get(header.getReadField())))
                        .availableValue(List.of())
                        .build());
            }
        }

        return values;
    }

    @Override
    public void create(String idData, Long id) {
        List<ImportUserStoryCommand> commands = prepareData(idData);

        userStoryUseCase.importUserStories(id, commands);

        ImportedDataStorage.getInstance().getStorage().remove(idData);
    }

    private List<ImportUserStoryCommand> prepareData(String idData) {
        ImportedData importedData = ImportedDataStorage.getInstance().getStorage().get(idData);

        List<ImportUserStoryCommand.ImportUserStoryCommandBuilder> commands = new ArrayList<>();

        int numberObjects = importedData.getColumns().get(importedData.getReadHeaders()[0]).size();

        for(int i = 0; i < numberObjects; i++) {
            commands.add(ImportUserStoryCommand.builder());
        }

        for (SelectedHeader selectedHeader: importedData.getSelectedHeaders()) {

            switch (selectedHeader.getSuggestedField()) {
                case "title" -> {
                    List<String> values = importedData.getColumns().get(selectedHeader.getReadField());
                    for(int i = 0; i < values.size(); i++) {
                        if (Objects.nonNull(values.get(i)) && !values.get(0).equals("null")) {
                            commands.get(i).title(values.get(i));
                        }
                    }
                }
                case "description" -> {
                    List<String> values = importedData.getColumns().get(selectedHeader.getReadField());
                    for(int i = 0; i < values.size(); i++) {
                        if (Objects.nonNull(values.get(i)) && !values.get(0).equals("null")) {
                            commands.get(i).description(values.get(i));
                        }
                    }
                }

                case "priority" -> {
                    Values preferences = importedData.getUserPreferences().get("priority");
                    List<String> values = importedData.getColumns().get(selectedHeader.getReadField());
                    for(int i = 0; i < values.size(); i++) {
                        if (Objects.nonNull(values.get(i)) && !values.get(0).equals("null")) {
                            commands.get(i).priority(PriorityStatus.valueOf(preferences.getValues().get(values.get(i))));
                        }
                    }
                }

                case "storyPoints" -> {
                    List<String> values = importedData.getColumns().get(selectedHeader.getReadField());
                    for(int i = 0; i < values.size(); i++) {
                        if (Objects.nonNull(values.get(i)) && !values.get(0).equals("null")) {
                            commands.get(i).storyPoints(Integer.valueOf(values.get(i)));
                        }
                    }
                }

                case "statusIssue" -> {
                    Values preferences = importedData.getUserPreferences().get("statusIssue");
                    List<String> values = importedData.getColumns().get(selectedHeader.getReadField());
                    for(int i = 0; i < values.size(); i++) {
                        if (Objects.nonNull(values.get(i)) && !values.get(0).equals("null")) {
                            commands.get(i).statusIssue(StatusIssue.valueOf(preferences.getValues().get(values.get(i))));
                        }
                    }
                }

                case "team" -> {
                    Values preferences = importedData.getUserPreferences().get("team");
                    List<String> values = importedData.getColumns().get(selectedHeader.getReadField());
                    for(int i = 0; i < values.size(); i++) {
                        if (Objects.nonNull(values.get(i)) && !values.get(0).equals("null")) {
                            commands.get(i).idTeam(Long.valueOf(preferences.getValues().get(values.get(i))));
                        }
                    }
                }
            }
        }

        List<ImportUserStoryCommand> importUserStoryCommands = new ArrayList<>();

        for(int i = 0; i < numberObjects; i++) {
            importUserStoryCommands.add(commands.get(i).build());
        }

        return importUserStoryCommands;
    }
}
