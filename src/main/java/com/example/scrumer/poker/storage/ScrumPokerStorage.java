package com.example.scrumer.poker.storage;

import com.example.scrumer.poker.model.ScrumPoker;

import java.util.HashMap;
import java.util.Map;

public class ScrumPokerStorage {

    private static Map<String, ScrumPoker> storage;
    private static ScrumPokerStorage instance;

    private ScrumPokerStorage() {
        storage = new HashMap<>();
    }

    public static synchronized ScrumPokerStorage getInstance() {
        if (instance == null) {
            instance = new ScrumPokerStorage();
        }

        return instance;
    }

    public Map<String, ScrumPoker> getStorage() {
        return storage;
    }

    public void setScrumPoker(ScrumPoker scrumPoker) {
        storage.put(scrumPoker.getIdScrumPoker(), scrumPoker);
    }
}
