package net.thephantompig791.appli;

import net.fabricmc.api.ClientModInitializer;

public class AppliClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Appli.LOGGER.info("Appli client initialized.");
    }
}
