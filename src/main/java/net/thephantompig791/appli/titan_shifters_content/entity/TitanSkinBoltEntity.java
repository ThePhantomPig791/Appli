package net.thephantompig791.appli.titan_shifters_content.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import net.thephantompig791.appli.titan_shifters_content.TitanShifters;
import net.thephantompig791.appli.titan_shifters_content.item.TitanShiftersItems;

import java.util.Random;

public class TitanSkinBoltEntity extends PersistentProjectileEntity {
    public TitanSkinBoltEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public TitanSkinBoltEntity(World world, LivingEntity owner) {
        super(TitanShifters.TITAN_SKIN_BOLT_ENTITY, owner, world);
    }

    public TitanSkinBoltEntity(World world, double x, double y, double z) {
        super(TitanShifters.TITAN_SKIN_BOLT_ENTITY, x, y, z, world);
    }

    @Override
    protected ItemStack asItemStack() {
        return TitanShiftersItems.TITAN_SKIN_BOLT.getDefaultStack();
    }

    @Environment(EnvType.CLIENT)
    private ParticleEffect getParticleParameters() {
        return ParticleTypes.CLOUD;
    }

    @Override
    public void tick() {
        super.tick();
        Random r = new Random();
        if (world.isClient && Math.random() <= 0.25) this.world.addParticle(
                this.getParticleParameters(),
                this.getX(),
                this.getY(),
                this.getZ(),
                r.nextDouble(0.2) - 0.1,
                r.nextDouble(0.2),
                r.nextDouble(0.2) - 0.1);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        float i = Math.min((float) this.getVelocity().lengthSquared(), 5);

        entity.damage(DamageSource.mobProjectile(this, (LivingEntity) this.getOwner()), i / 5);
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.addStatusEffect(
                    (new StatusEffectInstance(StatusEffects.SLOWNESS, (int) (20 * Math.ceil(i)), 0)),
                    this.getOwner()
            );
        }
    }

    @Override
    protected boolean tryPickup(PlayerEntity player) {
        return false;
    }

    @Override
    protected SoundEvent getHitSound() {
        return SoundEvents.ITEM_TRIDENT_HIT;
    }
}
