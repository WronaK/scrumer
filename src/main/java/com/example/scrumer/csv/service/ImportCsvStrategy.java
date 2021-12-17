package com.example.scrumer.csv.service;

import com.example.scrumer.csv.command.*;
import com.example.scrumer.csv.model.ImportOption;
import com.example.scrumer.csv.model.ImportedData;
import com.example.scrumer.csv.storage.ImportedDataStorage;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public abstract class ImportCsvStrategy {

    public List<String[]> parserFile(MultipartFile file) {

        List<String[]> result = new ArrayList<>();
        try {
            InputStream inputStream = file.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = bufferedReader.readLine()) != null) {

                String[] fileds = line.split("(,)(?=(?:[^\"]|\"[^\"]*\")*$)");


                for (int i = 0; i < fileds.length; i++) {
                    fileds[i] = fileds[i].replaceAll("^\"|\"$", "");
                }
                result.add(fileds);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


    public Map<String, List<String>> separateColumn(List<String[]> result) {
        Map<String, List<String>> columns = new HashMap<>();

        String[] headers = result.get(0);

        for (String header : headers) {
            columns.put(header, new ArrayList<String>());
        }

        for (int i = 1; i < result.size(); i++) {
            int j = 0;
            for (String field : result.get(i)) {
                columns.get(headers[j]).add(field);
                j++;
            }
        }

        return columns;
    }

    public abstract List<String> getFields();

    public ReadHeadersCommand importCsvFile(ImportOption option, MultipartFile file) {
        List<String[]> content = parserFile(file);

        ImportedData importedData = ImportedData.builder()
                .id(UUID.randomUUID().toString())
                .readContent(content)
                .readHeaders(content.get(0))
                .importOption(option)
                .columns(separateColumn(content))
                .selectedHeaders(new ArrayList<>())
                .userPreferences(new HashMap<>())
                .build();

        ImportedDataStorage.getInstance().setImportedData(importedData);
        List<String> fields = getFields();

        return new ReadHeadersCommand(importedData.getId(), createCommand(importedData.getReadHeaders(), fields), fields);
    }

    private List<HeaderCommand> createCommand(String[] headers, List<String> fields) {
        return Arrays.stream(headers).map(header -> new HeaderCommand(header, getSuggested(header, fields), false)).collect(Collectors.toList());
    }

    private String getSuggested(String name, List<String> fields) {
        for (String field : fields) {
            if (field.toLowerCase().contains(name.toLowerCase()) || name.toLowerCase().contains(field.toLowerCase())) {
                return field;
            }
        }
        return null;
    }

    public MyValue selectedFields(MapHeaderCommand selectedHeader) {
        ImportedData importedData = ImportedDataStorage.getInstance().getStorage().get(selectedHeader.getIdImportedData());

        importedData.getSelectedHeaders().addAll(selectedHeader.getSelectedHeaders());

        ImportedDataStorage.getInstance().setImportedData(importedData);

        MyValue mapValue = new MyValue();
        mapValue.setIdImportedData(selectedHeader.getIdImportedData());
        mapValue.setValues(preparationValue(importedData));

        return mapValue;
    }

    protected abstract List<Value> preparationValue(ImportedData importedData);

    protected Set<String> separateValue(List<String> values) {
        Set<String> uniqualValue = new HashSet<>();

        for(String value: values) {
            if (value.startsWith("[") && value.endsWith("]")) {
                value = value.substring(1, value.length() - 1);

                String[] separateValue = value.split("(,)(?=(?:[^\"]|\"[^\"]*\")*$)");

                for(String v: separateValue) {
                    uniqualValue.add(v);
                }
            } else if (!value.equals("")) {
                uniqualValue.add(value);
            }
        }

        return uniqualValue;
    }

    public void matchFields(ResultCommand resultCommand) {
        ImportedData importedData = ImportedDataStorage.getInstance().getStorage().get(resultCommand.getIdImportedData());

        importedData.addUserPreferences(resultCommand.getMapValue());

        ImportedDataStorage.getInstance().setImportedData(importedData);

    }

    public abstract void create(String idData, Long id);
}
