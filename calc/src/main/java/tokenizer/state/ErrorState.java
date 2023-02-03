package tokenizer.state;

import tokenizer.Tokenizer;

public class ErrorState implements State {
    @Override
    public void createToken(Tokenizer tokenizer) {
        throw new RuntimeException(String.format("Unsupported character at %d", tokenizer.getPos()));
    }
}