package visitor;

import token.BinaryOperation;
import token.Brace;
import token.Token;
import token.tokens.NumberToken;

import java.util.Stack;

public class CalcVisitor implements TokenVisitor {
    private final Stack<Token> stack = new Stack<>();

    @Override
    public void visit(NumberToken token) {
        stack.push(token);
    }

    @Override
    public void visit(Brace token) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(BinaryOperation token) {
        if (stack.size() < 2) {
            throw new RuntimeException("Expected more operands");
        }
        NumberToken b = (NumberToken) stack.pop();
        NumberToken a = (NumberToken) stack.pop();
        stack.push(new NumberToken(token.apply(a.getNumber(), b.getNumber())));
    }

    public int getResult() {
        if (stack.size() != 1) {
            throw new RuntimeException("Expected only one value");
        }
        return ((NumberToken) stack.pop()).getNumber();
    }

    public void visit(Token token) {
        if (token instanceof NumberToken) {
            visit((NumberToken) token);
        } else if (token instanceof Brace) {
            visit((Brace) token);
        } else {
            visit((BinaryOperation) token);
        }
    }
}
