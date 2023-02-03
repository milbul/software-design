package tokenizer;

import token.Token;
import tokenizer.state.EndState;
import tokenizer.state.StartState;
import tokenizer.state.State;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private final InputStream is;
    private int curChar;
    private int curPos;
    private State state;
    private final List<Token> tokens;

    public Tokenizer(InputStream is) throws IOException {
        this.is = is;
        curPos = -1;
        curChar = -1;
        tokens = new ArrayList<>();
        nextChar();
    }

    public void nextChar() throws IOException {
        curPos++;
        try {
            curChar = is.read();
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public int getChar() {
        return curChar;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void putToken(Token token) {
        tokens.add(token);
    }

    public int getPos() {
        return curPos;
    }

    public List<Token> tokenize() throws IOException {
        state = new StartState();
        while (!(state instanceof EndState)) {
            state.createToken(this);
        }
        return tokens;
    }
}