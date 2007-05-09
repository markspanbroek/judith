package net.spanbroek.judith.scriptapi;

import net.spanbroek.judith.interpreter.*;
import javax.script.*;
import java.io.*;
import java.util.*;

public class JudithScriptEngine extends AbstractScriptEngine {

    private ScriptEngineFactory factory = null;

    public JudithScriptEngine() {
        super();
    }

    JudithScriptEngine(ScriptEngineFactory factory) {
        this.factory = factory;
    }

    public ScriptEngineFactory getFactory() {
        return factory;
    }

    public Bindings createBindings() {
        return null; // TODO
    }

    public Object eval(Reader reader, ScriptContext context)
      throws ScriptException {
        try {
            new Interpreter().interpret(reader);
        }
        catch(IOException exception) {
            throw new ScriptException(exception);
        }
        return null;
    }

    public Object eval(String script, ScriptContext context)
      throws ScriptException {
        return eval(new StringReader(script), context);
    }

}