package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.expressions.IExp;
import model.types.BoolType;
import model.types.IType;

public class ConditionalAssignmentStmt implements IStmt {
    private String var;
    private IExp exp1;
    private IExp exp2;
    private IExp exp3;

    public ConditionalAssignmentStmt(String var, IExp exp1, IExp exp2, IExp exp3) {
        this.var = var;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IStmt ifStmt = new IfStmt(
                exp1,
                new AssignStmt(var, exp2),
                new AssignStmt(var, exp3)
        );

        state.getExeStack().push(ifStmt);

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ConditionalAssignmentStmt(var, exp1.deepCopy(), exp2.deepCopy(), exp3.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typeVar = typeEnv.get(var);
        IType typeExp1 = exp1.typeCheck(typeEnv);
        IType typeExp2 = exp2.typeCheck(typeEnv);
        IType typeExp3 = exp3.typeCheck(typeEnv);

        if(typeExp1.equals(new BoolType())) {
            if(typeVar.equals(typeExp2) && typeVar.equals(typeExp3)) {
                return typeEnv;
            } else {
                throw new MyException("Conditional Assignment: variable and both options must be of the same type.");
            }
        } else {
            throw new MyException("Conditional Assignment: condition must be boolean.");
        }
    }

    @Override
    public String toString() {
        return var + "=(" + exp1 + ") ?" + exp2 + " : " + exp3;
    }
}
