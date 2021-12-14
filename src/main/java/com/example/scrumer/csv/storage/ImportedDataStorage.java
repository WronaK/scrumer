package com.example.scrumer.csv.storage;

import com.example.scrumer.csv.model.ImportedData;

import java.util.HashMap;
import java.util.Map;

public class ImportedDataStorage {
    private static Map<String, ImportedData> storage;
    private static ImportedDataStorage instance;

    private ImportedDataStorage() {
        storage = new HashMap<>();
    }

    public static synchronized ImportedDataStorage getInstance() {
        if (instance == null) {
            instance = new ImportedDataStorage();
        }

        return instance;
    }

    public Map<String, ImportedData> getStorage() {
        return storage;
    }

    public void setImportedData(ImportedData importedData) {
        storage.put(importedData.getId(), importedData);
    }
}
