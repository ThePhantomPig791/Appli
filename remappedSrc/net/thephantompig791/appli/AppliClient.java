package net.thephantompig791.appli;

import net.fabricmc.api.ClientModInitializer;
import net.thephantompig791.appli.client.RadialMenuClient;

public class AppliClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        RadialMenuClient.init();

        Appli.LOGGER.info("Appli client initialized.");
    }
}
