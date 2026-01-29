package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.expressions.IExp;
import model.expressions.NotExpression;
import model.types.BoolType;
import model.types.IType;

public class RepeatUntilStmt implements IStmt {
    private IStmt stmt;
    private IExp exp;

    public RepeatUntilStmt(IStmt stmt, IExp exp) {
        this.stmt = stmt;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IStmt converted = new CompStmt(
                stmt,
                new WhileStmt(
                        new NotExpression(exp),
                        stmt.deepCopy()
                )
        );

        state.getExeStack().push(converted);

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new RepeatUntilStmt(stmt.deepCopy(), exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType expType = exp.typeCheck(typeEnv);

        if(expType.equals(new BoolType())) {
            stmt.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        } else {
            throw new MyException("the condition of repeat until must be boolean.");
        }
    }

    @Override
    public String toString() {
        return "repeat (" + stmt + ") until (" + exp +")";
    }
}
