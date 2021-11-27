package com.example.scrumer.csv.service;

import com.example.scrumer.issue.entity.UserStory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CsvService {

    public String[] readData(MultipartFile file) {
        List<String[]> values = parserFile(file);
        return separateColumn(values);
    }

    public List<String[]> parserFile(MultipartFile file) {

        List<String[]> result  = new ArrayList<>();
        try {
            InputStream inputStream = file.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = bufferedReader.readLine()) != null) {

                String[] fileds = line.split("(,)(?=(?:[^\"]|\"[^\"]*\")*$)");


                for(int i = 0; i < fileds.length; i++) {
                    fileds[i] = fileds[i].replaceAll("(^[|]$)|(^\"|\"$)", "");
                }
                result.add(fileds);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void printResult(List<String[]> result) {

        for (String[] column: result) {
            for (String field: column) {
                System.out.print(" --" + field + "-- ");
            }
            System.out.println();
        }
    }


    public String[] separateColumn(List<String[]> result) {
        Map<String, List<String>> columns = new HashMap<>();

        String[] headers = result.get(0);

//        System.out.println(Arrays.toString(headers));
//        for (String header: headers) {
//            columns.put(header, new ArrayList<String>());
//        }
//
//        System.out.println(columns);
//
//        for (int i = 1; i < result.size(); i++) {
//            int j = 0;
//            for (String field: result.get(i)) {
//                columns.get(headers[j]).add(field);
//                j++;
//            }
//        }
//
//        System.out.println(columns);

        return headers;
    }

    public List<String> getFields() {
        UserStory userStory = new UserStory();
        Class cls = userStory.getClass();

        Field[] fields = cls.getDeclaredFields();

        return Arrays.stream(fields).map(Field::getName).collect(Collectors.toList());
    }

}
