package token.tokens;

import token.BinaryOperation;

public class Plus implements BinaryOperation {
    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public int apply(int a, int b) {
        return a + b;
    }

    @Override
    public String toString() {
        return "PLUS";
    }
}
