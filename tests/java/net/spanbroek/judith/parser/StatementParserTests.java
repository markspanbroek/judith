package net.spanbroek.judith.parser;

import net.spanbroek.judith.tree.*;
import net.spanbroek.judith.tree.Number;
import net.spanbroek.judith.tree.Boolean;
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
    public void parsesObjectDeclaration() {
        ObjectDeclaration declaration = new ObjectDeclaration("a", new Number(1));
        assertEquals(declaration, parser.parse("object a := 1"));
    }

    @Test
    public void parsesAssignment() {
        Assignment assignment = new Assignment("a", new Number(1));
        assertEquals(assignment, parser.parse("a := 1"));
    }

    @Test
    public void parsesIfStatement() {
        Statement[] statements = { new Assignment("a", new Number(1)) };
        Conditional[] conditionals = { new Conditional(new Boolean(true), statements) };
        assertEquals(new If(conditionals), parser.parse("if true a := 1 fi"));
    }

    @Test
    public void parsesIfStatementWithMultipleConditions() {
        Statement[] statements1 = { new Assignment("a", new Number(1)) };
        Statement[] statements2 = { new Assignment("b", new Number(2)) };
        Conditional conditional1 = new Conditional(new Boolean(true), statements1);
        Conditional conditional2 = new Conditional(new Boolean(false), statements2);
        Conditional[] conditionals = {conditional1, conditional2};
        assertEquals(new If(conditionals), parser.parse("if true a := 1 || false b := 2 fi"));
    }
}
