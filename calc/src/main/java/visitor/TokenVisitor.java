package visitor;

import token.BinaryOperation;
import token.Brace;
import token.tokens.NumberToken;

public interface TokenVisitor {
    void visit(NumberToken token);

    void visit(Brace token);

    void visit(BinaryOperation token);
}
