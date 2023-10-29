package Framework;

import org.osbot.rs07.api.Tabs;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;

import java.util.Random;

public class ClanChatter{

    private MethodProvider api;

    public ClanChatter(MethodProvider api){
        this.api = api;
    }

    public boolean notInClanChat(){
        RS2Widget notInChat = api.getWidgets().getWidgetContainingText("Not in chat");
        return (notInChat != null && notInChat.isVisible());
    }

    public void openClanChatTab(){
        api.getTabs().open(Tab.CLANCHAT);
    }

    public boolean isClanChatTabOpen(){
        return Tab.CLANCHAT.isOpen(api.getBot());
    }

    public boolean isInAnyClanChat(){
        if(api.getTabs().isOpen(Tab.CLANCHAT)) {
            RS2Widget notInChannel = api.getWidgets().getWidgetContainingText("Not in channel");
            if (notInChannel != null) {
                return false;
            }
        } else{
            openClanChatTab();
        }
            return true;

    }

    public void joinClanChat(String name){

        if(api.getTabs().isOpen(Tab.CLANCHAT)){
            //RS2Widget enterName = getWidgets().getWidgetContainingText("Enter the player name whose channel you wish to join:");
            RS2Widget chat_channel = api.getWidgets().get(707, 3);
            RS2Widget enterName = api.getWidgets().get(162, 41);
            RS2Widget joinChat = api.getWidgets().get(7,18);

            if (enterName != null && enterName.isVisible()){
                api.getKeyboard().typeString(name);
                sleep(4000, 5000);
            }
            else {
                if (joinChat != null && joinChat.isVisible()){
                    clickOnWidget(joinChat);
                } else{
                    if(chat_channel != null && chat_channel.isVisible()){
                        clickOnWidget(chat_channel);
                    }
                }
            }
        } else {
            api.log("Clan chat tab is not open");
            if(api.getTabs().open(Tab.CLANCHAT)){
                api.log("Opened clan chat tab");
            }else{
                api.log("Failed to open clan chat tab");
            }
        }




    }

    public void leaveClanChat(){
        if(api.getTabs().isOpen(Tab.CLANCHAT)) {
            RS2Widget leave = api.getWidgets().get(7,18);
            if(leave != null){
                if(leave.getMessage().contains("Leave")){
                    clickOnWidget(leave);
                }
            }
        }else {
            api.log("Clan chat tab is not open");
            if(api.getTabs().open(Tab.CLANCHAT)){
                api.log("Opened clan chat tab");
            }else{
                api.log("Failed to open clan chat tab");
            }
        }

        RS2Widget leaveWidget = api.getWidgets().getWidgetContainingText("Leave Chat");
        if (leaveWidget != null && leaveWidget.isVisible()) clickOnWidget(leaveWidget);
    }

    public boolean isInClan(String name){
        if(isInAnyClanChat()){
            RS2Widget widgetClanName = api.getWidgets().get(7, 2);
            if(widgetClanName != null){
                if(widgetClanName.getMessage().contains(name)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isOwner(String name){
        RS2Widget owner = api.getWidgets().getWidgetContainingText("Owner:");
        return owner.getMessage().contains(name);
    }

    public void talkInClanChat(String output){
        api.getKeyboard().typeString(String.format("/%s", output));
        sleep(1500, 2000);
    }

    private void clickOnWidget(RS2Widget widget){
        widget.hover();
        api.getMouse().click(false);
        sleep(1500, 2000);
    }

    private void sleep(int min, int max){

        try{
            api.sleep(new Random().nextInt(max-min) + min);
        } catch(Exception e){
            api.log(e);
        }
    }
}