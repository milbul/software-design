package visitor;

import token.BinaryOperation;
import token.Brace;
import token.Token;
import token.tokens.NumberToken;

public class PrintVisitor implements TokenVisitor {

    @Override
    public void visit(NumberToken token) {
        print(token);
    }

    @Override
    public void visit(Brace token) {
        print(token);
    }

    @Override
    public void visit(BinaryOperation token) {
        print(token);
    }

    public void visit(Token token) {
        print(token);
    }

    public void print(Token token) {
        System.out.print(token.toString() + " ");
    }
}
