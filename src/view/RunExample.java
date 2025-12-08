package view;

import controller.IController;
import exception.MyException;
import exception.RepoException;

import java.io.IOException;

public class RunExample extends Command{
    private IController ctr;

    public RunExample(String key, String desc, IController ctr) {
        super(key, desc);
        this.ctr = ctr;
    }

    @Override
    public void execute() {
        try {
            ctr.allStep();
        } catch (InterruptedException e) {
            System.out.println("Execution interrupted: " + e.getMessage());
        } catch (MyException e) {
            System.out.println("Interpreter error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Execution error: " + e.getMessage());
        }
    }
}
