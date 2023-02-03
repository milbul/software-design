package tokenizer.state;

import tokenizer.Tokenizer;

public class EndState implements State {
    @Override
    public void createToken(Tokenizer tokenizer) {
        throw new UnsupportedOperationException();
    }
}
