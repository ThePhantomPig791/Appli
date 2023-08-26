package net.thephantompig791.appli.titan_shifters_content.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.Identifier;
import net.thephantompig791.appli.Appli;

public class TitanShiftersItems {

    public static final Item TITAN_SKIN_WARHAMMER = register("titan_skin_warhammer",
            new TitanSkinWarhammerItem(new FabricItemSettings()));

    public static final Item TITAN_SKIN_CROSSBOW = register("titan_skin_crossbow",
            new TitanSkinCrossbowItem(new FabricItemSettings()));

    public static final Item TITAN_INJECTION = register("titan_injection",
            new Item(new FabricItemSettings().maxCount(1)));

    public static final Item TITAN_SADDLE_MOUNT = register("titan_saddle_mount",
            new Item(new FabricItemSettings().maxCount(1)));

    public static final Item TITAN_BARREL_MOUNT = register("titan_barrel_mount",
            new Item(new FabricItemSettings().maxCount(1)));

    public static final Item TITAN_RIFLE_MOUNT = register("titan_rifle_mount",
            new Item(new FabricItemSettings().maxCount(1)));


    private static Item register(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Appli.TS_MOD_ID, name), item);
    }

    public static void register() {}
}
