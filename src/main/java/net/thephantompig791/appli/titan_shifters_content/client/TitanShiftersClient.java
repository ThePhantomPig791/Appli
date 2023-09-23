package net.thephantompig791.appli.titan_shifters_content.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.util.Identifier;
import net.thephantompig791.appli.Appli;
import net.thephantompig791.appli.titan_shifters_content.TitanShifters;
import net.thephantompig791.appli.titan_shifters_content.client.render.TitanSkinBoltEntityRenderer;
import net.thephantompig791.appli.titan_shifters_content.item.TitanShiftersItems;
import net.thephantompig791.appli.titan_shifters_content.item.TitanSkinBowItem;

public class TitanShiftersClient {
    @Environment(EnvType.CLIENT)
    public static void init() {
        EntityRendererRegistry.register(TitanShifters.TITAN_SKIN_BOLT_ENTITY, (TitanSkinBoltEntityRenderer::new));

        ModelPredicateProviderRegistry.register(TitanShiftersItems.TITAN_SKIN_CROSSBOW,
                new Identifier("loaded"),
                (itemStack, clientWorld, livingEntity, x) ->
                        itemStack.getOrCreateNbt().getBoolean("loaded") ? 1 : 0
        );

        // am I allowed to just take this from {@code net.minecraft.client.item.ModelPredicateProviderRegistry}
        ModelPredicateProviderRegistry.register(TitanShiftersItems.TITAN_SKIN_BOW, new Identifier("pull"), (stack, world, entity, seed) -> {
            if (entity == null) {
                return 0.0f;
            }
            if (entity.getActiveItem() != stack) {
                return 0.0f;
            }
            return (float)(stack.getMaxUseTime() - entity.getItemUseTimeLeft()) / 20.0f;
        });
        ModelPredicateProviderRegistry.register(TitanShiftersItems.TITAN_SKIN_BOW, new Identifier("pulling"), (stack, world, entity, seed) ->
                entity != null &&
                entity.isUsingItem() &&
                entity.getActiveItem() == stack &&
                (entity.getOffHandStack().isOf(TitanShiftersItems.TITAN_SKIN_BOLT) || (entity instanceof PlayerEntity p && p.isCreative()))
                        ? 1.0f : 0.0f
        );

        ModelPredicateProviderRegistry.register(TitanShiftersItems.TITAN_SKIN_BOLT, new Identifier("mainhand_pull"), (stack, world, entity, seed) ->
                entity != null &&
                entity.getMainHandStack().isOf(TitanShiftersItems.TITAN_SKIN_BOW) &&
                entity.getOffHandStack().equals(stack) &&
                entity.getItemUseTimeLeft() != 0
                    ? 1.0f : 0
        );
    }
}
