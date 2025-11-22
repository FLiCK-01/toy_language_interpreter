package model.adt;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T> {
    private final List<T> list;

    public MyList() {
        this.list = new ArrayList<>();
    }

    @Override
    public void add(T value) {
        list.add(value);
    }

    @Override
    public List<T> getValues() {
        return new ArrayList<>(list);
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
