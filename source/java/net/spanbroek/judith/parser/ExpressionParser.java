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

    Rule expression1 = rule();
    Rule expression2 = rule();
    Rule expression3 = rule();
    Rule expression4 = rule();
    Rule expression5 = rule();
    Rule expression6 = rule();
    Rule expression7 = rule();
    Rule expression8 = rule();
    Rule braces = rule();
    Rule boolean_ = rule();
    Rule number = rule();
    Rule text = rule();
    Rule identifier = rule();
    Rule digit = rule();
    Rule letter = rule();
    Rule w = rule();
    Rule comment = rule();

    public ExpressionParser() {
        setupRules();
        setupTransformations();
    }

    private void setupRules() {
        this.is(
                expression1
        );
        expression1.is(
                expression2
        );
        expression2.is(
                optional(expression2, w, choice("+", "-"), w), expression3
        );
        expression3.is(
                expression4
        );
        expression4.is(
                expression5
        );
        expression5.is(
                expression6
        );
        expression6.is(
                expression7
        );
        expression7.is(
                expression8
        );
        expression8.is(
                choice(braces, text, number, boolean_, identifier)
        );
        braces.is(
                "(", w, this, w, ")"
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
        w.is(
                repeat(choice(" ", "\n", comment))
        );
        comment.is(
                "#", repeat(no('\n'))
        );
    }

    private void setupTransformations() {
        expression2.transform(new Transformation() {
            @Override
            public Object transform(List<Object> objects, Context context) {
                if (objects.size() > 1) {
                    Expression left = (Expression) objects.get(0);
                    Expression right = (Expression) objects.get(4);
                    String operator = objects.get(2).equals("+") ? "plus" : "minus";
                    return new MethodCall(left, operator, new Expression[]{right});
                }
                return objects.get(0);
            }
        });
        braces.transform(new Transformation() {
            @Override
            public Object transform(List<Object> objects, Context context) {
                return objects.get(2);
            }
        });
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
        w.transform(new Transformation() {
            @Override
            public Object transform(List<Object> objects, Context context) {
                return context.getOriginalText();
            }
        });
    }
}
