package nodes;

import Framework.Node;
import Framework.PrioritisedNode;
import Framework.Priority;
import Framework.Sleep;
import org.osbot.rs07.api.Bank;
import org.osbot.rs07.script.MethodProvider;

@PrioritisedNode(priority = Priority.FOUR)
public class WithdrawFromBank extends Node {


    public WithdrawFromBank(MethodProvider api) {
        super(api);
    }

    @Override
    public boolean canExecute() throws InterruptedException {
        return AreasAndPositions.feroxArea.contains(api.myPosition()) && bankContainsItemsToWithdraw();
    }

    @Override
    public int execute() throws InterruptedException {
        api.log("Node: WithdrawFromBank ");
        if (!api.getBank().isOpen()) {
            if (api.getBank().open()) {
                Sleep.sleepUntil(() -> api.getBank().isOpen(), 3000);
            }
        } else {
            withdrawFromBank("Cosmic rune");
            withdrawFromBank("Law rune");
            withdrawFromBank("Nature rune");
            withdrawFromBank("Coins");
            if (api.getBank().contains("Uncut diamond", "Diamond")) {
                if (api.getBank().enableMode(Bank.BankMode.WITHDRAW_NOTE)) {
                    withdrawFromBank("Uncut diamond");
                    withdrawFromBank("Diamond");
                }
            }
        }
        return 500;
    }

    private boolean bankContainsItemsToWithdraw() {
        //api.log("Bank contains Cosmic rune: "+api.getBank().contains("Cosmic rune") + api.getBank().contains("Law rune") + api.getBank().contains("Nature rune") + api.getBank().contains("Coins"));
        return api.getBank().contains("Cosmic rune") || api.getBank().contains("Law rune") || api.getBank().contains("Nature rune") || api.getBank().contains("Coins");
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
