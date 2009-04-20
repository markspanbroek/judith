package net.spanbroek.judith.runtime;

import net.spanbroek.judith.runtime.Object;

class NumberBuilder {

    private Scope scope;
    
    public static void build(Scope scope) {
        NumberBuilder builder = new NumberBuilder(scope);
        builder.declareNumber();
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
    }

    private NumberBuilder(Scope scope) {
        this.scope = scope;
    }

    private void declareNumber() {
        scope.declare("Number", new Object(scope.get("Object"), scope));
        scope.get("Number").setNativeObject(0d);
    }
    
    private void declareEqualsMethod() {
        class NumberEqualsMethod extends Method {
            public NumberEqualsMethod() {
                super("number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", World.wrap(self == number));
            }
        }
        scope.get("Number").declare("equals", new NumberEqualsMethod());
    }
    
    private void declareAtmostMethod() {
        class NumberAtMostMethod extends Method {
            public NumberAtMostMethod() {
                super("number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", World.wrap(self <= number));
            }
        }
        scope.get("Number").declare("atmost", new NumberAtMostMethod());
    }
    
    private void declareAtleastMethod() {
        class NumberAtLeastMethod extends Method {
            public NumberAtLeastMethod() {
                super("number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", World.wrap(self >= number));
            }
        }
        scope.get("Number").declare("atleast", new NumberAtLeastMethod());
    }
    
    private void declareLessthanMethod() {
        class NumberLessThanMethod extends Method {
            public NumberLessThanMethod() {
                super("number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", World.wrap(self < number));
            }
        }
        scope.get("Number").declare("lessthan", new NumberLessThanMethod());
    }
    
    private void declareMorethanMethod() {
        class NumberMoreThanMethod extends Method {
            public NumberMoreThanMethod() {
                super("number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", World.wrap(self > number));
            }
        }
        scope.get("Number").declare("morethan", new NumberMoreThanMethod());
    }
    
    private void declarePlusMethod() {
        class NumberPlusMethod extends Method {
            public NumberPlusMethod() {
                super("number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", World.wrap(self + number));
            }
        }
        scope.get("Number").declare("plus", new NumberPlusMethod());
    }

    private void declareMinusMethod() {
        class NumberMinusMethod extends Method {
            public NumberMinusMethod() {
                super("number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", World.wrap(self - number));
            }
        }
        scope.get("Number").declare("minus", new NumberMinusMethod());
    }

    private void declareStarMethod() {
        class NumberStarMethod extends Method {
            public NumberStarMethod() {
                super("number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", World.wrap(self * number));
            }
        }
        scope.get("Number").declare("star", new NumberStarMethod());
    }

    private void declareSlashMethod() {
        class NumberSlashMethod extends Method {
            public NumberSlashMethod() {
                super("number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", World.wrap(self / number));
            }
        }
        scope.get("Number").declare("slash", new NumberSlashMethod());
    }

    private void declareCarrotMethod() {
        class NumberCarrotMethod extends Method {
            public NumberCarrotMethod() {
                super("number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", World.wrap(Math.pow(self,number)));
            }
        }
        scope.get("Number").declare("carrot", new NumberCarrotMethod());
    }

    private void declareUnaryMinusMethod() {
        class NumberUnaryMinusMethod extends Method {
            protected void execute(Scope scope) {
                scope.set(
                  "result",
                  World.wrap(-(Double)scope.get("self").getNativeObject())
                );
            }
        }
        scope.get("Number").declare("minus", new NumberUnaryMinusMethod());
    }
    
    private void declareFloorMethod() {
        class NumberFloorMethod extends Method {
            protected void execute(Scope scope) {
                scope.set(
                  "result",
                  World.wrap(
                    Math.floor((Double)scope.get("self").getNativeObject())
                  )
                );
            }
        }
        scope.get("Number").declare("floor", new NumberFloorMethod());
    }
    
    private void declareCeilingMethod() {
        class NumberCeilingMethod extends Method {
            protected void execute(Scope scope) {
                scope.set(
                  "result",
                  World.wrap(
                    Math.ceil((Double)scope.get("self").getNativeObject())
                  )
                );
            }
        }
        scope.get("Number").declare("ceiling", new NumberCeilingMethod());
    }

}