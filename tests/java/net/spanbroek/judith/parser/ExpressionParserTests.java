package net.spanbroek.judith.parser;

import net.spanbroek.judith.tree.Boolean;
import net.spanbroek.judith.tree.Number;
import net.spanbroek.judith.tree.Text;
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
}
