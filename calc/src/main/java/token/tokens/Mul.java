package token.tokens;

import token.BinaryOperation;

public class Mul implements BinaryOperation {
    @Override
    public int getPriority() {
        return 3;
    }

    @Override
    public int apply(int a, int b) {
        return a * b;
    }

    @Override
    public String toString() {
        return "MUL";
    }
}
