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
        assertEquals(operation("a", "minus"), parser.parse("-a"));
        assertEquals(operation("a", "not"), parser.parse("not a"));
    }

    @Test
    public void parsesBraces() {
        MethodCall subtraction = operation("a", "minus", operation("b", "minus", "c"));
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
        assertEquals(operation("a", expectedMethodName, "b"), parser.parse("a " + operator + " b"));
    }

    private MethodCall operation(String left, String operand) {
        return new MethodCall(new Reference(left), operand, new Expression[]{});
    }

    private MethodCall operation(String left, String operand, String right) {
        return new MethodCall(new Reference(left), operand, new Expression[]{new Reference(right)});
    }

    private MethodCall operation(String left, String operand, MethodCall right) {
        return new MethodCall(new Reference(left), operand, new Expression[]{right});
    }
}
