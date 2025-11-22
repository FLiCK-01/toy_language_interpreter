package model.adt;

import model.values.IValue;

import java.util.Map;

public interface MyIHeap {
    public IValue getVal(int address);
    public void put(int address, IValue value);
    public boolean isDefined(int address);
    public void update(int address, IValue value);
    public int allocate(IValue value);
    public Map<Integer, IValue> getContent();
    public void setContent(Map<Integer, IValue> content);
}
