package token;

import visitor.TokenVisitor;

public interface BinaryOperation extends Token {
    @Override
    default void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    int getPriority();

    int apply(int a, int b);
}
