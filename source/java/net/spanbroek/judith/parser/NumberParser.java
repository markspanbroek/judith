package net.spanbroek.judith.parser;

import net.spanbroek.parsing.Position;
import net.spanbroek.parsing.Rule;
import net.spanbroek.parsing.Transformation;

import java.util.List;

import static net.spanbroek.parsing.Parsing.*;

public class NumberParser extends Rule {

    private Rule integer_ = rule();
    private Rule fractional = rule();
    private Rule digit = rule();

    public NumberParser() {
        setupRules();
        setupTransformations();
    }

    private void setupRules() {
        this.is(
                integer_, optional(fractional)
        );
        integer_.is(
                digit, repeat(digit)
        );
        fractional.is(
                ".", digit, repeat(digit)
        );
        digit.is(
                range('0', '9')
        );
    }

    private void setupTransformations() {
        this.transform(new Transformation() {
            @Override
            public Object transform(List<Object> objects, Position position) {
                Integer integerPart = (Integer) objects.get(0);
                Double fractionalPart = objects.size() > 1 ? (Double) objects.get(1) : 0;

                return new net.spanbroek.judith.tree.Number(integerPart + fractionalPart);
            }
        });
        integer_.transform(new Transformation() {
            @Override
            public Object transform(List<Object> objects, Position position) {
                int value = 0;
                for (Object object : objects) {
                    int digit = (Integer) object;
                    value = value * 10 + digit;
                }
                return value;
            }
        });
        fractional.transform(new Transformation() {
            @Override
            public Object transform(List<Object> objects, Position position) {
                double value = 0;
                double divisor = 10;
                for (int i = 1; i < objects.size(); i++) {
                    int digit = (Integer) objects.get(i);
                    value = value + digit / divisor;
                    divisor = divisor * 10;
                }
                return value;
            }
        });
        digit.transform(new Transformation() {
            @Override
            public Object transform(List<Object> objects, Position position) {
                char character = ((String) objects.get(0)).charAt(0);
                return character - '0';
            }
        });
    }

}
