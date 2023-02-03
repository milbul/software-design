package tokenizer.state;

import token.tokens.NumberToken;
import tokenizer.Tokenizer;

import java.io.IOException;

public class NumberState implements State {
    @Override
    public void createToken(Tokenizer tokenizer) throws IOException {
        StringBuilder sb = new StringBuilder();
        while (Character.isDigit(tokenizer.getChar())) {
            sb.append((char) tokenizer.getChar());
            tokenizer.nextChar();
        }
        tokenizer.putToken(new NumberToken(Integer.parseInt(sb.toString())));
        tokenizer.setState(new StartState());
    }
}
