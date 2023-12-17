package net.thephantompig791.appli.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.Ownable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import net.minecraft.world.World;
import net.thephantompig791.appli.power.PreventItemPickupPower;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity implements Ownable {
    private ItemEntityMixin(EntityType<?> type, World world) { super(type, world); }

    @Shadow
    protected abstract ItemStack getStack();

    @Inject(method = "onPlayerCollision", at = @At("HEAD"), cancellable = true)
    public void onPlayerCollision(PlayerEntity player, CallbackInfo ci) {
        List<PreventItemPickupPower> powers = PowerHolderComponent.getPowers(player, PreventItemPickupPower.class);
        powers.forEach(power -> {
            if (power.isActive()
                    && (power.bientityCondition == null || power.bientityCondition.test(new Pair<>(player, this)))
                    && (power.itemCondition == null || power.itemCondition.test(new Pair<>(this.getWorld(), this.getStack())))
            ) {
                ci.cancel();
            }
        });
    }
}
