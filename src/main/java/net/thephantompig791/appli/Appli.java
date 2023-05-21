package net.thephantompig791.appli;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.thephantompig791.appli.power.factory.action.AppliBiEntityActions;
import net.thephantompig791.appli.power.factory.condition.AppliBiEntityConditions;
import net.thephantompig791.appli.power.factory.condition.AppliBlockConditions;
import net.thephantompig791.appli.power.factory.condition.AppliEntityConditions;
import net.thephantompig791.appli.titan_shifters_content.item.TitanShiftersItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Appli implements ModInitializer{
    public static final String MOD_ID = "appli";
    public static final String TS_MOD_ID = "titan_shifter_megapack";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static String VERSION = "";

    @Override
    public void onInitialize() {

        AppliEntityConditions.register();
        AppliBlockConditions.register();
        AppliBiEntityConditions.register();

        AppliBiEntityActions.register();

        //AppliPowerFactories.register();

        TitanShiftersItems.register();

        //yeah, i took this from apoli.java, sorry about that, but like everyone else did, so...
        FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(modContainer -> {
            VERSION = modContainer.getMetadata().getVersion().getFriendlyString();
            if(VERSION.contains("+")) {
                VERSION = VERSION.split("\\+")[0];
            }
            if(VERSION.contains("-")) {
                VERSION = VERSION.split("-")[0];
            }
        });
        LOGGER.info("Appli " + VERSION + " has initialized. Ready to power up your game that's already been powered up by many other (better) power libraries.");
    }

    public static Identifier identifier(String path) {
        return new Identifier(MOD_ID, path);
    }
}