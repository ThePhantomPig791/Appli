package net.thephantompig791.appli.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.thephantompig791.appli.data.AppliDataTypes;
import net.thephantompig791.appli.packet.AppliNetworkingConstants;
import net.thephantompig791.appli.util.RadialMenu;
import net.thephantompig791.appli.util.RadialMenuEntry;

import java.util.List;

@Environment(EnvType.CLIENT)
public class RadialMenuClient {
    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(AppliNetworkingConstants.RADIAL_MENU_SERVER_TO_CLIENT, (client, handler, buf, responseSender) -> {
            List<RadialMenuEntry> list = AppliDataTypes.RADIAL_MENU_ENTRIES.receive(buf);
            client.execute(() -> {
                RadialMenuScreen radialMenuScreen = new RadialMenuScreen(new RadialMenu(list));
                client.setScreen(radialMenuScreen);
            });
        });
    }
}
