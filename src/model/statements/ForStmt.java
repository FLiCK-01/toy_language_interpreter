package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.expressions.IExp;
import model.expressions.RelationalExpression;
import model.expressions.RelationalOperator;
import model.expressions.VariableExpression;
import model.types.IType;
import model.types.IntType;

public class ForStmt implements IStmt {
    private String var;
    private IExp exp1;
    private IExp exp2;
    private IExp exp3;
    private IStmt stmt;

    public ForStmt(String var, IExp exp1, IExp exp2, IExp exp3, IStmt stmt) {
        this.var = var;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
        this.stmt = stmt;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IStmt init = new AssignStmt(var, exp1);

        IExp condExp = new RelationalExpression(new VariableExpression(var), exp2, RelationalOperator.LESS_THAN);

        IStmt step = new AssignStmt(var, exp3);
        IStmt body = new CompStmt(stmt, step);

        IStmt loop = new WhileStmt(condExp, body);

        IStmt decl = new VarDeclStmt(var, new IntType());

        IStmt result = new CompStmt(decl, new CompStmt(init, loop));

        state.getExeStack().push(result);

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ForStmt(var, exp1.deepCopy(), exp2.deepCopy(), exp3.deepCopy(), stmt.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        MyIDictionary<String, IType> newEnv = typeEnv.deepCopy();
        newEnv.put(var, new IntType());

        IType t1 = exp1.typeCheck(newEnv);
        IType t2 = exp2.typeCheck(newEnv);
        IType t3 = exp3.typeCheck(newEnv);

        if(t1.equals(new IntType()) && t2.equals(new IntType()) && t3.equals(new IntType())) {
            stmt.typeCheck(newEnv);

            return newEnv;
        } else {
            throw new MyException("ForStmt: all expressions must be of int type.");
        }
    }

    @Override
    public String toString() {
        return "for(" + var + "=" + exp1 + "; " + var + "<" + exp2 + "; " + var + "=" + exp3 + ") {" + stmt + " }";
    }
}
