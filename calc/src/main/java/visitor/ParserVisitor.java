package visitor;

import token.BinaryOperation;
import token.Brace;
import token.Token;
import token.tokens.LeftBrace;
import token.tokens.NumberToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ParserVisitor implements TokenVisitor {
    private final Stack<Token> stack = new Stack<>();
    private final List<Token> tokens = new ArrayList<>();

    @Override
    public void visit(NumberToken token) {
        tokens.add(token);
    }

    @Override
    public void visit(Brace token) {
        if (token instanceof LeftBrace) {
            stack.push(token);
        } else {
            while (!stack.isEmpty() && !(stack.peek() instanceof LeftBrace)) {
                tokens.add(stack.pop());
            }
            if (stack.isEmpty()) {
                throw new RuntimeException("Left brace wasn't found");
            } else {
                stack.pop();
            }
        }
    }

    @Override
    public void visit(BinaryOperation token) {
        while (!stack.isEmpty() && stack.peek() instanceof BinaryOperation
                && ((BinaryOperation) stack.peek()).getPriority() >= token.getPriority()) {
            tokens.add(stack.pop());
        }
        stack.add(token);
    }

    public List<Token> getTokens() {
        while (!stack.isEmpty()) {
            if (!(stack.peek() instanceof BinaryOperation)) {
                throw new RuntimeException("Wrong bracket sequence");
            }
            tokens.add(stack.pop());
        }
        return tokens;
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
