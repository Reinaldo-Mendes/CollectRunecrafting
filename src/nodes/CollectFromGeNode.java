package nodes;

import Framework.Node;
import Framework.PrioritisedNode;
import Framework.Priority;
import Framework.Sleep;
import org.osbot.rs07.api.GrandExchange;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.MethodProvider;

import static nodes.Main.hasCollected;

@PrioritisedNode(priority =  Priority.SIX)
public class CollectFromGeNode extends Node {
    public CollectFromGeNode(MethodProvider api){
        super(api);
    }

    @Override
    public boolean canExecute() throws InterruptedException {
        return (AreasAndPositions.duelArenaArea.contains(api.myPosition()) || isBankNearby()) && !hasCollected;
    }

    @Override
    public int execute() throws InterruptedException {
        api.log("Node: CollectFromGe ");
        if(api.getBank().isOpen()){
            if(api.getBank().close()){
                api.log("Closed bank to collect from GE");
            }
        }

        RS2Widget collectToBankWidget = api.getWidgets().get(402, 4); //This is the widget for the "Collect to bank" on the collect screen
        if(!isWidgetVisible(collectToBankWidget)){
            if(api.getNpcs().closest("Banker") != null){
                if(api.getNpcs().closest("Banker").interact("Collect")){
                    Sleep.sleepUntil(() -> isWidgetVisible(collectToBankWidget), 3000);
                }
            } else{
                if(api.getObjects().closest("Open chest") != null){
                    if(api.getObjects().closest("Open chest").interact("Collect")){
                        Sleep.sleepUntil(() -> isWidgetVisible(collectToBankWidget), 3000);
                    }
                }
            }
            if(api.getObjects().closest(f -> f.hasAction("Collect")) != null){
                if(api.getObjects().closest(f -> f.hasAction("Collect")).interact("Collect")){
                    Sleep.sleepUntil(() -> isWidgetVisible(collectToBankWidget), 3000);
                }
            }
        } else{
            collectToBankWidget.interact("Collect to bank");
           Sleep.sleepUntil(() -> false, 2000);
            if(!api.getBank().isOpen()){
                if(api.getBank().open()){
                    Sleep.sleepUntil(() -> false, 5000);
                }
            }
        }







        return 50;
    }

    private boolean isBankNearby() {
        //RS2Object bankChest = api.getObjects().closest("Bank chest");
        RS2Object openChest = api.getObjects().closest("Open chest");
        NPC banker = api.getNpcs().closest("Banker");
        if (openChest != null) {
            if (api.getMap().distance(openChest) <= 13) {
                return true;
            }
        }
        if (banker != null) {
            if (banker.exists() && api.getMap().distance(banker) <= 13) {
                return true;
            }
        }
        return false;
    }

    private boolean isWidgetVisible(RS2Widget widget){
        if(widget != null){
            return widget.isVisible();
        }
        return false;
    }
}
