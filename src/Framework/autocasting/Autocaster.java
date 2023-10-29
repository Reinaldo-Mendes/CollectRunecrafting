package Framework.autocasting;

import Framework.Sleep;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.MethodProvider;

public class Autocaster {
    private static final int AUTOCAST_CONFIG = 108;
    private CachedWidget openPanel = new CachedWidget(593, 25);
    private CachedWidget panel = new CachedWidget(548, 61);
    //private CachedWidget cancel = new CachedWidget(201, 0, 0);
    private CachedWidget spellWidget;
    private MethodProvider api;

    public Autocaster(MethodProvider api) {
        this.api = api;
    }

    public boolean isAutocasting() {
        return api.getConfigs().get(AUTOCAST_CONFIG) != 0;
    }

    public boolean isAutocasting(Items spell) {
        return api.getConfigs().get(AUTOCAST_CONFIG) == spell.getConfigValue();
    }

    public void openPanel(Items spell) throws InterruptedException {
        if (api.getTabs().getOpen() != Tab.ATTACK) {
            TabHotkey.COMBAT.openTab(api);
        } else {
            if (openPanel.getWidget(api.getWidgets()) != null) {
                if (openPanel.getWidget(api.getWidgets()).interact()) {
                    //new CSleep(() -> isSpellVisible(spell), 2_500).sleep();
                    Sleep.sleepUntil(() -> isSpellVisible(spell), 2500);
                }
            }
        }
    }

    public void selectSpell(Items spell) {
        if (isSpellVisible(spell)) {
            api.log("Spell is visible");
            if (spellWidget.getWidget(api.getWidgets()).interact()) {
                api.log("Interacted with the widget for spell");
                Sleep.sleepUntil(() -> isAutocasting(), 2500);
            } else{
                api.log("Failed to interact with spell widget");
            }
        }
    }

    public void autoCastSpell(Items spell) throws InterruptedException {
        if (!isSpellVisible(spell)){
            openPanel(spell);
        }

        else {
            selectSpell(spell);
        }
    }

    public boolean isPanelOpen() {
        return panel.getWidget(api.getWidgets()) != null;
    }

    public boolean isSpellVisible(Items spell) {
        spellWidget = new CachedWidget(spell.getRoot(), spell.getChild(), spell.getChild2());
        return spellWidget.getWidget(api.getWidgets()) != null;
    }
}