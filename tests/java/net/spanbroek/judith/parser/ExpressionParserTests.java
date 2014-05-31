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
        assertEquals("foo", parser.parse("foo"));
        assertEquals("FOO", parser.parse("FOO"));
        assertEquals("12foo", parser.parse("12foo"));
        assertEquals("1foo23", parser.parse("1foo23"));
        assertEquals("foo'", parser.parse("foo'"));
        assertEquals("?foo", parser.parse("?foo"));
    }

    @Test
    public void parsesBinaryOperations() {
        checkParsingOfBinaryOperator("plus", "+");
        checkParsingOfBinaryOperator("minus", "-");
        checkParsingOfBinaryOperator("equals", "=");
        checkParsingOfBinaryOperator("atmost", "<=");
        checkParsingOfBinaryOperator("atleast", ">=");
        checkParsingOfBinaryOperator("lessthan", "<");
        checkParsingOfBinaryOperator("morethan", ">");
        checkParsingOfBinaryOperator("colon", ":");
    }

    @Test
    public void parsesBraces() {
        MethodCall subtraction = operation(2, "minus", operation(1, "minus", 1));
        assertEquals(subtraction, parser.parse("2-(1-1)"));
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

    private void checkParsingOfBinaryOperator(String parsedMethodName, String operator) {
        assertEquals(operation(1, parsedMethodName, 2), parser.parse("1 " + operator + " 2"));
    }

    private MethodCall operation(int left, String operand, int right) {
        return new MethodCall(new Number(left), operand, new Expression[]{new Number(right)});
    }

    private MethodCall operation(int left, String operand, MethodCall right) {
        return new MethodCall(new Number(left), operand, new Expression[]{right});
    }
}
