package nodes;

import Framework.Node;
import Framework.PrioritisedNode;
import Framework.Priority;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.event.WebWalkEvent;
import org.osbot.rs07.event.webwalk.PathPreferenceProfile;
import org.osbot.rs07.script.MethodProvider;

@PrioritisedNode(priority = Priority.ONE)
public class WalkToDuelBank extends Node {

    public WalkToDuelBank(MethodProvider api) {
        super(api);
    }

    @Override
    public boolean canExecute() throws InterruptedException {
        return !AreasAndPositions.duelArenaArea.contains(api.myPosition());
    }

    @Override
    public int execute() throws InterruptedException {
        PathPreferenceProfile ppp = new PathPreferenceProfile();
        ppp.setAllowTeleports(true);
        ppp.checkInventoryForItems(true);
        ppp.checkBankForItems(true);
        ppp.setAllowObstacles(true);
        WebWalkEvent walkToDuelArena = new WebWalkEvent( new Position(3384, 3269, 0));
        walkToDuelArena.setPathPreferenceProfile(ppp);
        api.execute(walkToDuelArena);
        return 50;
    }
}
