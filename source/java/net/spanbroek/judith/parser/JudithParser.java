package net.spanbroek.judith.parser;

import net.spanbroek.judith.tree.Boolean;
import net.spanbroek.judith.tree.*;
import net.spanbroek.judith.tree.Number;
import net.spanbroek.parsing.Context;
import net.spanbroek.parsing.Rule;
import net.spanbroek.parsing.Transformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.spanbroek.parsing.Parsing.*;

public class JudithParser extends Rule {

    Rule statements = rule();
    Rule statement = rule();
    Rule object = rule();
    Rule assignment = rule();
    Rule if_ = rule();
    Rule do_ = rule();
    Rule conditionals = rule();
    Rule conditional = rule();
    Rule block = rule();
    Rule expression = rule();
    Rule expression1 = rule();
    Rule expression2 = rule();
    Rule expression3 = rule();
    Rule expression4 = rule();
    Rule expression5 = rule();
    Rule expression6 = rule();
    Rule expression7 = rule();
    Rule expression8 = rule();
    Rule methodCall = rule();
    Rule expressions = rule();
    Rule braces = rule();
    Rule boolean_ = rule();
    Rule number = rule();
    Rule text = rule();
    Rule reference = rule();
    Rule identifier = rule();
    Rule digit = rule();
    Rule letter = rule();
    Rule w = rule();
    Rule comment = rule();

    Map<String, String> operators;

    public JudithParser() {
        setupRules();
        setupOperators();
        setupTransformations();
    }

    private void setupRules() {
        statements.is(
                optional(statement, repeat(w, statement))
        );
        statement.is(
                choice(object, assignment, if_, do_, block)
        );
        object.is(
                "object", w, identifier, w, ":=", w, expression
        );
        assignment.is(
                identifier, w, ":=", w, expression
        );
        if_.is(
                "if", w, conditionals, w, "fi"
        );
        do_.is(
                "do", w, conditionals, w, "od"
        );
        conditionals.is(
                conditional, repeat(w, "||", w, conditional)
        );
        conditional.is(
                expression, w, statements
        );
        block.is(
                "[", w, statements, w, "]"
        );
        expression.is(
                optional(expression, w, choice("and", "or"), w), expression1
        );
        expression1.is(
                optional(expression1, w, choice("=", "<=", ">=", "<", ">", ":"), w), expression2
        );
        expression2.is(
                optional(expression2, w, choice("+", "-"), w), expression3
        );
        expression3.is(
                optional(expression3, w, choice("*", "/"), w), expression4
        );
        expression4.is(
                optional(expression4, w, "^", w), expression5
        );
        expression5.is(
                choice(concat(choice("not", "-"), w, expression5), expression6)
        );
        expression6.is(
                choice(methodCall, expression7)
        );
        expression7.is(
                expression8
        );
        expression8.is(
                choice(braces, text, number, boolean_, reference)
        );
        methodCall.is(
                expression6, ".", identifier, expressions
        );
        expressions.is(
                optional("(", w, expression, repeat(w, ",", w, expression), w, ")")
        );
        braces.is(
                "(", w, expression, w, ")"
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
        reference.is(
                identifier
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

    private void setupOperators() {
        operators = new HashMap<String, String>();
        operators.put("and", "and");
        operators.put("or", "or");
        operators.put("-", "minus");
        operators.put("+", "plus");
        operators.put("=", "equals");
        operators.put("<=", "atmost");
        operators.put(">=", "atleast");
        operators.put("<", "lessthan");
        operators.put(">", "morethan");
        operators.put(":", "colon");
        operators.put("*", "star");
        operators.put("/", "slash");
        operators.put("^", "carrot");
        operators.put("not", "not");
    }

    private void setupTransformations() {
        statements.transform(new Transformation() {
            @Override
            public Object transform(List<Object> objects, Context context) {
                List<Statement> result = new ArrayList<Statement>();
                for (int i=0; i<objects.size(); i+=2) {
                    result.add((Statement)objects.get(i));
                }
                return result.toArray(new Statement[result.size()]);
            }
        });
        object.transform(new Transformation() {
            @Override
            public Object transform(List<Object> objects, Context context) {
                String identifier = (String) objects.get(2);
                Expression expression = (Expression) objects.get(6);
                return new ObjectDeclaration(identifier, expression);
            }
        });
        assignment.transform(new Transformation() {
            @Override
            public Object transform(List<Object> objects, Context context) {
                String identifier = (String) objects.get(0);
                Expression expression = (Expression) objects.get(4);
                return new Assignment(identifier, expression);
            }
        });
        if_.transform(new Transformation() {
            @Override
            public Object transform(List<Object> objects, Context context) {
                Conditional[] conditionals = (Conditional[]) objects.get(2);
                return new If(conditionals);
            }
        });
        do_.transform(new Transformation() {
            @Override
            public Object transform(List<Object> objects, Context context) {
                Conditional[] conditionals = (Conditional[]) objects.get(2);
                return new Do(conditionals);
            }
        });
        conditionals.transform(new Transformation() {
            @Override
            public Object transform(List<Object> objects, Context context) {
                List<Conditional> result = new ArrayList<Conditional>();
                for (int i=0; i<objects.size(); i+=4) {
                    result.add((Conditional)objects.get(i));
                }
                return result.toArray(new Conditional[result.size()]);
            }
        });
        conditional.transform(new Transformation() {
            @Override
            public Object transform(List<Object> objects, Context context) {
                Expression expression = (Expression)objects.get(0);
                Statement[] statements = (Statement[])objects.get(2);
                return new Conditional(expression, statements);
            }
        });
        block.transform(new Transformation() {
            @Override
            public Object transform(List<Object> objects, Context context) {
                return new Block((Statement[]) objects.get(2));
            }
        });
        expression.transform(new Transformation() {
            @Override
            public Object transform(List<Object> objects, Context context) {
                return transformBinaryOperation(objects);
            }
        });
        expression1.transform(new Transformation() {
            @Override
            public Object transform(List<Object> objects, Context context) {
                return transformBinaryOperation(objects);
            }
        });
        expression2.transform(new Transformation() {
            @Override
            public Object transform(List<Object> objects, Context context) {
                return transformBinaryOperation(objects);
            }
        });
        expression3.transform(new Transformation() {
            @Override
            public Object transform(List<Object> objects, Context context) {
                return transformBinaryOperation(objects);
            }
        });
        expression4.transform(new Transformation() {
            @Override
            public Object transform(List<Object> objects, Context context) {
                return transformBinaryOperation(objects);
            }
        });
        expression5.transform(new Transformation() {
            @Override
            public Object transform(List<Object> objects, Context context) {
                return transformPrefixOperation(objects);
            }
        });
        methodCall.transform(new Transformation() {
            @Override
            public Object transform(List<Object> objects, Context context) {
                Expression callee = (Expression) objects.get(0);
                String methodName = (String) objects.get(2);
                Expression[] parameters = (Expression[]) objects.get(3);
                return new MethodCall(callee, methodName, parameters);
            }
        });
        expressions.transform(new Transformation() {
            @Override
            public Object transform(List<Object> objects, Context context) {
                List<Expression> result = new ArrayList<Expression>();
                for (int i = 2; i < objects.size(); i += 4) {
                    result.add((Expression) objects.get(i));
                }
                return result.toArray(new Expression[result.size()]);
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
        reference.transform(new Transformation() {
            @Override
            public Object transform(List<Object> objects, Context context) {
                return new Reference((String) objects.get(0));
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

    private Object transformBinaryOperation(List<Object> objects) {
        if (objects.size() > 1) {
            Expression left = (Expression) objects.get(0);
            Expression right = (Expression) objects.get(4);
            String operator = (String) objects.get(2);
            return new MethodCall(left, operators.get(operator), new Expression[]{right});
        }
        return objects.get(0);
    }

    private Object transformPrefixOperation(List<Object> objects) {
        if (objects.size() > 1) {
            String operator = (String) objects.get(0);
            Expression operand = (Expression) objects.get(2);
            return new MethodCall(operand, operators.get(operator), new Expression[]{});
        }
        return objects.get(0);
    }
}
