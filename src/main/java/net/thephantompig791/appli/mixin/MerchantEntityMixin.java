package net.thephantompig791.appli.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Pair;
import net.minecraft.village.TradeOffer;
import net.minecraft.world.World;
import net.thephantompig791.appli.Appli;
import net.thephantompig791.appli.power.ActionOnTradePower;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(MerchantEntity.class)
public abstract class MerchantEntityMixin extends Entity {
    @Shadow @Nullable public abstract PlayerEntity getCustomer();

    private MerchantEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "trade", at = @At("TAIL"))
    public void trade(TradeOffer offer, CallbackInfo ci) {
        List<ActionOnTradePower> powers = PowerHolderComponent.getPowers(this.getCustomer(), ActionOnTradePower.class);
        powers.forEach(power -> {
            if (power.isActive() && (power.bientityCondition == null || power.bientityCondition.test(new Pair<>(this.getCustomer(), this)))) {
                power.bientityAction.accept(new Pair<>(this.getCustomer(), this));
            }
        });
    }
}
