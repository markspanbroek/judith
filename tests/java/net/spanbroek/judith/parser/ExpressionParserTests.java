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
    public void parsesAddition() {
        MethodCall addition = new MethodCall(new Number(40), "plus", new Expression[]{new Number(2)});
        assertEquals(addition, parser.parse("40+2"));
    }

    @Test
    public void parsesSubtraction() {
        MethodCall subtraction = new MethodCall(new Number(44), "minus", new Expression[]{new Number(2)});
        assertEquals(subtraction, parser.parse("44-2"));
    }

    @Test
    public void parsesBraces() {
        MethodCall oneMinusOne = new MethodCall(new Number(1), "minus", new Expression[]{new Number(1)});
        MethodCall subtraction = new MethodCall(new Number(2), "minus", new Expression[]{oneMinusOne});
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
}
