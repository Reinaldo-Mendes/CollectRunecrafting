package Framework;

import org.osbot.rs07.script.MethodProvider;

public abstract class Node {
    public Node(MethodProvider api){
        this.api = api;
    }
    protected MethodProvider api;
    public abstract boolean canExecute() throws InterruptedException;

    public abstract int execute() throws InterruptedException;
}
