package net.spanbroek.judith.parser;

import net.spanbroek.parsing.Context;
import net.spanbroek.parsing.Rule;
import net.spanbroek.parsing.Transformation;
import net.spanbroek.judith.tree.Number;

import java.util.List;

import static net.spanbroek.parsing.Parsing.*;

public class NumberParser extends Rule {

    private Rule digit = rule();

    public NumberParser() {
        setupRules();
        setupTransformations();
    }

    private void setupRules() {
        this.is(
                digit, repeat(digit), optional(".", digit, repeat(digit))
        );
        digit.is(
                range('0', '9')
        );
    }

    private void setupTransformations() {
        this.transform(new Transformation() {
            @Override
            public Object transform(List<Object> objects, Context context) {
                double value = Double.parseDouble(context.getOriginalText());
                return new Number(value);
            }
        });
    }

}
