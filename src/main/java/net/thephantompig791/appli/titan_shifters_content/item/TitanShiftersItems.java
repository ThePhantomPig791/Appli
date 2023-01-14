package net.thephantompig791.appli.titan_shifters_content.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.thephantompig791.appli.Appli;

public class TitanShiftersItems {

    public static final Item TITAN_SKIN_WARHAMMER = register("titan_skin_warhammer",
            new TitanSkinWarhammerItem(new FabricItemSettings()));

    public static final Item TITAN_SKIN_CROSSBOW = register("titan_skin_crossbow",
            new CrossbowItem(new FabricItemSettings()));



    private static Item register(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Appli.MOD_ID, name), item);
    }

    public static void register() {}
}
