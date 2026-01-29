package model.adt;

import java.util.HashMap;

public class MyLatchTable implements MyILatchTable {
    private HashMap<Integer, Integer> latchTable;
    private int freeLocation = 0;

    public MyLatchTable() {
        this.latchTable = new HashMap<>();
    }

    @Override
    public synchronized void put(int key, int value) {
        latchTable.put(key, value);
    }

    @Override
    public synchronized int get(int key) {
        return latchTable.get(key);
    }

    @Override
    public synchronized boolean containsKey(int key) {
        return latchTable.containsKey(key);
    }

    @Override
    public synchronized int getFreeAddress() {
        freeLocation++;
        return freeLocation;
    }

    @Override
    public synchronized void update(int key, int value) {
        latchTable.put(key, value);
    }

    @Override
    public synchronized void setContent(HashMap<Integer, Integer> newMap) {
        this.latchTable = newMap;
    }

    @Override
    public synchronized HashMap<Integer, Integer> getContent() {
        return latchTable;
    }

    @Override
    public String toString() {
        return latchTable.toString();
    }
}
