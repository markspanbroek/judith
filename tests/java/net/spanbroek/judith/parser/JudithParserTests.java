package net.spanbroek.judith.parser;

import net.spanbroek.judith.tree.Assignment;
import net.spanbroek.judith.tree.Number;
import net.spanbroek.judith.tree.Program;
import net.spanbroek.judith.tree.Statement;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JudithParserTests {

    private JudithParser parser;

    @Before
    public void setUp() throws Exception {
        parser = new JudithParser();
    }

    @Test
    public void parsesStatements() {
        Statement[] statements = new Statement[] {
                new Assignment("a", new Number(1)),
                new Assignment("b", new Number(2))
        };
        assertEquals(new Program(statements), parser.parse(" a:=1 b:=2 "));
    }

}
