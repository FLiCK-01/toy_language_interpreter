package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.expressions.IExp;
import model.expressions.RelationalExpression;
import model.expressions.RelationalOperator;
import model.types.IType;

public class SwitchStmt implements IStmt {
    private IExp exp;
    private IExp exp1;
    private IExp exp2;
    private IStmt stmt1;
    private IStmt stmt2;
    private IStmt stmt3;

    public SwitchStmt(IExp exp, IExp exp1, IStmt stmt1, IExp exp2, IStmt stmt2, IStmt stmt3) {
        this.exp = exp;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
        this.stmt3 = stmt3;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IStmt nestedIf = new IfStmt(
                new RelationalExpression(exp, exp2, RelationalOperator.EQUAL),
                stmt2,
                stmt3
        );
        IStmt mainIf = new IfStmt(
                new RelationalExpression(exp, exp1, RelationalOperator.EQUAL),
                stmt1,
                nestedIf
        );

        state.getExeStack().push(mainIf);

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new SwitchStmt(exp.deepCopy(), exp1.deepCopy(), stmt1.deepCopy(), exp2.deepCopy(), stmt2.deepCopy(), stmt3.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType mainType = exp.typeCheck(typeEnv);
        IType typeExp1 =  exp1.typeCheck(typeEnv);
        IType typeExp2 =  exp2.typeCheck(typeEnv);

        if(mainType.equals(typeExp1) && mainType.equals(typeExp2)) {
            stmt1.typeCheck(typeEnv);
            stmt2.typeCheck(typeEnv);
            stmt3.typeCheck(typeEnv);
            return typeEnv;
        } else {
            throw new MyException("SwitchStmt: main expression and case expressions must be of the same type.");
        }
    }

    @Override
    public String toString() {
        return "switch(" + exp + ") (case " + exp1 + ": " + stmt1 + ") (case " + exp2 + ": " + stmt2 + ") (default: " + stmt3 + ")";
    }
}
