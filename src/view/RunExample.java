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

            System.out.println("\n - - - Program output - - -");
            System.out.println(ctr.getCrtProgram().getOut().toString());
            System.out.println("------------------------------\n");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
