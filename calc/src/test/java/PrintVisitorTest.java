import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import token.Token;
import tokenizer.Tokenizer;
import visitor.ParserVisitor;
import visitor.PrintVisitor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class PrintVisitorTest {
    private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @Before
    public void before() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void emptyExpressionTest() throws IOException {
        String expression = "";
        calc(expression);
        String expected = "";
        Assert.assertEquals(expected, outputStream.toString());
    }

    @Test
    public void numberTest() throws IOException {
        String expression = "2";
        calc(expression);
        String expected = "NUMBER(2) ";
        Assert.assertEquals(expected, outputStream.toString());
    }

    @Test
    public void simpleTest() throws IOException {
        String expression = "2 * 3 ";
        calc(expression);
        String expected = "NUMBER(2) NUMBER(3) MUL ";
        Assert.assertEquals(expected, outputStream.toString());
    }

    @Test
    public void expression1Test() throws IOException {
        String expression = " (   (  2  -  3)    *  10 + 6  /  2) *  2  ";
        calc(expression);
        String expected = "NUMBER(2) NUMBER(3) MINUS NUMBER(10) MUL NUMBER(6) NUMBER(2) DIV PLUS NUMBER(2) MUL ";
        Assert.assertEquals(expected, outputStream.toString());
    }

    @Test
    public void expression2Test() throws IOException {
        String expression = "(10 + 20 / 5 + 7 * 3 - 150 / 10)";
        calc(expression);
        String expected = "NUMBER(10) NUMBER(20) NUMBER(5) DIV PLUS NUMBER(7) NUMBER(3) MUL PLUS NUMBER(150) NUMBER(10) DIV MINUS ";
        Assert.assertEquals(expected, outputStream.toString());
    }

    private void calc(String expression) throws IOException {
        InputStream expressionStream = new ByteArrayInputStream(expression.getBytes(StandardCharsets.UTF_8));
        Tokenizer tokenizer = new Tokenizer(expressionStream);
        List<Token> tokens = tokenizer.tokenize();
        ParserVisitor parserVisitor = new ParserVisitor();
        for (Token token : tokens) {
            parserVisitor.visit(token);
        }
        PrintVisitor printVisitor = new PrintVisitor();
        for (Token token : parserVisitor.getTokens()) {
            printVisitor.visit(token);
        }
    }
}
