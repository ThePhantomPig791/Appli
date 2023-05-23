package net.thephantompig791.appli.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Power;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.thephantompig791.appli.Appli;
import net.thephantompig791.appli.power.DisableKeysPower;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(Keyboard.class)
public abstract class DisableKeysPowerMixin {
    @Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
    public void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo callbackInfo) {
        if (MinecraftClient.getInstance().player != null && !MinecraftClient.getInstance().player.isSpectator()) {
            List<DisableKeysPower> powerList = PowerHolderComponent.getPowers(MinecraftClient.getInstance().player, DisableKeysPower.class);
            List<Integer> keys = new ArrayList<>();
            powerList.stream().filter(Power::isActive).forEach(power -> keys.addAll(power.getKeys()));
            if (keys.toString().equals("[]") && powerList.stream().anyMatch(Power::isActive)) {
                KeyBinding.unpressAll();
                callbackInfo.cancel();
            } else {
                for (int keyToDisable : keys) {
                    if (key == keyToDisable) {
                        KeyBinding.setKeyPressed(InputUtil.fromKeyCode(key, scancode), false);
                        callbackInfo.cancel();
                    }
                }
            }
        }
    }
}