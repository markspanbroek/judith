package net.spanbroek.judith.parser;

import net.spanbroek.judith.tree.*;
import net.spanbroek.judith.tree.Boolean;
import net.spanbroek.parsing.Context;
import net.spanbroek.parsing.Rule;
import net.spanbroek.parsing.Transformation;
import net.spanbroek.judith.tree.Number;

import java.util.List;

import static net.spanbroek.parsing.Parsing.*;

public class ExpressionParser extends Rule {

    Rule boolean_ = rule();
    Rule number = rule();
    Rule text = rule();
    Rule identifier = rule();
    Rule digit = rule();
    Rule letter = rule();

    public ExpressionParser() {
        setupRules();
        setupTransformations();
    }

    private void setupRules() {
        this.is(
                choice(text, number, boolean_, identifier)
        );
        boolean_.is(
                choice("true", "false")
        );
        number.is(
                digit, repeat(digit), optional(".", digit, repeat(digit))
        );
        text.is(
                "\"", repeat(no('"')), "\""
        );
        identifier.is(
                repeat(digit), letter, repeat(choice(digit, letter))
        );
        digit.is(
                range('0', '9')
        );
        letter.is(
                choice(range('a', 'z'), range('A', 'Z'), "?", "'")
        );
    }

    private void setupTransformations() {
        boolean_.transform(new Transformation() {
            @Override
            public Object transform(List<Object> objects, Context context) {
                boolean value = objects.get(0).equals("true");
                return new Boolean(value);
            }
        });
        number.transform(new Transformation() {
            @Override
            public Object transform(List<Object> objects, Context context) {
                double value = Double.parseDouble(context.getOriginalText());
                return new Number(value);
            }
        });
        text.transform(new Transformation() {
            @Override
            public Object transform(List<Object> objects, Context context) {
                String originalText = context.getOriginalText();
                String value = originalText.substring(1, originalText.length() - 1);
                return new Text(value);
            }
        });
        identifier.transform(new Transformation() {
            @Override
            public Object transform(List<Object> objects, Context context) {
                return context.getOriginalText();
            }
        });
    }
}
