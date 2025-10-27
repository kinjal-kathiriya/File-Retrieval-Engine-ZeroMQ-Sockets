package csc435.app;

import java.util.HashMap;
import java.util.Map;

public class IndexStore {
    private Map<String, String> index;

    public IndexStore() {
        this.index = new HashMap<>();
    }

    public synchronized void indexData(String data) {
        System.out.println("Indexing data: " + data); // Debugging statement
        index.put(data.toLowerCase(), data);
    }

    public synchronized String searchData(String data) {
     System.out.println("Searching for data: " + data); // Debugging statement
        return index.get(data.toLowerCase());
    }
}