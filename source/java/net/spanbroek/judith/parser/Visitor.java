package net.spanbroek.judith.parser;

import net.spanbroek.judith.tree.Lambda;
import net.spanbroek.judith.parser.analysis.*;
import net.spanbroek.judith.parser.node.*;
import net.spanbroek.judith.tree.Object;
import net.spanbroek.judith.tree.Boolean;
import net.spanbroek.judith.tree.Number;
import net.spanbroek.judith.tree.*;
import java.util.*;

class Visitor extends DepthFirstAdapter {

    private String filename;

    private Stack stack = new Stack();

    public Visitor(String filename) {
        this.filename = filename;
    }

    public Program getResult() {
        return (Program)stack.peek();
    }

    public void outAProgram(AProgram node) {
        List statements = (List)stack.pop();
        stack.push(
          new Program(
            (Statement[])statements.toArray(new Statement[]{})
          )
        );
    }

    public void outAObject(AObject node) {
        Expression expression = (Expression)stack.pop();
        String identifier = (String)stack.pop();
        Object object = new Object(identifier, expression);
        stack.push(object);
    }

    public void outAAssignment(AAssignment node) {
        Expression expression = (Expression)stack.pop();
        String identifier = (String)stack.pop();
        Assignment assignment = new Assignment(identifier, expression);
        stack.push(assignment);
    }
    
    public void outAIf(AIf node) {
        List conditionals = (List)stack.pop();
        If i = new If(
          (Conditional[])conditionals.toArray(new Conditional[]{})
        );
        stack.push(i);
    }

    public void outADo(ADo node) {
        List conditionals = (List)stack.pop();
        Do d = new Do(
          (Conditional[])conditionals.toArray(new Conditional[]{})
        );
        stack.push(d);
    }


    public void outAConditional(AConditional node) {
        List statements = (List)stack.pop();
        Expression expression = (Expression)stack.pop();
        Conditional conditional = new Conditional(
          expression,
          (Statement[])statements.toArray(new Statement[]{})
        );
        stack.push(conditional);
    }

    public void outABlock(ABlock node) {
        List statements = (List)stack.pop();
        statements.add(stack.pop());
        Block block = new Block(
          (Statement[])statements.toArray(new Statement[]{})
        );
        stack.push(block);
    }

    public void outAIdentifierExpression7(AIdentifierExpression7 node) {
        String identifier = (String)stack.pop();
        stack.push(new Reference(identifier));
    }

    public void outAParametersMethodcall(AParametersMethodcall node) {
        List expressions = (List)stack.pop();
        String identifier = (String)stack.pop();
        Expression operand = (Expression)stack.pop();
        stack.push(
          new MethodCall(
            operand, 
            identifier,
            (Expression[])expressions.toArray(new Expression[]{}),
            new Location(
              filename,
              node.getIdentifierbrace().getLine(),
              node.getIdentifierbrace().getPos()
            )
          )
        );
    }

    public void outASimpleMethodcall(ASimpleMethodcall node) {
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

    public void outAAlteration(AAlteration node) {
        List alterationParts = (List)stack.pop();
        Expression operand = (Expression)stack.pop();
        List objects = new ArrayList();
        List methods = new ArrayList();
        for (Iterator i=alterationParts.iterator(); i.hasNext(); ) {
            java.lang.Object alterationPart = i.next();
            if (alterationPart instanceof Object) {
                objects.add(alterationPart);
            }
            if (alterationPart instanceof Method) {
                methods.add(alterationPart);
            }
        }
        Alteration alteration = new Alteration(
            operand,
            (Object[])objects.toArray(new Object[]{}),
            (Method[])methods.toArray(new Method[]{})
        );
        stack.push(alteration);
    }

    public void outALambda(ALambda node) {
        Expression expression = (Expression)stack.pop();
        List identifiers = (List)stack.pop();
        stack.push(
          new Lambda(
            (String[])identifiers.toArray(new String[]{}),
            expression
          )
        );
    }

    public void outAParametersMethod(AParametersMethod node) {
        List statements = (List)stack.pop();
        List identifiers = (List)stack.pop();
        String identifier = (String)stack.pop();
        Method method = new Method(
          identifier,
          (String[])identifiers.toArray(new String[]{}),
          (Statement[])statements.toArray(new Statement[]{})
        );
        stack.push(method);
    }

    public void outASimpleMethod(ASimpleMethod node) {
        List statements = (List)stack.pop();
        String identifier = (String)stack.pop();
        Method method = new Method(
          identifier,
          new String[]{},
          (Statement[])statements.toArray(new Statement[]{})
        );
        stack.push(method);
    }

    public void caseTBoolean(TBoolean node) {
        stack.push(new Boolean("true".equals(node.getText())));
    }

    public void caseTNumber(TNumber node) {
        stack.push(new Number(Double.parseDouble(node.getText())));
    }

    public void caseTText(TText node) {
        String text = node.getText();
        text = text.substring(1,text.length()-1);
        stack.push(new Text(text));
    }

    public void caseTIdentifier(TIdentifier node) {
        stack.push(node.getText());
    }

    public void caseTIdentifierbrace(TIdentifierbrace node) {
        String identifier = node.getText();
        identifier = identifier.substring(0,identifier.length()-1);
        stack.push(identifier);
    }

    // operators

    public void outAAndExpression(AAndExpression node) {
        binaryOperation("and", node.getAndsym());
    }

    public void outAOrExpression(AOrExpression node) {
        binaryOperation("or", node.getOrsym());
    }

    public void outAEqualsExpression1(AEqualsExpression1 node) {
        binaryOperation("equals", node.getEquals());
    }

    public void outAAtmostExpression1(AAtmostExpression1 node) {
        binaryOperation("atmost", node.getAtmost());
    }

    public void outAAtleastExpression1(AAtleastExpression1 node) {
        binaryOperation("atleast", node.getAtleast());
    }

    public void outALessthanExpression1(ALessthanExpression1 node) {
        binaryOperation("lessthan", node.getLessthan());
    }

    public void outAMorethanExpression1(AMorethanExpression1 node) {
        binaryOperation("morethan", node.getMorethan());
    }

    public void outAColonExpression1(AColonExpression1 node) {
        binaryOperation("colon", node.getColon());
    }

    public void outAPlusExpression2(APlusExpression2 node) {
        binaryOperation("plus", node.getPlus());
    }

    public void outAMinusExpression2(AMinusExpression2 node) {
        binaryOperation("minus", node.getMinus());
    }

    public void outAStarExpression3(AStarExpression3 node) {
        binaryOperation("star", node.getTimes());
    }

    public void outASlashExpression3(ASlashExpression3 node) {
        binaryOperation("slash", node.getDivide());
    }

    public void outACarrotExpression4(ACarrotExpression4 node) {
        binaryOperation("carrot", node.getPower());
    }

    public void outAMinusExpression5(AMinusExpression5 node) {
        unaryOperation("minus", node.getMinus());
    }

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

    public void outAMultipleStatements(AMultipleStatements node) {
        addtoList();
    }

    public void outAEmptyStatements(AEmptyStatements node) {
        startEmptyList();
    }

    public void outAMultipleConditionals(AMultipleConditionals node) {
        addtoList();
    }

    public void outASingleConditionals(ASingleConditionals node) {
        startList();
    }

    public void outAMultipleExpressionlist(AMultipleExpressionlist node) {
        addtoList();
    }

    public void outASingleExpressionlist(ASingleExpressionlist node) {
        startList();
    }

    public void outAMultipleAlterationparts(AMultipleAlterationparts node) {
        addtoList();
    }

    public void outAEmptyAlterationparts(AEmptyAlterationparts node) {
        startEmptyList();
    }

    public void outAMultipleIdentifierlist(AMultipleIdentifierlist node) {
        addtoList();
    }

    public void outASingleIdentifierlist(ASingleIdentifierlist node) {
        startList();
    }

    private void startList() {
        List list = new ArrayList();
        list.add(stack.pop());
        stack.push(list);
    }

    private void startEmptyList() {
        stack.push(new ArrayList());
    }

    private void addtoList() {
        java.lang.Object element = stack.pop();
        List list = (List)stack.peek();
        list.add(element);
    }

}
