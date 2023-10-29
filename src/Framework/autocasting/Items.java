package Framework.autocasting;

import org.osbot.rs07.api.ui.MagicSpell;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Spells.NormalSpells;
import org.osbot.rs07.script.MethodProvider;

public enum Items {
    AIR_STRIKE("Wind strike", NormalSpells.WIND_STRIKE, 201, 1, 1, 3),
    WATER_STRIKE("Water strike", NormalSpells.WATER_STRIKE, 201, 1, 2, 5),
    EARTH_STRIKE("Earth strike", NormalSpells.EARTH_STRIKE, 201, 1, 3, 7),
    FIRE_STRIKE("Fire strike", NormalSpells.FIRE_STRIKE, 201, 1, 4, 9),
    AIR_BOLT("Wind bolt", NormalSpells.WIND_BOLT, 201, 1, 5, 11),
    WATER_BOLT("Water bolt", NormalSpells.WATER_BOLT, 201, 1, 6, 13),
    EARTH_BOLT("Earth bolt", NormalSpells.EARTH_BOLT, 201, 1, 7, 15),
    FIRE_BOLT("Fire bolt", NormalSpells.FIRE_BOLT, 201, 1, 8, 17),
    AIR_BLAST("Wind blast", NormalSpells.WIND_BLAST, 201, 1, 9, 19),
    WATER_BLAST("Water blast", NormalSpells.WATER_BLAST, 201, 1, 10, 21),
    EARTH_BLAST("Earth blast", NormalSpells.EARTH_BLAST, 201, 1, 11, 23),
    FIRE_BLAST("Fire blast", NormalSpells.FIRE_BLAST, 201, 1, 12, 25),
    WIND_WAVE("Wind wave", NormalSpells.WIND_WAVE, 201, 1, 13, 27),
    WATER_WAVE("Water wave", NormalSpells.WATER_WAVE, 201, 1, 14, 29),
    EARTH_WAVE("Earth wave", NormalSpells.EARTH_WAVE, 201, 1, 15, 31),
    FIRE_WAVE("Fire wave", NormalSpells.FIRE_WAVE, 201, 1, 16, 33),
    IBAN_BLAST("Iban blast", NormalSpells.IBAN_BLAST),
    CRUMBLE_UNDEAD("Crumble undead", NormalSpells.CRUMBLE_UNDEAD, 201, 1, 0, 35),
    MAGIC_DART("Magic dart", NormalSpells.MAGIC_DART, 201, 1, 0, 37),
    CLAWS_OF_GUTHIX("Claws of Guthix", NormalSpells.CLAWS_OF_GUTHIX),
    SARADOMIN_STRIKE("Saradomin strike", NormalSpells.SARADOMIN_STRIKE),
    FLAMESS_OF_ZAMORAK("Flames of Zamorak", NormalSpells.FLAMES_OF_ZAMORAK),
    CONFUSE("Confuse", NormalSpells.CONFUSE),
    WEAKEN("Weaken", NormalSpells.WEAKEN),
    CURSE("Curse", NormalSpells.CURSE),
    STUN("Stun", NormalSpells.STUN);

    Items(String name, MagicSpell spell, int root, int child, int child2, int configValue) {
        this.name = name;
        this.magicSpell = spell;
        this.root = root;
        this.child = child;
        this.child2 = child2;
        this.configValue = configValue;
    }

    Items(String name, MagicSpell spell) {
        this.name = name;
        this.magicSpell = spell;
    }

    private MagicSpell magicSpell;
    private String name;
    private int root, child, child2, configValue;

    @Override
    public String toString() {
        return name;
    }

    public MagicSpell getMagicSpell() {
        return magicSpell;
    }

    public int getRoot() {
        return root;
    }

    public int getChild() {
        return child;
    }

    public int getChild2() {
        return child2;
    }

    public int getConfigValue() {
        return configValue;
    }

    public RS2Widget getWidget(MethodProvider api) {
        return api.getWidgets().get(getRoot(), getChild(), getChild2());
    }
}