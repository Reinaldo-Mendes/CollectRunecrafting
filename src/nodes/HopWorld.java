package nodes;

import Framework.Node;
import Framework.PrioritisedNode;
import Framework.Priority;
import org.osbot.rs07.script.MethodProvider;

@PrioritisedNode(priority =  Priority.SEVEN)
public class HopWorld extends Node {

    public HopWorld(MethodProvider api) {
        super(api);
    }

    @Override
    public boolean canExecute() throws InterruptedException {
        return AreasAndPositions.duelArenaArea.contains(api.myPosition()) && api.getWorlds().getCurrentWorld() != 371;
    }

    @Override
    public int execute() throws InterruptedException {
        api.getWorlds().hop(371);
        return 50;
    }
}
