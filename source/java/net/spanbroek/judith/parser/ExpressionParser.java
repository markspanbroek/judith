package net.spanbroek.judith.parser;

import net.spanbroek.judith.tree.Boolean;
import net.spanbroek.judith.tree.Text;
import net.spanbroek.parsing.Position;
import net.spanbroek.parsing.Rule;
import net.spanbroek.parsing.Transformation;

import java.util.List;

import static net.spanbroek.parsing.Parsing.*;

public class ExpressionParser extends Rule {

    Rule boolean_ = rule();
    Rule number = new NumberParser();
    Rule text = rule();

    public ExpressionParser() {
        setupRules();
        setupTransformations();
    }

    private void setupRules() {
        this.is(
                choice(text, number, boolean_)
        );
        boolean_.is(
                choice("true", "false")
        );
        text.is(
                "\"", repeat(no('"')), "\""
        );
    }

    private void setupTransformations() {
        boolean_.transform(new Transformation() {
            @Override
            public Object transform(List<Object> objects, Position position) {
                boolean value = objects.get(0).equals("true");
                return new Boolean(value);
            }
        });
        text.transform(new Transformation() {
            @Override
            public Object transform(List<Object> objects, Position position) {
                StringBuilder result = new StringBuilder();
                for (int i=1; i<objects.size()-1; i++) {
                    result.append(objects.get(i));
                }
                return new Text(result.toString());
            }
        });
    }
}
