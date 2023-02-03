package tokenizer.state;

import token.tokens.*;
import tokenizer.Tokenizer;

import java.io.IOException;

public class StartState implements State {
    @Override
    public void createToken(Tokenizer tokenizer) throws IOException {
        while (Character.isWhitespace(tokenizer.getChar())) {
            tokenizer.nextChar();
        }
        int c = tokenizer.getChar();
        if (c == -1) {
            tokenizer.setState(new EndState());
            return;
        }
        if (Character.isDigit(c)) {
            tokenizer.setState(new NumberState());
            return;
        }
        switch (c) {
            case '*' -> tokenizer.putToken(new Mul());
            case '/' -> tokenizer.putToken(new Div());
            case '+' -> tokenizer.putToken(new Plus());
            case '-' -> tokenizer.putToken(new Minus());
            case '(' -> tokenizer.putToken(new LeftBrace());
            case ')' -> tokenizer.putToken(new RightBrace());
            default -> tokenizer.setState(new ErrorState());
        }
        tokenizer.nextChar();
    }
}