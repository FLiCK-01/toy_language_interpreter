package model.adt;

import model.values.IValue;

import java.util.HashMap;
import java.util.Map;

public class MyHeap implements MyIHeap {
    private Map<Integer, IValue> map;
    private int freeLocation;
    public MyHeap() {
        this.map = new HashMap<>();
        this.freeLocation = 1;
    }

    @Override
    public IValue getVal(int address) {
        return map.get(address);
    }

    @Override
    public void put(int address, IValue value) {
        map.put(address, value);
    }

    @Override
    public boolean isDefined(int address) {
        return map.containsKey(address);
    }

    @Override
    public void update(int address, IValue value) {
        map.put(address, value);
    }

    @Override
    public int allocate(IValue value) {
        int newAddress = freeLocation;
        map.put(newAddress, value);
        freeLocation++;
        return newAddress;
    }

    @Override
    public Map<Integer, IValue> getContent() {
        return map;
    }

    @Override
    public void setContent(Map<Integer, IValue> content) {
        this.map = content;
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
