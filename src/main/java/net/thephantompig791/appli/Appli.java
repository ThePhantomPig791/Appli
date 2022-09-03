package net.thephantompig791.appli;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Appli implements ModInitializer {
	public static final String MOD_ID = "appli";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static String VERSION = "";

	@Override
	public void onInitialize() {
		FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(modContainer -> {
			VERSION = modContainer.getMetadata().getVersion().getFriendlyString();
			if(VERSION.contains("+")) {
				VERSION = VERSION.split("\\+")[0];
			}
			if(VERSION.contains("-")) {
				VERSION = VERSION.split("-")[0];
			}
		});
		LOGGER.info("Appli \" + VERSION + \" has initialized. Powering up your game that's already been powered up by many other (better) addons.");
	}
}