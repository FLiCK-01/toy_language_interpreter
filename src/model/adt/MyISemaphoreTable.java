package model.adt;

import java.util.HashMap;
import java.util.List;
import javafx.util.Pair;

public interface MyISemaphoreTable {
    void put(int key, Pair<Integer, List<Integer>> value);
    Pair<Integer, List<Integer>> get(int key);
    boolean containsKey(int key);
    int getFreeAddress();
    void update(int key, Pair<Integer, List<Integer>> value);
    void setContent(HashMap<Integer, Pair<Integer, List<Integer>>> newMap);
    HashMap<Integer, Pair<Integer, List<Integer>>> getContent();
}
