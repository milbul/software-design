import org.junit.Assert;
import org.junit.Test;
import token.Token;
import tokenizer.Tokenizer;
import visitor.CalcVisitor;
import visitor.ParserVisitor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CalcVisitorTest {
    @Test
    public void numberTest() throws IOException {
        String expression = "2";
        Assert.assertEquals(2, calc(expression));
    }

    @Test
    public void simpleTest() throws IOException {
        String expression = "2 * 3";
        Assert.assertEquals(6, calc(expression));
    }

    @Test
    public void expressionTest() throws IOException {
        String expression = " (   (  2  -  3)    *  10 + 6  /  2) *  2  ";
        Assert.assertEquals(-14, calc(expression));
    }


    @Test
    public void expression2Test() throws IOException {
        String expression =  "(10 + 20 / 5 + 7 * 3 - 150 / 10)";
        Assert.assertEquals(20, calc(expression));
    }

    @Test
    public void brokenBracketsTest() {
        String expression = "2 + 3 * 5)";
        Assert.assertThrows(RuntimeException.class, () -> calc(expression));
    }

    @Test
    public void notEnoughOperandsTest() {
        String expression = "3 * + 4";
        Assert.assertThrows(RuntimeException.class, () -> calc(expression));
    }

    @Test
    public void tooManyOperandsTest() {
        String expression = "2 * 3 4";
        Assert.assertThrows(RuntimeException.class, () -> calc(expression));
    }

    @Test
    public void unexpectedCharacterTest() {
        String expression = "3 + 2 a 7";
        Assert.assertThrows(RuntimeException.class, () -> calc(expression));
    }



    private int calc(String expression) throws IOException {
        InputStream expressionStream = new ByteArrayInputStream(expression.getBytes(StandardCharsets.UTF_8));
        Tokenizer tokenizer = new Tokenizer(expressionStream);
        List<Token> tokens = tokenizer.tokenize();
        ParserVisitor parserVisitor = new ParserVisitor();
        for (Token token : tokens) {
            parserVisitor.visit(token);
        }
        CalcVisitor calcVisitor = new CalcVisitor();
        for (Token token : parserVisitor.getTokens()) {
            calcVisitor.visit(token);
        }
        return calcVisitor.getResult();
    }
}
