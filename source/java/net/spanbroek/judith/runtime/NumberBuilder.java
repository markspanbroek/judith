package net.spanbroek.judith.runtime;

import net.spanbroek.judith.runtime.Object;

class NumberBuilder {

    private World world;

    private Object numberToBe;
    
    public static void build(World world) {
        NumberBuilder builder = new NumberBuilder(world);
        builder.declareEqualsMethod();
        builder.declareAtmostMethod();
        builder.declareAtleastMethod();
        builder.declareLessthanMethod();
        builder.declareMorethanMethod();
        builder.declarePlusMethod();
        builder.declareMinusMethod();
        builder.declareStarMethod();
        builder.declareSlashMethod();
        builder.declareCarrotMethod();
        builder.declareUnaryMinusMethod();
        builder.declareFloorMethod();
        builder.declareCeilingMethod();
        builder.declareAsTextMethod();
        builder.declareNumber();
    }

    private NumberBuilder(World world) {
        this.world = world;
        numberToBe = new Object(world.get("Object"), world);
        numberToBe.setNativeObject(0d);
    }
    
    private void declareEqualsMethod() {
        class NumberEqualsMethod extends Method {
            public NumberEqualsMethod() {
                super("number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", world.wrap(self == number));
            }
        }
        numberToBe.declare("equals", new NumberEqualsMethod());
    }
    
    private void declareAtmostMethod() {
        class NumberAtMostMethod extends Method {
            public NumberAtMostMethod() {
                super("number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", world.wrap(self <= number));
            }
        }
        numberToBe.declare("atmost", new NumberAtMostMethod());
    }
    
    private void declareAtleastMethod() {
        class NumberAtLeastMethod extends Method {
            public NumberAtLeastMethod() {
                super("number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", world.wrap(self >= number));
            }
        }
        numberToBe.declare("atleast", new NumberAtLeastMethod());
    }
    
    private void declareLessthanMethod() {
        class NumberLessThanMethod extends Method {
            public NumberLessThanMethod() {
                super("number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", world.wrap(self < number));
            }
        }
        numberToBe.declare("lessthan", new NumberLessThanMethod());
    }
    
    private void declareMorethanMethod() {
        class NumberMoreThanMethod extends Method {
            public NumberMoreThanMethod() {
                super("number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", world.wrap(self > number));
            }
        }
        numberToBe.declare("morethan", new NumberMoreThanMethod());
    }
    
    private void declarePlusMethod() {
        class NumberPlusMethod extends Method {
            public NumberPlusMethod() {
                super("number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", world.wrap(self + number));
            }
        }
        numberToBe.declare("plus", new NumberPlusMethod());
    }

    private void declareMinusMethod() {
        class NumberMinusMethod extends Method {
            public NumberMinusMethod() {
                super("number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", world.wrap(self - number));
            }
        }
        numberToBe.declare("minus", new NumberMinusMethod());
    }

    private void declareStarMethod() {
        class NumberStarMethod extends Method {
            public NumberStarMethod() {
                super("number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", world.wrap(self * number));
            }
        }
        numberToBe.declare("star", new NumberStarMethod());
    }

    private void declareSlashMethod() {
        class NumberSlashMethod extends Method {
            public NumberSlashMethod() {
                super("number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", world.wrap(self / number));
            }
        }
        numberToBe.declare("slash", new NumberSlashMethod());
    }

    private void declareCarrotMethod() {
        class NumberCarrotMethod extends Method {
            public NumberCarrotMethod() {
                super("number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", world.wrap(Math.pow(self,number)));
            }
        }
        numberToBe.declare("carrot", new NumberCarrotMethod());
    }

    private void declareUnaryMinusMethod() {
        class NumberUnaryMinusMethod extends Method {
            protected void execute(Scope scope) {
                scope.set(
                  "result",
                  world.wrap(-(Double)scope.get("self").getNativeObject())
                );
            }
        }
        numberToBe.declare("minus", new NumberUnaryMinusMethod());
    }
    
    private void declareFloorMethod() {
        class NumberFloorMethod extends Method {
            protected void execute(Scope scope) {
                scope.set(
                  "result",
                  world.wrap(
                    Math.floor((Double)scope.get("self").getNativeObject())
                  )
                );
            }
        }
        numberToBe.declare("floor", new NumberFloorMethod());
    }
    
    private void declareCeilingMethod() {
        class NumberCeilingMethod extends Method {
            protected void execute(Scope scope) {
                scope.set(
                  "result",
                  world.wrap(
                    Math.ceil((Double)scope.get("self").getNativeObject())
                  )
                );
            }
        }
        numberToBe.declare("ceiling", new NumberCeilingMethod());
    }

    private void declareAsTextMethod() {
        class AsTextMethod extends Method {
            protected void execute(Scope scope) {
                scope.set(
                  "result",
                  world.wrap(
                    Double.toString((Double)scope.get("self").getNativeObject())
                  )
                );
            }
        }
    }

    private void declareNumber() {
        class NumberMethod extends Method {
            protected void execute(Scope scope) {
                scope.set("result", numberToBe.copy());
            }
        }
        world.get("Objects").declare("Number", new NumberMethod());
        world.setNumber(numberToBe);
    }

}
