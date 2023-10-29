package nodes;

import Framework.Node;
import Framework.PrioritisedNode;
import Framework.Priority;
import Framework.Sleep;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.event.WebWalkEvent;
import org.osbot.rs07.event.webwalk.PathPreferenceProfile;
import org.osbot.rs07.script.MethodProvider;

@PrioritisedNode(priority =  Priority.SEVEN)
public class startNode extends Node {

    private boolean hasStarted = false;
    public startNode(MethodProvider api){
        super(api);
    }
    @Override
    public boolean canExecute() throws InterruptedException {
        return !hasStarted;
    }

    @Override
    public int execute() throws InterruptedException {
        api.log("Node: StartNode ");
        if(AreasAndPositions.feroxArea.contains(api.myPosition())){
            if(!api.getBank().isOpen()){
                if(api.getBank().open()){
                    Sleep.sleepUntil(() -> api.getBank().isOpen(), 5000);
                    api.log(api.getBank().getItems());
                }
            } else{
                if(api.getInventory().isEmpty()){
                    hasStarted = true;
                } else{
                    if(api.getBank().depositAll()){
                        Sleep.sleepUntil(() -> api.getInventory().isEmpty(), 3000);
                    }
                }
            }
        } else{
            PathPreferenceProfile ppp = new PathPreferenceProfile();
            ppp.setAllowTeleports(true);
            ppp.checkInventoryForItems(true);
            ppp.checkBankForItems(true);
            ppp.setAllowObstacles(true);
            WebWalkEvent walkToFerox = new WebWalkEvent(new Position(3133, 3629, 0));
            walkToFerox.setPathPreferenceProfile(ppp);

            api.execute(walkToFerox);
        }
        return 50;
    }
}
