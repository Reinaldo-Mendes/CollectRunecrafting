package nodes;

import Framework.Node;
import Framework.PrioritisedNode;
import Framework.Priority;
import Framework.Sleep;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.ui.EquipmentSlot;
import org.osbot.rs07.event.WebWalkEvent;
import org.osbot.rs07.event.webwalk.PathPreferenceProfile;
import org.osbot.rs07.script.MethodProvider;

@PrioritisedNode(priority = Priority.SEVEN)
public class startNode extends Node {

    private boolean hasStarted = false;

    public startNode(MethodProvider api) {
        super(api);
    }

    @Override
    public boolean canExecute() throws InterruptedException {
        return !hasStarted;
    }

    @Override
    public int execute() throws InterruptedException {
        api.log("Node: StartNode ");
        if (AreasAndPositions.duelArenaArea.contains(api.myPosition())) {
            if (!api.getBank().isOpen()) {
                if (api.getBank().open()) {
                    Sleep.sleepUntil(() -> api.getBank().isOpen(), 5000);
                    return 50;
                }
            } else {
                    if (api.getBank().depositAll()) {
                        Sleep.sleepUntil(() -> api.getInventory().isEmpty(), 3000);
                        hasStarted = true;
                    }
            }
        } else {
            if (AreasAndPositions.feroxArea.contains(api.myPosition())) {
                if (!api.getInventory().contains(i -> i.getName().contains("dueling")) && !api.getEquipment().isWearingItem(EquipmentSlot.RING, d -> d.getName().contains("dueling"))) {
                    if (!api.getBank().isOpen()) {
                        if (api.getBank().open()) {
                            if (api.getBank().withdraw("Ring of dueling(8)", 1)) {
                                Sleep.sleepUntil(() -> api.getInventory().contains("Ring of dueling(8)"), 3000);
                            }
                        }
                    } else {
                        if (api.getBank().withdraw("Ring of dueling(8)", 1)) {
                            Sleep.sleepUntil(() -> api.getInventory().contains("Ring of dueling(8)"), 3000);
                        }
                    }
                }
            }

            PathPreferenceProfile ppp = new PathPreferenceProfile();
            ppp.setAllowTeleports(true);
            ppp.checkInventoryForItems(true);
            ppp.checkBankForItems(true);
            ppp.setAllowObstacles(true);
            WebWalkEvent walkToDuelArena = new WebWalkEvent(AreasAndPositions.duelArenaArea.getRandomPosition());
            walkToDuelArena.setPathPreferenceProfile(ppp);
            api.execute(walkToDuelArena);


        }
        return 50;
    }
}
