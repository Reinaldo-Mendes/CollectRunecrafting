package Framework.autocasting;

import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.MethodProvider;

import java.awt.event.KeyEvent;

public enum TabHotkey {

    COMBAT(Tab.ATTACK, 0, 1224),
    SKILLS(Tab.SKILLS, 1, 1224),
    QUEST(Tab.QUEST, 2, 1224),
    INVENTORY(Tab.INVENTORY, 3, 1224),
    EQUIPMENT(Tab.EQUIPMENT, 4, 1224),
    PRAYER(Tab.PRAYER, 5, 1224),
    MAGIC(Tab.MAGIC, 0, 1225),
    CLAN(Tab.CLANCHAT, 1, 1225),
    FRIENDS(Tab.FRIENDS, 2, 1225),
    SETTINGS(Tab.SETTINGS, 4, 1225),
    EMOTES(Tab.EMOTES, 5, 1225),
    LOGOUT(Tab.LOGOUT, 1, 1226);

    private final Tab tab;
    private final int index;
    private final int register;

    private final static int[] KEYCODES = {
            -1,
            KeyEvent.VK_F1,
            KeyEvent.VK_F2,
            KeyEvent.VK_F3,
            KeyEvent.VK_F4,
            KeyEvent.VK_F5,
            KeyEvent.VK_F6,
            KeyEvent.VK_F7,
            KeyEvent.VK_F8,
            KeyEvent.VK_F9,
            KeyEvent.VK_F10,
            KeyEvent.VK_F11,
            KeyEvent.VK_F12,
            KeyEvent.VK_ESCAPE
    };

    private TabHotkey(Tab tab, int index, int register) {
        this.tab = tab;
        this.index = index;
        this.register = register;
    }

    public Tab getTab() {
        return tab;
    }

    public int getHotkey(MethodProvider parent) {
        int config = parent.getConfigs().get(this.register);
        int kcIndex = (config >> (this.index * 5)) & 0b11111;
        return KEYCODES[kcIndex];
    }

    public boolean openTab(MethodProvider parent) throws InterruptedException {
        if(!isAssigned(parent)) return false;
        int hkey = getHotkey(parent);
        parent.getKeyboard().pressKey(hkey);
        try {
            MethodProvider.sleep(MethodProvider.random(20, 50));
        } finally {
            parent.getKeyboard().releaseKey(hkey);
        }
        return true;
    }

    public boolean isAssigned(MethodProvider parent) {
        return getHotkey(parent) != -1;
    }

    public static TabHotkey forTab(Tab tab) {
        for(TabHotkey thk : values()) {
            if(thk.getTab() == tab) {
                return thk;
            }
        }
        return null;
    }
}