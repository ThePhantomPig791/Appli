package net.thephantompig791.appli.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.thephantompig791.appli.Appli;
import net.thephantompig791.appli.data.AppliDataTypes;
import net.thephantompig791.appli.packet.AppliNetworkingConstants;
import net.thephantompig791.appli.util.RadialMenu;
import net.thephantompig791.appli.util.RadialMenuEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class RadialMenuEvents {
    private static Map<MinecraftClient, List<RadialMenuEntry>> clientEntries = new HashMap<>();
    public static void init() {

        ClientTickEvents.END_CLIENT_TICK.register(new ClientTickEvents.EndTick() {
            @Override
            public void onEndTick(MinecraftClient client) {
                if (client.currentScreen != null) return;
                RadialMenu radialMenu = new RadialMenu(clientEntries.get(client));
                if (clientEntries.containsKey(client)) {
                    if (client.mouse.wasLeftButtonClicked()) {
                        client.mouse.lockCursor();
                        clientEntries.remove(client);
                        radialMenu.getRadialMenuEntry(client).getEntityAction().accept(client.player);
                        Appli.LOGGER.info("clicked out of radial menu");
                    } else {
                        client.mouse.unlockCursor();
                    }
                }
            }
        });
        /*
        HudRenderCallback.EVENT.register(new HudRenderCallback() {
            @Override
            public void onHudRender(MatrixStack matrixStack, float tickDelta) {
                MinecraftClient client = MinecraftClient.getInstance();
                if (!clientsToShowMenu.contains(client)) return;

                RenderSystem.setShaderTexture(0, new Identifier("minecraft", "textures/block/white_wool.png"));
                DrawableHelper.drawTexture(matrixStack, (client.getWindow().getScaledWidth() / 2) - 64, (client.getWindow().getScaledHeight() / 2) - 64, 0, 0, 128, 128, 320, 256);

            }
        });*/

        ClientPlayNetworking.registerGlobalReceiver(AppliNetworkingConstants.RADIAL_MENU_ACTION_TO_CLIENT, (client, handler, buf, responseSender) -> {
            List<RadialMenuEntry> list = AppliDataTypes.RADIAL_MENU_ENTRIES.receive(buf);
            client.execute(() -> {
                clientEntries.put(client, list);
            });
        });
    }
}
