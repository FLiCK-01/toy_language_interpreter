package model.adt;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;

public class MySemaphoreTable implements MyISemaphoreTable{
    private HashMap<Integer, Pair<Integer, List<Integer>>> semaphoreTable;
    private int freeLocation = 0;

    public MySemaphoreTable(){
        this.semaphoreTable = new HashMap<>();
    }

    @Override
    public synchronized void put(int key, Pair<Integer, List<Integer>> value) {
        semaphoreTable.put(key, value);
    }

    @Override
    public synchronized Pair<Integer, List<Integer>> get(int key) {
        return semaphoreTable.get(key);
    }

    @Override
    public synchronized boolean containsKey(int key) {
        return semaphoreTable.containsKey(key);
    }

    @Override
    public synchronized int getFreeAddress() {
        freeLocation++;
        return freeLocation;
    }

    @Override
    public synchronized void update(int key, Pair<Integer, List<Integer>> value) {
        semaphoreTable.put(key, value);
    }

    @Override
    public synchronized void setContent(HashMap<Integer, Pair<Integer, List<Integer>>> newMap) {
        semaphoreTable = newMap;
    }

    @Override
    public synchronized HashMap<Integer, Pair<Integer, List<Integer>>> getContent() {
        return semaphoreTable;
    }

    @Override
    public String toString() {
        return semaphoreTable.toString();
    }
}
