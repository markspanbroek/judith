package net.spanbroek.judith.parser;

import net.spanbroek.judith.tree.*;
import net.spanbroek.judith.tree.Boolean;
import net.spanbroek.judith.tree.Number;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExpressionParserTests {

    private ExpressionParser parser;

    @Before
    public void setUp() {
        parser = new ExpressionParser();
    }

    @Test
    public void parsesBooleans() {
        assertEquals(new Boolean(true), parser.parse("true"));
        assertEquals(new Boolean(false), parser.parse("false"));
    }

    @Test
    public void parsesNumbers() {
        assertEquals(new Number(0), parser.parse("0"));
        assertEquals(new Number(3), parser.parse("3"));
        assertEquals(new Number(42), parser.parse("42"));
        assertEquals(new Number(42.31), parser.parse("42.31"));
    }

    @Test
    public void parsesText() {
        assertEquals(new Text("foo"), parser.parse("\"foo\""));
        assertEquals(new Text("bar'234"), parser.parse("\"bar'234\""));
    }

    @Test
    public void parsesIdentifiers() {
        assertEquals(new Reference("foo"), parser.parse("foo"));
        assertEquals(new Reference("FOO"), parser.parse("FOO"));
        assertEquals(new Reference("12foo"), parser.parse("12foo"));
        assertEquals(new Reference("1foo23"), parser.parse("1foo23"));
        assertEquals(new Reference("foo'"), parser.parse("foo'"));
        assertEquals(new Reference("?foo"), parser.parse("?foo"));
    }

    @Test
    public void parsesBinaryOperations() {
        checkParsingOfBinaryOperator("and", "and");
        checkParsingOfBinaryOperator("or", "or");
        checkParsingOfBinaryOperator("plus", "+");
        checkParsingOfBinaryOperator("minus", "-");
        checkParsingOfBinaryOperator("equals", "=");
        checkParsingOfBinaryOperator("atmost", "<=");
        checkParsingOfBinaryOperator("atleast", ">=");
        checkParsingOfBinaryOperator("lessthan", "<");
        checkParsingOfBinaryOperator("morethan", ">");
        checkParsingOfBinaryOperator("colon", ":");
        checkParsingOfBinaryOperator("star", "*");
        checkParsingOfBinaryOperator("slash", "/");
        checkParsingOfBinaryOperator("carrot", "^");
    }

    @Test
    public void parsesPrefixOperations() {
        assertEquals(methodCall("a", "minus"), parser.parse("-a"));
        assertEquals(methodCall("a", "not"), parser.parse("not a"));
    }

    @Test
    public void parsesMethodCalls() {
        assertEquals(
                methodCall("callee", "method"),
                parser.parse("callee.method"));
        assertEquals(
                methodCall("callee", "method", "parameter"),
                parser.parse("callee.method(parameter)"));
        assertEquals(
                methodCall("callee", "method", "parameter1", "parameter2"),
                parser.parse("callee.method(parameter1, parameter2)"));
    }

    @Test
    public void parsesBraces() {
        MethodCall subtraction = methodCall("a", "minus", methodCall("b", "minus", "c"));
        assertEquals(subtraction, parser.parse("a-(b-c)"));
    }

    @Test
    public void ignoresWhitespace() {
        assertEquals(new Number(1), parser.parse("( 1  )"));
        assertEquals(new Number(1), parser.parse("( 1 \n\n)"));
    }

    @Test
    public void ignoresComments() {
        assertEquals(new Number(1), parser.parse("( #comment\n 1 #comment\n )"));
    }

    private void checkParsingOfBinaryOperator(String expectedMethodName, String operator) {
        assertEquals(methodCall("a", expectedMethodName, "b"), parser.parse("a " + operator + " b"));
    }

    private MethodCall methodCall(String callee, String methodName) {
        return new MethodCall(new Reference(callee), methodName, new Expression[]{});
    }

    private MethodCall methodCall(String callee, String methodName, String... parameters) {
        Expression[] expressions = new Expression[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            expressions[i] = new Reference(parameters[i]);
        }
        return new MethodCall(new Reference(callee), methodName, expressions);
    }

    private MethodCall methodCall(String callee, String methodName, Expression parameter) {
        return new MethodCall(new Reference(callee), methodName, new Expression[]{parameter});
    }
}
