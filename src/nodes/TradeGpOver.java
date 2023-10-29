package nodes;

import Framework.*;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.script.MethodProvider;

@PrioritisedNode(priority = Priority.ONE)
public class TradeGpOver extends Node {
    ClanChatter cc;
    public TradeGpOver(MethodProvider api) {
        super(api);
        cc = new ClanChatter(api);
    }

    @Override
    public boolean canExecute() throws InterruptedException {
        return AreasAndPositions.feroxArea.contains(api.myPosition()) && isMuleClose() && (api.getInventory().contains("Cosmic rune") || api.getInventory().contains("Law rune")
                || api.getInventory().contains("Nature rune") || api.getInventory().contains("Coins") || ourOfferContainsItems());
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
            String playerName2 = player.getName();
            if (playerName.equals("weak ass kid") || playerName.equals("return bor") || playerName.equals("Propadinga") ||
                    playerName2.equals("weak ass kid") || playerName2.equals("return bor") || playerName2.equals("Propadinga")) {
                if (!api.getTrade().isCurrentlyTrading()) {
                    if (player.interact("Trade with")) {
                        api.log("Interacted trade");
                        Sleep.sleepUntil(() -> api.getTrade().isCurrentlyTrading(), 7000);


                    } else {
                        api.log("Failed to interact");
                    }
                } else{
                    if (api.getTrade().isFirstInterfaceOpen()) {
                        if (api.getTrade().offerAll("Cosmic rune", "Law rune", "Nature rune", "Coins", "Uncut diamond", "Diamond")) {
                            Sleep.sleepUntil(() -> !api.getInventory().contains("Cosmic rune") && !api.getInventory().contains("Coins") && !api.getInventory().contains("Law rune") && !api.getInventory().contains("Nature rune") &&
                                    !api.getInventory().contains("Uncut diamond") && !api.getInventory().contains("Diamond"), 3000);
                            if (api.getTrade().acceptTrade()) {
                                Sleep.sleepUntil(() -> api.getTrade().isSecondInterfaceOpen(), 2000);
                            }
                        }
                    }
                    if (api.getTrade().isSecondInterfaceOpen()) {
                        if (api.getTrade().acceptTrade()) {
                            Sleep.sleepUntil(() -> api.getTrade().didOtherAcceptTrade(), 2000);
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
            if (playerNameFixed.equals("weak ass kid") || playerNameFixed.equals("return bor") || playerNameFixed.equals("Propadinga")) {
                //api.log("Mule is close");
                return true;
            } else {
                //api.log("Mule is not close");
            }
        }
        return false;
    }

    private boolean ourOfferContainsItems() {
        if (api.getTrade().getOurOffers() != null) {
            if (api.getTrade().getOurOffers().contains("Cosmic rune") || api.getTrade().getOurOffers().contains("Coins") || api.getTrade().getOurOffers().contains("Law rune") ||
            api.getTrade().getOurOffers().contains("Nature rune")) {
                return true;
            }
        }
        return false;
    }
}
