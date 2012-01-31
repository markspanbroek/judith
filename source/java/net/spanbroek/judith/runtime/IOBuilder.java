package net.spanbroek.judith.runtime;

import net.spanbroek.judith.runtime.Object;
import net.spanbroek.judith.Exception;
import java.io.*;

class IOBuilder {

    private World world;

    public static void build(World world) {
        IOBuilder builder = new IOBuilder(world);
        builder.buildWriteMethod();
        builder.buildReadLineMethod();
    }

    private IOBuilder(World world) {
        this.world = world;
    }

    private void buildWriteMethod() {
        class WriteMethod extends Method {

            public WriteMethod() {
                super("write", "text");
            }

            protected void execute(Scope scope) {
                // TODO: something goes wrong here, when using 'parent' and 'replace'
                //scope.get("parent").call("write", new Object[]{scope.get("text")}, scope.get("self"));
                System.out.print((String)world.unwrap(scope.get("text")));
                System.out.flush();
            }
        }
        Object io = world.get("Objects").call("IO");
        Object standardOutput = io.call("StandardOutput");
        Object newStandardOutput = new Object(standardOutput, world);
        newStandardOutput.declare(new WriteMethod());
        standardOutput.replace(newStandardOutput);
    }

    private void buildReadLineMethod() {
        class ReadLineMethod extends Method {
            public ReadLineMethod() {
                super("readLine");
            }
            protected void execute(Scope scope) {
                try {
                    scope.set(
                        "result",
                        world.wrap(
                            new BufferedReader(
                                new InputStreamReader(System.in)
                            ).readLine()
                        )
                    );
                }
                catch(IOException exception) {
                    throw new Exception(exception.getMessage());
                }
            }
        }
        Object io = world.get("Objects").call("IO");
        Object standardInput = io.call("StandardInput");
        Object newStandardInput = new Object(standardInput, world);
        newStandardInput.declare(new ReadLineMethod());
        standardInput.replace(newStandardInput);
    }

}
