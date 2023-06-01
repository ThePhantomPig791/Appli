package net.thephantompig791.appli;

import net.fabricmc.api.ClientModInitializer;
import net.thephantompig791.appli.client.RadialMenuEvents;

public class AppliClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        RadialMenuEvents.init();

        Appli.LOGGER.info("Appli client initialized.");
    }
}
