package net.spanbroek.judith.parser;

import net.spanbroek.judith.parser.parser.*;
import net.spanbroek.judith.parser.lexer.*;
import net.spanbroek.judith.parser.Visitor;
import net.spanbroek.judith.tree.Program;
import net.spanbroek.judith.Exception;
import java.io.*;

public class Parser {

    static public Program parse(Reader reader, String filename) 
      throws Exception {

        net.spanbroek.judith.parser.parser.Parser parser;
        parser = new net.spanbroek.judith.parser.parser.Parser(
          new Lexer(
            new PushbackReader(reader)
          )
        );

        try {
            Visitor visitor = new Visitor(filename);
            parser.parse().apply(visitor);
            return visitor.getResult();
        }
        catch(ParserException exception) {
            throw new Exception(exception);
        }
        catch(LexerException exception) {
            throw new Exception(exception);
        }
        catch(IOException exception) {
            throw new Exception(exception);
        }

    }

}
