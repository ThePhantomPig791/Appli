package net.thephantompig791.appli.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Power;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.thephantompig791.appli.power.PreventMousePower;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin (Mouse.class )
public abstract class DisableMousePowerMixin {
    @Inject(method = "onMouseButton", at = @At("HEAD"), cancellable = true)
    private void appli$onMouseButton(long window, int button, int action, int mods, CallbackInfo callbackInfo) {
        if (MinecraftClient.getInstance().player != null && !MinecraftClient.getInstance().player.isSpectator()) {
            if (PowerHolderComponent.getPowers(MinecraftClient.getInstance().player, PreventMousePower.class).stream().anyMatch(Power::isActive)) {
                callbackInfo.cancel();
            }
        }
    }

    @Inject(method = "onMouseScroll", at = @At("HEAD"), cancellable = true)
    private void appli$onMouseScroll(long window, double horizontal, double vertical, CallbackInfo callbackInfo) {
        if (MinecraftClient.getInstance().player != null && !MinecraftClient.getInstance().player.isSpectator()) {
            if (PowerHolderComponent.getPowers(MinecraftClient.getInstance().player, PreventMousePower.class).stream().anyMatch(Power::isActive)) {
                callbackInfo.cancel();
            }
        }
    }

    @Inject(method = "updateMouse", at = @At("HEAD"), cancellable = true)
    private void appli$updateMouse(CallbackInfo callbackInfo) {
        if (MinecraftClient.getInstance().player != null && !MinecraftClient.getInstance().player.isSpectator()) {
            if (PowerHolderComponent.getPowers(MinecraftClient.getInstance().player, PreventMousePower.class).stream().anyMatch(Power::isActive)) {
                callbackInfo.cancel();
            }
        }
    }
}
