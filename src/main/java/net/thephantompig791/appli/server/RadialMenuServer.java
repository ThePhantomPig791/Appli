package net.thephantompig791.appli.server;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.thephantompig791.appli.Appli;
import net.thephantompig791.appli.packet.AppliNetworkingConstants;

@Environment(EnvType.SERVER)
public class RadialMenuServer {
    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(AppliNetworkingConstants.RADIAL_MENU_CLIENT_TO_SERVER, (server, player, handler, buf, responseSender) -> {
            ActionFactory<Entity>.Instance action = ApoliDataTypes.ENTITY_ACTION.receive(buf);
            Appli.LOGGER.info("(not in execute) received packet on server!" + action);
            server.execute(() -> {
                action.accept(player);
                player.closeHandledScreen();
                Appli.LOGGER.info("received packet on server!" + action);
            });
        });
    }
}
