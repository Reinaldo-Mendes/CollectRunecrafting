package nodes;

import Framework.Node;
import Framework.PrioritisedNode;
import Framework.Priority;
import Framework.Sleep;
import org.osbot.rs07.api.Bank;
import org.osbot.rs07.script.MethodProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@PrioritisedNode(priority = Priority.FOUR)
public class WithdrawFromBank extends Node {

    private List<String> itemList = new ArrayList<>(Arrays.asList("Cosmic rune", "Law rune", "Nature rune", "Chaos rune","Death rune", "Coins", "Uncut diamond", "Diamond",
            "Air rune", "Earth rune", "Water rune"));

    public WithdrawFromBank(MethodProvider api) {
        super(api);
    }

    @Override
    public boolean canExecute() throws InterruptedException {
        return AreasAndPositions.duelArenaArea.contains(api.myPosition()) && !inventoryContainsItems() && bankContainsItemsToWithdraw()
                && !tradeContainsItems();
    }

    @Override
    public int execute() throws InterruptedException {
        api.log("Node: WithdrawFromBank ");
        if (!api.getBank().isOpen()) {
            if (api.getBank().open()) {
                Sleep.sleepUntil(() -> api.getBank().isOpen(), 3000);
                return 50;
            }
        } else {
            if (api.getBank().getWithdrawMode().equals(Bank.BankMode.WITHDRAW_NOTE)) {
                for(String itemName: itemList){
                    withdrawFromBank(itemName);
                }
            } else{
                if(api.getBank().enableMode(Bank.BankMode.WITHDRAW_NOTE)){
                    api.log("Enabled withdraw as note");
                    Sleep.sleepUntil(() -> api.getBank().getWithdrawMode().equals(Bank.BankMode.WITHDRAW_NOTE), 3000);
                }
            }

        }
        return 500;
    }

    private boolean bankContainsItemsToWithdraw() {
       for(String itemName: itemList){
           if(api.getBank().contains(itemName)){
               return true;
           }
       }
       return false;
    }

    private boolean inventoryContainsItems(){
        for(String itemName: itemList){
            if(api.getInventory().contains(itemName)){
                return true;
            }
        }
        return false;
    }

    private boolean tradeContainsItems(){
        for(String itemName: itemList){
            if(api.getTrade().getOurOffers().contains(itemName)){
                return true;
            }
        }
        return false;
    }

    private boolean withdrawFromBank(String itemName) {
        if (api.getBank().contains(itemName)) {
            if (api.getBank().withdrawAll(itemName)) {
                return Sleep.sleepUntil(() -> api.getInventory().contains(itemName), 3000);
            }
        }
        return false;
    }

}
