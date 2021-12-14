package com.example.scrumer.csv.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MyValue {
    private String idImportedData;
    private List<Value> values = new ArrayList<>();
}
