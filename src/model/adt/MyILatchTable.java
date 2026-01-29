package model.adt;

import java.util.HashMap;

public interface MyILatchTable {
    void put(int key, int value);
    int get(int key);
    boolean containsKey(int key);
    int getFreeAddress();
    void update(int key, int value);
    void setContent(HashMap<Integer, Integer> newMap);
    HashMap<Integer, Integer> getContent();
}
