package net.thephantompig791.appli.power.factory.action;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.thephantompig791.appli.Appli;
import net.thephantompig791.appli.data.AppliDataTypes;
import net.thephantompig791.appli.packet.AppliNetworkingConstants;

public class AppliEntityActions {
    public static void register() {
        register(new ActionFactory<>(Appli.identifier("radial_menu"), new SerializableData()
                .add("entries", AppliDataTypes.RADIAL_MENU_ENTRIES),
                (data, entity) -> {
                    if (!entity.isPlayer()) return;
                    MinecraftClient.getInstance().mouse.unlockCursor();
                    if (entity.world.isClient) return;
                    PacketByteBuf buf = PacketByteBufs.create();
                    AppliDataTypes.RADIAL_MENU_ENTRIES.send(buf, data.get("entries"));
                    ServerPlayNetworking.send((ServerPlayerEntity) entity, AppliNetworkingConstants.RADIAL_MENU_SERVER_TO_CLIENT, buf);
                }));
    }

    private static void register(ActionFactory<Entity> actionFactory) {
        Registry.register(ApoliRegistries.ENTITY_ACTION, actionFactory.getSerializerId(), actionFactory);
    }
}