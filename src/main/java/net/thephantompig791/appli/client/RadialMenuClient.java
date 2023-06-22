package net.thephantompig791.appli.client;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.apace100.apoli.data.ApoliDataTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.thephantompig791.appli.Appli;
import net.thephantompig791.appli.data.AppliDataTypes;
import net.thephantompig791.appli.packet.AppliNetworkingConstants;
import net.thephantompig791.appli.util.RadialMenu;
import net.thephantompig791.appli.util.RadialMenuEntry;

import java.util.*;

@Environment(EnvType.CLIENT)
public class RadialMenuClient {
    private static Map<MinecraftClient, List<RadialMenuEntry>> clientEntries = new HashMap<>();
    public static void init() {

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.currentScreen != null) return;
            if (clientEntries.containsKey(client)) {
                RadialMenu radialMenu = new RadialMenu(clientEntries.get(client));
                if (client.mouse.wasLeftButtonClicked()) {
                    Appli.LOGGER.info("mouse pos: (" + client.mouse.getX() + ", " + client.mouse.getY() + ")");

                    if (radialMenu.click(client) == null) return;
                    radialMenu.click(client).getEntityAction().accept(client.player);

                    PacketByteBuf buf = PacketByteBufs.create();
                    radialMenu.click(client).getEntityAction().write(buf);
                    ClientPlayNetworking.send(AppliNetworkingConstants.RADIAL_MENU_CLIENT_TO_SERVER, buf);

                    client.mouse.lockCursor();
                    clientEntries.remove(client);

                    Appli.LOGGER.info("clicked out of radial menu, action: " + radialMenu.click(client).getEntityAction());
                } else {
                    client.mouse.unlockCursor();
                }
            }
        });
        HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (clientEntries.containsKey(client)) {
                RadialMenu radialMenu = new RadialMenu(clientEntries.get(client));
                radialMenu.draw(client);
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(AppliNetworkingConstants.RADIAL_MENU_SERVER_TO_CLIENT, (client, handler, buf, responseSender) -> {
            List<RadialMenuEntry> list = AppliDataTypes.RADIAL_MENU_ENTRIES.receive(buf);
            client.execute(() -> clientEntries.put(client, list));
        });
    }
}
