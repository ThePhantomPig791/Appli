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
