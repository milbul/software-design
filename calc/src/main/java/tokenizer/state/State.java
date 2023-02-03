package tokenizer.state;

import tokenizer.Tokenizer;

import java.io.IOException;

public interface State {
    void createToken(Tokenizer tokenizer) throws IOException;
}