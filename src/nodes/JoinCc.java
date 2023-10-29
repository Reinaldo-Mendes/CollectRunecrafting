package nodes;

import Framework.ClanChatter;
import Framework.Node;
import Framework.PrioritisedNode;
import Framework.Priority;
import org.osbot.rs07.script.MethodProvider;

@PrioritisedNode(priority =  Priority.TWO)
public class JoinCc extends Node {

    ClanChatter cc;

    public JoinCc(MethodProvider api){
        super(api);
       cc = new ClanChatter(api);
    }

    @Override
    public boolean canExecute() throws InterruptedException {
        return !Main.hasJoinedCc;

    }

    @Override
    public int execute() throws InterruptedException {
        api.log("Node: JoinCC ");
        if(api.getBank().isOpen()){
            api.getBank().close();
        }
        cc.joinClanChat("Elite Br Pk");
        return 1000;
    }
}
