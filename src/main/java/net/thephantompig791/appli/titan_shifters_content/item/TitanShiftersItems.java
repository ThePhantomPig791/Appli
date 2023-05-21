package net.thephantompig791.appli.titan_shifters_content.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.thephantompig791.appli.Appli;

public class TitanShiftersItems {

    public static final Item TITAN_SKIN_WARHAMMER = register("titan_skin_warhammer",
            new TitanSkinWarhammerItem(new FabricItemSettings()));

    public static final Item TITAN_SKIN_CROSSBOW = register("titan_skin_crossbow",
            new TitanSkinCrossbowItem(new FabricItemSettings()));

    //I decided against using titan injections
    /*
    public static final Item TITAN_INJECTION = register("titan_injection",
            new TitanSkinCrossbowItem(new FabricItemSettings()));

    public static final Item EMPTY_INJECTION = register("empty_injection",
            new TitanSkinCrossbowItem(new FabricItemSettings()));
     */



    private static Item register(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Appli.TS_MOD_ID, name), item);
    }

    public static void register() {}
}
