package nodes;

import Framework.*;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.script.MethodProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@PrioritisedNode(priority = Priority.ONE)
public class TradeGpOver extends Node {
    ClanChatter cc;
    private List<String> rsnList = new ArrayList<>(Arrays.asList("weak ass kid", "Propadinga", "return bor", "Shod Chris"));
    private List<String> itemList = new ArrayList<>(Arrays.asList("Cosmic rune", "Law rune", "Nature rune", "Chaos rune","Death rune", "Coins", "Uncut diamond", "Diamond",
        "Blood rune", "Air rune", "Earth rune", "Water rune"));
    public TradeGpOver(MethodProvider api) {
        super(api);
        cc = new ClanChatter(api);
    }

    @Override
    public boolean canExecute() throws InterruptedException {
        return AreasAndPositions.duelArenaArea.contains(api.myPosition()) && isMuleClose() && (ourInventoryContainsItems() || ourOfferContainsItems());
    }

    @Override
    public int execute() throws InterruptedException {
        api.log("Node: TradeGp ");
        if (api.getBank().isOpen()) {
            api.getBank().close();
            Sleep.sleepUntil(() -> !api.getBank().isOpen(), 3000);
        }

        for (Player player : api.getPlayers().getAll()) {
            String playerName = player.getName().replace("\u00A0", " ");
            for(String rsn: rsnList){
                if(playerName.equals(rsn)){
                    if (!api.getTrade().isCurrentlyTrading()) {
                        if (player.interact("Trade with")) {
                            api.log("Interacted trade");
                            Sleep.sleepUntil(() -> api.getTrade().isCurrentlyTrading(), 4000);
                        } else {
                            api.log("Failed to interact");
                        }
                    } else{
                        if (api.getTrade().isFirstInterfaceOpen()) {
                            api.getTrade().offerAll(rsnList.toString());
                            //if (api.getTrade().offerAll("Cosmic rune", "Law rune", "Nature rune", "Chaos rune","Death rune", "Coins", "Uncut diamond", "Diamond")) {
                            for(String itemName: itemList){
                                if(api.getTrade().offerAll(itemName)){
                                    Sleep.sleepUntil(() -> !api.getInventory().contains(itemName), 2000);
                                }
                            }
                            if (api.getTrade().acceptTrade()) {
                                Sleep.sleepUntil(() -> api.getTrade().isSecondInterfaceOpen(), 2000);
                            }
                            /*if(api.getTrade().offerAll(itemList.toString())){
                                Sleep.sleepUntil(() -> !api.getInventory().contains("Cosmic rune") && !api.getInventory().contains("Coins") && !api.getInventory().contains("Law rune") && !api.getInventory().contains("Nature rune") &&
                                        !api.getInventory().contains("Uncut diamond") && !api.getInventory().contains("Diamond"), 3000);
                                if (api.getTrade().acceptTrade()) {
                                    Sleep.sleepUntil(() -> api.getTrade().isSecondInterfaceOpen(), 2000);
                                }
                            }*/
                        }
                        if (api.getTrade().isSecondInterfaceOpen()) {
                            if (api.getTrade().acceptTrade()) {
                                Sleep.sleepUntil(() -> api.getTrade().didOtherAcceptTrade(), 2000);
                            }
                        }
                    }
                }
            }
        }
        return 50;
    }


    private boolean isMuleClose() {
        for (Player p : api.getPlayers().getAll()) {
            String playerNameFixed = p.getName().replace("\u00A0", " ");
            for(String rsn: rsnList){
                if(playerNameFixed.equals(rsn)){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean ourOfferContainsItems() {
        if (api.getTrade().getOurOffers() != null) {
            /*
            if (api.getTrade().getOurOffers().contains("Cosmic rune") || api.getTrade().getOurOffers().contains("Coins") || api.getTrade().getOurOffers().contains("Law rune") ||
            api.getTrade().getOurOffers().contains("Nature rune")) {
                return true;
            }*/

            for(String itemName: itemList){
                if(api.getTrade().getOurOffers().contains(itemName)){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean ourInventoryContainsItems(){
        for(String itemName: itemList){
            if(api.getInventory().contains(itemName)){
                return true;
            }
        }
        return false;
    }
}
