package model.adt;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {
    private Stack<T> stack;
    public MyStack() {
        this.stack = new Stack<>();
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public void push(T value) {
        stack.push(value);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public List<T> getValues() {
        return new ArrayList<>(stack);
    }

    @Override
    public String toString() {
        Stack<T> oldStack = new Stack<>();
        oldStack.addAll(stack);
        Stack<T> newStack = new Stack<>();
        while(!oldStack.isEmpty()) {
            newStack.push(oldStack.pop());
        }
        return newStack.toString();
    }
}
