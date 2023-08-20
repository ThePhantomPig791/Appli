package net.thephantompig791.appli.server;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.thephantompig791.appli.packet.AppliNetworkingConstants;

public class RadialMenuServer {
    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(AppliNetworkingConstants.RADIAL_MENU_CLIENT_TO_SERVER, (server, player, handler, buf, responseSender) -> {
            ActionFactory<Entity>.Instance action = ApoliDataTypes.ENTITY_ACTION.receive(buf);
            server.execute(() -> {
                player.closeHandledScreen();
                action.accept(player);
            });
        });
    }
}
