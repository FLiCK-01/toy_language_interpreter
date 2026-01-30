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
        IStmt increment = new AssignStmt(var, exp3);
        IStmt body = new CompStmt(stmt, increment);

        IStmt loop = new WhileStmt(condExp, body);

        IStmt result = new CompStmt(
                new VarDeclStmt(var, new IntType()),
                new CompStmt(init, loop)
        );

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

        IType type1 = exp1.typeCheck(newEnv);
        IType type2 = exp2.typeCheck(newEnv);
        IType type3 = exp3.typeCheck(newEnv);

        if(type1.equals(new IntType()) && type2.equals(new IntType()) && type3.equals(new IntType())) {
            stmt.typeCheck(newEnv);
            return typeEnv;
        } else {
            throw new MyException("ForStmt: all expressions must be of type int.");
        }
    }

    @Override
    public String toString() {
        return "for(" + var + "=" + exp1 + ";" + var + "<" + exp2 + ";" + var + "=" + exp3 + ") {" + stmt + " }";
    }
}
