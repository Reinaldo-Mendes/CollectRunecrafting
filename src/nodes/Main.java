package nodes;

import Framework.ClanChatter;
import Framework.Node;
import Framework.NodeManager;
import Framework.PaintAPI;
import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.input.keyboard.BotKeyListener;
import org.osbot.rs07.listener.MessageListener;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ScriptManifest(author = "Naaldo", info = "Test", logo="",name = "CollectRunecraft", version = 1.1)
public class Main extends Script implements MessageListener {

    private NodeManager manager;
    public static boolean hasTraded = false;
    public static boolean hasCollected = false;
    public static boolean hasJoinedCc = false;
    @Override
    public void onStart(){
        manager = new NodeManager();
        manager.addNode(new CollectFromGeNode(this));
        manager.addNode(new startNode(this));
        manager.addNode(new WithdrawFromBank(this));
        manager.addNode(new TradeGpOver(this));
        manager.addNode(new JoinCc(this));
        manager.addNode(new HopWorld(this));
        manager.addNode(new WalkToDuelBank(this));
    }


    @Override
    public int onLoop() throws InterruptedException {
       manager.getNextNode().execute();
        return 10;
    }


    public void onMessage(Message msg) throws InterruptedException {
        if (msg.getMessage().contains("Accepted trade")) {
            hasTraded = true;
            ClanChatter cc = new ClanChatter(this);
            cc.leaveClanChat();
            stop();
        }
        if(msg.getMessage().contains("You have nothing to collect.")){
            hasCollected = true;
        }

        if(msg.getMessage().contains("Tribe")){
           log("We are in terror tribe cc!");
            hasJoinedCc = true;
        }

    }

}