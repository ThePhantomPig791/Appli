package net.thephantompig791.appli.titan_shifters_content;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.thephantompig791.appli.Appli;
import net.thephantompig791.appli.titan_shifters_content.entity.TitanSkinBoltEntity;
import net.thephantompig791.appli.titan_shifters_content.item.TitanShiftersItems;

public class TitanShifters {
    public static final String MOD_ID = "titan_shifter_megapack";

    public static final EntityType<TitanSkinBoltEntity> TITAN_SKIN_BOLT_ENTITY = Registry.register(
            Registry.ENTITY_TYPE,
            identifier("titan_skin_bolt"),
            FabricEntityTypeBuilder.<TitanSkinBoltEntity>create(SpawnGroup.MISC, TitanSkinBoltEntity::new)
                    .dimensions(EntityDimensions.fixed(1F, 1F))
                    .trackRangeBlocks(24).trackedUpdateRate(60)
                    .build()
    );

    public static void register() {
        TitanShiftersItems.register();

        Appli.LOGGER.info("Titan Shifters content loaded! (Note: this will load even if you do not have the corresponding datapack installed.)");
    }

    public static Identifier identifier(String path) {
        return new Identifier(MOD_ID, path);
    }
}
