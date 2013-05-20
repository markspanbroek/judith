package net.spanbroek.judith.parser;

import net.spanbroek.judith.parser.analysis.*;
import net.spanbroek.judith.parser.node.*;
import net.spanbroek.judith.tree.Boolean;
import net.spanbroek.judith.tree.Number;
import net.spanbroek.judith.tree.Node;
import net.spanbroek.judith.tree.*;
import java.util.*;

class Visitor extends DepthFirstAdapter {

    private String filename;

    private Stack<java.lang.Object> stack = new Stack<java.lang.Object>();

    public Visitor(String filename) {
        this.filename = filename;
    }

    public Program getResult() {
        return (Program)stack.peek();
    }

    @Override
    public void outAProgram(AProgram node) {
        List<Statement> statements = popList();
        stack.push(
          new Program(
            statements.toArray(new Statement[]{})
          )
        );
    }

    @Override
    public void outAObject(AObject node) {
        Expression expression = (Expression)stack.pop();
        String identifier = (String)stack.pop();
        ObjectDeclaration object = new ObjectDeclaration(identifier, expression);
        stack.push(object);
    }

    @Override
    public void outAAssignment(AAssignment node) {
        Expression expression = (Expression)stack.pop();
        String identifier = (String)stack.pop();
        Assignment assignment = new Assignment(identifier, expression);
        stack.push(assignment);
    }

    @Override
    public void outAIf(AIf node) {
        List<Conditional> conditionals = popList();
        If i = new If(
          conditionals.toArray(new Conditional[]{})
        );
        stack.push(i);
    }

    @Override
    public void outADo(ADo node) {
        List<Conditional> conditionals = popList();
        Do d = new Do(
          conditionals.toArray(new Conditional[]{})
        );
        stack.push(d);
    }


    @Override
    public void outAConditional(AConditional node) {
        List<Statement> statements = popList();
        Expression expression = (Expression)stack.pop();
        Conditional conditional = new Conditional(
          expression,
          statements.toArray(new Statement[]{})
        );
        stack.push(conditional);
    }

    @Override
    public void outANormalBlock(ANormalBlock node) {
        List<Statement> statements = popList();
        statements.add(0, (Statement)stack.pop());
        Block block = new Block(
          statements.toArray(new Statement[]{})
        );
        stack.push(block);
    }

    @Override
    public void outAEmptyBlock(AEmptyBlock node) {
        Block block = new Block(new Statement[]{});
        stack.push(block);
    }

    @Override
    public void outABlockExpression7(ABlockExpression7 node) {
        Block block = (Block)stack.pop();
        stack.push(new LambdaBlock(block.getStatements()));
    }

    @Override
    public void outAIdentifierExpression8(AIdentifierExpression8 node) {
        String identifier = (String)stack.pop();
        stack.push(new Reference(identifier));
    }

    @Override
    public void outAParametersCallobject(AParametersCallobject node) {
        List<Expression> expressions = popList();
        String identifier = (String)stack.pop();
        Expression operand = (Expression)stack.pop();
        stack.push(
          new MethodCall(
            operand,
            identifier,
            expressions.toArray(new Expression[]{}),
            new Location(
              filename,
              node.getIdentifierbrace().getLine(),
              node.getIdentifierbrace().getPos()
            )
          )
        );
    }

    @Override
    public void outASimpleCallobject(ASimpleCallobject node) {
        String identifier = (String)stack.pop();
        Expression operand = (Expression)stack.pop();
        stack.push(
          new MethodCall(
            operand,
            identifier,
            new Expression[]{},
            new Location(
              filename,
              node.getIdentifier().getLine(),
              node.getIdentifier().getPos()
            )
          )
        );
    }

    @Override
    public void outAParametersCallself(AParametersCallself node) {
        List<Expression> expressions = popList();
        String identifier = (String)stack.pop();
        stack.push(
                new MethodCall(
                        new Reference("self"),
                        identifier,
                        expressions.toArray(new Expression[]{}),
                        new Location(
                                filename,
                                node.getIdentifierbrace().getLine(),
                                node.getIdentifierbrace().getPos()
                        )
                )
        );
    }

    @Override
    public void outASimpleCallself(ASimpleCallself node) {
        String identifier = (String)stack.pop();
        stack.push(
                new MethodCall(
                        new Reference("self"),
                        identifier,
                        new Expression[]{},
                        new Location(
                                filename,
                                node.getIdentifier().getLine(),
                                node.getIdentifier().getPos()
                        )
                )
        );
    }

    @Override
    public void outAAlteration(AAlteration node) {
        List<Node> alterationParts = popList();
        Expression operand = (Expression)stack.pop();
        List<ObjectDeclaration> objects = new ArrayList<ObjectDeclaration>();
        List<Method> methods = new ArrayList<Method>();
        for (Iterator<Node> i=alterationParts.iterator(); i.hasNext(); ) {
            java.lang.Object alterationPart = i.next();
            if (alterationPart instanceof ObjectDeclaration) {
                objects.add((ObjectDeclaration)alterationPart);
            }
            if (alterationPart instanceof Method) {
                methods.add((Method)alterationPart);
            }
        }
        Alteration alteration = new Alteration(
            operand,
            objects.toArray(new ObjectDeclaration[]{}),
            methods.toArray(new Method[]{})
        );
        stack.push(alteration);
    }

    @Override
    public void outALambda(ALambda node) {
        Expression expression = (Expression)stack.pop();
        List<String> identifiers = popList();
        stack.push(
          new Lambda(
            identifiers.toArray(new String[]{}),
            expression
          )
        );
    }

    @Override
    public void outALambdablock(ALambdablock node) {
        List<Statement> statements = popList();
        List<String> identifiers = popList();
        stack.push(
          new LambdaBlock(
            identifiers.toArray(new String[]{}),
            statements.toArray(new Statement[]{})
          )
        );
    }

    @Override
    public void outAParametersMethod(AParametersMethod node) {
        List<Statement> statements = popList();
        List<String> identifiers = popList();
        String identifier = (String)stack.pop();
        Method method = new Method(
          identifier,
          identifiers.toArray(new String[]{}),
          statements.toArray(new Statement[]{})
        );
        stack.push(method);
    }

    @Override
    public void outASimpleMethod(ASimpleMethod node) {
        List<Statement> statements = popList();
        String identifier = (String)stack.pop();
        Method method = new Method(
          identifier,
          new String[]{},
          statements.toArray(new Statement[]{})
        );
        stack.push(method);
    }

    @Override
    public void caseTBoolean(TBoolean node) {
        stack.push(new Boolean("true".equals(node.getText())));
    }

    @Override
    public void caseTNumber(TNumber node) {
        stack.push(new Number(Double.parseDouble(node.getText())));
    }

    @Override
    public void caseTText(TText node) {
        String text = node.getText();
        text = text.substring(1,text.length()-1);
        stack.push(new Text(text));
    }

    @Override
    public void caseTIdentifier(TIdentifier node) {
        stack.push(node.getText());
    }

    @Override
    public void caseTIdentifierbrace(TIdentifierbrace node) {
        String identifier = node.getText();
        identifier = identifier.substring(0,identifier.length()-1);
        stack.push(identifier);
    }

    // operators

    @Override
    public void outAAndExpression(AAndExpression node) {
        binaryOperation("and", node.getAndsym());
    }

    @Override
    public void outAOrExpression(AOrExpression node) {
        binaryOperation("or", node.getOrsym());
    }

    @Override
    public void outAEqualsExpression1(AEqualsExpression1 node) {
        binaryOperation("equals", node.getEquals());
    }

    @Override
    public void outAAtmostExpression1(AAtmostExpression1 node) {
        binaryOperation("atmost", node.getAtmost());
    }

    @Override
    public void outAAtleastExpression1(AAtleastExpression1 node) {
        binaryOperation("atleast", node.getAtleast());
    }

    @Override
    public void outALessthanExpression1(ALessthanExpression1 node) {
        binaryOperation("lessthan", node.getLessthan());
    }

    @Override
    public void outAMorethanExpression1(AMorethanExpression1 node) {
        binaryOperation("morethan", node.getMorethan());
    }

    @Override
    public void outAColonExpression1(AColonExpression1 node) {
        binaryOperation("colon", node.getColon());
    }

    @Override
    public void outAPlusExpression2(APlusExpression2 node) {
        binaryOperation("plus", node.getPlus());
    }

    @Override
    public void outAMinusExpression2(AMinusExpression2 node) {
        binaryOperation("minus", node.getMinus());
    }

    @Override
    public void outAStarExpression3(AStarExpression3 node) {
        binaryOperation("star", node.getTimes());
    }

    @Override
    public void outASlashExpression3(ASlashExpression3 node) {
        binaryOperation("slash", node.getDivide());
    }

    @Override
    public void outACarrotExpression4(ACarrotExpression4 node) {
        binaryOperation("carrot", node.getPower());
    }

    @Override
    public void outAMinusExpression5(AMinusExpression5 node) {
        unaryOperation("minus", node.getMinus());
    }

    @Override
    public void outANotExpression5(ANotExpression5 node) {
        unaryOperation("not", node.getNotsym());
    }

    private void binaryOperation(String methodname, Token operator) {
        Expression right = (Expression)stack.pop();
        Expression left = (Expression)stack.pop();
        MethodCall methodCall = new MethodCall(
          left,
          methodname,
          new Expression[]{right},
          new Location(filename, operator.getLine(), operator.getPos())
        );
        stack.push(methodCall);
    }

    private void unaryOperation(String methodname, Token operator) {
        Expression operand = (Expression)stack.pop();
        MethodCall methodCall = new MethodCall(
          operand,
          methodname,
          new Expression[]{},
          new Location(filename, operator.getLine(), operator.getPos())
        );
        stack.push(methodCall);
    }


    // lists

    @Override
    public void outAMultipleStatements(AMultipleStatements node) {
        addtoList();
    }

    @Override
    public void outAEmptyStatements(AEmptyStatements node) {
        startEmptyList();
    }

    @Override
    public void outAMultipleConditionals(AMultipleConditionals node) {
        addtoList();
    }

    @Override
    public void outASingleConditionals(ASingleConditionals node) {
        startList();
    }

    @Override
    public void outAMultipleExpressionlist(AMultipleExpressionlist node) {
        addtoList();
    }

    @Override
    public void outASingleExpressionlist(ASingleExpressionlist node) {
        startList();
    }

    @Override
    public void outAMultipleAlterationparts(AMultipleAlterationparts node) {
        addtoList();
    }

    @Override
    public void outAEmptyAlterationparts(AEmptyAlterationparts node) {
        startEmptyList();
    }

    @Override
    public void outAMultipleIdentifierlist(AMultipleIdentifierlist node) {
        addtoList();
    }

    @Override
    public void outASingleIdentifierlist(ASingleIdentifierlist node) {
        startList();
    }

    private void startList() {
        List<java.lang.Object> list = new ArrayList<java.lang.Object>();
        list.add(stack.pop());
        stack.push(list);
    }

    private void startEmptyList() {
        stack.push(new ArrayList<java.lang.Object>());
    }

    private void addtoList() {
        java.lang.Object element = stack.pop();
        @SuppressWarnings("unchecked")
        List<java.lang.Object> list = (List<java.lang.Object>)stack.peek();
        list.add(element);
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> popList() {
        return (List<T>)stack.pop();
    }
}
