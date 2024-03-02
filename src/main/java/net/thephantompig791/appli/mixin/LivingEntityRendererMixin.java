package net.thephantompig791.appli.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.thephantompig791.appli.Appli;
import net.thephantompig791.appli.power.FadingModelColorPower;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin extends EntityRenderer<LivingEntity> {
    private LivingEntityRendererMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    // basically https://github.com/apace100/apoli/blob/1.20/src/main/java/io/github/apace100/apoli/mixin/LivingEntityRendererMixin.java
    // 99% a copy and paste
    @ModifyExpressionValue(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;getRenderLayer(Lnet/minecraft/entity/LivingEntity;ZZZ)Lnet/minecraft/client/render/RenderLayer;"))
    private RenderLayer appli$changeRenderLayerWhenTranslucent(@Nullable RenderLayer original, LivingEntity entity) {
        return PowerHolderComponent.hasPower(entity, FadingModelColorPower.class, FadingModelColorPower::isTranslucent)
                ? RenderLayer.getItemEntityTranslucentCull(this.getTexture(entity))
                : original;
    }

    @WrapOperation(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/EntityModel;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;IIFFFF)V"))
    private void appli$renderColorChangedModel(EntityModel<?> instance, MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha, Operation<Void> original, LivingEntity entity) {
        List<FadingModelColorPower> fadingModelColorPowers = PowerHolderComponent.getPowers(entity, FadingModelColorPower.class);
        if (fadingModelColorPowers.isEmpty()) {
            original.call(instance, matrices, vertices, light, overlay, red, green, blue, alpha);
            return;
        } else {
            if (entity instanceof PlayerEntity) {
                Appli.LOGGER.info("ticks: " + fadingModelColorPowers.get(0).getTicks());
                Appli.LOGGER.info("red: " + fadingModelColorPowers.get(0).getAlpha());
                Appli.LOGGER.info("green: " + fadingModelColorPowers.get(0).getRed());
                Appli.LOGGER.info("blue: " + fadingModelColorPowers.get(0).getGreen());
                Appli.LOGGER.info("alpha: " + fadingModelColorPowers.get(0).getBlue());
            }
        }

        float newRed = fadingModelColorPowers
                .stream()
                .map(FadingModelColorPower::getRed)
                .reduce(red, (a, b) -> a * b);
        float newGreen = fadingModelColorPowers
                .stream()
                .map(FadingModelColorPower::getGreen)
                .reduce(green, (a, b) -> a * b);
        float newBlue = fadingModelColorPowers
                .stream()
                .map(FadingModelColorPower::getBlue)
                .reduce(blue, (a, b) -> a * b);
        float newAlpha = fadingModelColorPowers
                .stream()
                .map(FadingModelColorPower::getAlpha)
                .min(Float::compareTo)
                .map(alphaFactor -> alpha * alphaFactor)
                .orElse(alpha);

        instance.render(matrices, vertices, light, overlay, newRed, newGreen, newBlue, newAlpha);
    }
}
