package net.spanbroek.judith.scriptapi;

import javax.script.*;
import java.util.*;

public class JudithScriptEngineFactory implements ScriptEngineFactory {

    public String getEngineName() {
        return "judith script engine";
    }

    public String getEngineVersion() {
        return "0.1dev";
    }

    public List<String> getExtensions() {
        return Arrays.asList("judith");
    }

    public List<String> getMimeTypes() {
        return Arrays.asList("application/judith");
    }

    public List<String> getNames() {
        return Arrays.asList("judith");
    }

    public String getLanguageName() {
        return "judith";
    }

    public String getLanguageVersion() {
        return "0.1dev";
    }

    public Object getParameter(String key) {
        if (ScriptEngine.ENGINE.equals(key)) {
            return getEngineName();
        }
        else if (ScriptEngine.ENGINE_VERSION.equals(key)) {
            return getEngineVersion();
        }
        else if (ScriptEngine.NAME.equals(key)) {
            return getNames().get(0);
        }
        else if (ScriptEngine.LANGUAGE.equals(key)) {
            return getLanguageName();
        }
        else if (ScriptEngine.LANGUAGE_VERSION.equals(key)) {
            return getLanguageVersion();
        }
        else {
            return null;
        }
    }

    public String getMethodCallSyntax(String object, String method,
      String... arguments) {
        String result = object + "." + method;
        if (arguments.length > 0) {
            result += "(";
            for (int i=0; i<arguments.length; i++) {
                if (i>0) {
                    result += ",";
                }
                result += arguments[i];
            }
            result += ")";
        }
        return result;
    }

    public String getOutputStatement(String toDisplay) {
        String result = "Objects.IO.StandardOutput.write(";
        boolean isFirst = true;
        for(String s : toDisplay.split("\"")) {
            if(!isFirst) {
                result += " + Text.quote + ";
            }
            result += "\"" + s + "\"";
            isFirst = false;
        }
        result = result + ")";
        return result;
    }

    public String getProgram(String... statements) {
        String result = "";
        for (int i=0; i<statements.length; i++) {
            if (i>0) {
                result += '\n';
            }
            result += statements[i];
        }
        return result;
    }

    public ScriptEngine getScriptEngine() {
        return new JudithScriptEngine(this);
    }

}