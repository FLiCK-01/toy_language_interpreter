package model.adt;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;

public class MyBarrierTable implements MyIBarrierTable{
    private HashMap<Integer, Pair<Integer, List<Integer>>> map;
    private int freeLocation = 0;

    public MyBarrierTable(){
        this.map = new HashMap<>();
    }

    @Override
    public synchronized void put(int key, Pair<Integer, List<Integer>> value) {
        map.put(key, value);
    }

    @Override
    public synchronized Pair<Integer, List<Integer>> get(int key) {
        return map.get(key);
    }

    @Override
    public synchronized boolean containsKey(int key) {
        return map.containsKey(key);
    }

    @Override
    public synchronized int getFreeAddress() {
        freeLocation++;
        return freeLocation;
    }

    @Override
    public synchronized void update(int key, Pair<Integer, List<Integer>> value) {
        map.put(key, value);
    }

    @Override
    public synchronized HashMap<Integer, Pair<Integer, List<Integer>>> getContent() {
        return map;
    }

    @Override
    public synchronized void setContent(HashMap<Integer, Pair<Integer, List<Integer>>> newMap) {
        this.map = newMap;
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
