package net.spanbroek.judith.runtime;

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
                super("equals", "number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", world.wrap(self == number));
            }
        }
        numberToBe.declare(new NumberEqualsMethod());
    }

    private void declareAtmostMethod() {
        class NumberAtMostMethod extends Method {
            public NumberAtMostMethod() {
                super("atmost", "number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", world.wrap(self <= number));
            }
        }
        numberToBe.declare(new NumberAtMostMethod());
    }

    private void declareAtleastMethod() {
        class NumberAtLeastMethod extends Method {
            public NumberAtLeastMethod() {
                super("atleast", "number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", world.wrap(self >= number));
            }
        }
        numberToBe.declare(new NumberAtLeastMethod());
    }

    private void declareLessthanMethod() {
        class NumberLessThanMethod extends Method {
            public NumberLessThanMethod() {
                super("lessthan", "number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", world.wrap(self < number));
            }
        }
        numberToBe.declare(new NumberLessThanMethod());
    }

    private void declareMorethanMethod() {
        class NumberMoreThanMethod extends Method {
            public NumberMoreThanMethod() {
                super("morethan", "number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", world.wrap(self > number));
            }
        }
        numberToBe.declare(new NumberMoreThanMethod());
    }

    private void declarePlusMethod() {
        class NumberPlusMethod extends Method {
            public NumberPlusMethod() {
                super("plus", "number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", world.wrap(self + number));
            }
        }
        numberToBe.declare(new NumberPlusMethod());
    }

    private void declareMinusMethod() {
        class NumberMinusMethod extends Method {
            public NumberMinusMethod() {
                super("minus", "number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", world.wrap(self - number));
            }
        }
        numberToBe.declare(new NumberMinusMethod());
    }

    private void declareStarMethod() {
        class NumberStarMethod extends Method {
            public NumberStarMethod() {
                super("star", "number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", world.wrap(self * number));
            }
        }
        numberToBe.declare(new NumberStarMethod());
    }

    private void declareSlashMethod() {
        class NumberSlashMethod extends Method {
            public NumberSlashMethod() {
                super("slash", "number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", world.wrap(self / number));
            }
        }
        numberToBe.declare(new NumberSlashMethod());
    }

    private void declareCarrotMethod() {
        class NumberCarrotMethod extends Method {
            public NumberCarrotMethod() {
                super("carrot", "number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", world.wrap(Math.pow(self,number)));
            }
        }
        numberToBe.declare(new NumberCarrotMethod());
    }

    private void declareUnaryMinusMethod() {
        class NumberUnaryMinusMethod extends Method {
            public NumberUnaryMinusMethod() {
                super("minus");
            }
            protected void execute(Scope scope) {
                scope.set(
                  "result",
                  world.wrap(-(Double)scope.get("self").getNativeObject())
                );
            }
        }
        numberToBe.declare(new NumberUnaryMinusMethod());
    }

    private void declareFloorMethod() {
        class NumberFloorMethod extends Method {
            public NumberFloorMethod() {
                super("floor");
            }
            protected void execute(Scope scope) {
                scope.set(
                  "result",
                  world.wrap(
                    Math.floor((Double)scope.get("self").getNativeObject())
                  )
                );
            }
        }
        numberToBe.declare(new NumberFloorMethod());
    }

    private void declareCeilingMethod() {
        class NumberCeilingMethod extends Method {
            public NumberCeilingMethod() {
                super("ceiling");
            }
            protected void execute(Scope scope) {
                scope.set(
                  "result",
                  world.wrap(
                    Math.ceil((Double)scope.get("self").getNativeObject())
                  )
                );
            }
        }
        numberToBe.declare(new NumberCeilingMethod());
    }

    private void declareAsTextMethod() {
        class AsTextMethod extends Method {
            public AsTextMethod() {
                super("asText");
            }
            protected void execute(Scope scope) {
                double value = (Double)scope.get("self").getNativeObject();
                scope.set(
                  "result",
                  world.wrap(
                    Math.round(value) != value ? Double.toString(value) : Long.toString((long)value)
                  )
                );
            }
        }
        numberToBe.declare(new AsTextMethod());
    }

    private void declareNumber() {
        class NumberMethod extends Method {
            public NumberMethod() {
                super("Number");
            }
            protected void execute(Scope scope) {
                scope.set("result", numberToBe.copy());
            }
        }
        world.get("Objects").declare(new NumberMethod());
        world.setNumber(numberToBe);
    }

}
