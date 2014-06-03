package net.spanbroek.judith.parser;

import net.spanbroek.judith.tree.Assignment;
import net.spanbroek.judith.tree.Number;
import net.spanbroek.parsing.Parser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StatementParserTests {

    private Parser parser;

    @Before
    public void setUp() throws Exception {
        parser = new JudithParser().statement;
    }

    @Test
    public void parsesAssignment() {
        Assignment assignment = new Assignment("a", new Number(1));
        assertEquals(assignment, parser.parse("a := 1"));
    }
}
