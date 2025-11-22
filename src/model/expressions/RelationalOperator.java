package model.expressions;

public enum RelationalOperator {
    LESS_THAN("<"),
    LESS_EQUAL("<="),
    EQUAL("=="),
    NOT_EQUAL("!="),
    GREATER_THAN(">"),
    GREATER_EQUAL(">=");

    private final String symbol;

    RelationalOperator(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
