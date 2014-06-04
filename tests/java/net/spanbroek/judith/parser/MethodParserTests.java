package net.spanbroek.judith.parser;

import net.spanbroek.judith.tree.Assignment;
import net.spanbroek.judith.tree.Method;
import net.spanbroek.judith.tree.Statement;
import net.spanbroek.judith.tree.Number;
import net.spanbroek.parsing.Parser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MethodParserTests {

    private Parser parser;

    @Before
    public void setUp() throws Exception {
        parser = new JudithParser().method;
    }

    @Test
    public void testParsesSimpleMethod() {
        Method method = new Method("foo", new String[]{}, new Statement[]{});
        assertEquals(method, parser.parse("method foo [ ]"));
    }

    @Test
    public void testParsesMethodWithParameters() {
        Method method = new Method("foo", new String[]{"a", "b"}, new Statement[]{});
        assertEquals(method, parser.parse("method foo( a , b ) [ ]"));
    }

    @Test
    public void testParsesMethodWithStatements() {
        Statement[] statements = {
                new Assignment("a", new Number(1)),
                new Assignment("b", new Number(2))
        };
        Method method = new Method("foo", new String[]{}, statements);
        assertEquals(method, parser.parse("method foo [ a:=1 b:=2 ]"));
    }
}
