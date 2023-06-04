package net.thephantompig791.appli;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.thephantompig791.appli.server.RadialMenuServer;

public class AppliServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        RadialMenuServer.init();
    }
}
