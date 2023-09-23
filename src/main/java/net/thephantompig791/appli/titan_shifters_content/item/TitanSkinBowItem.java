package net.thephantompig791.appli.titan_shifters_content.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import net.thephantompig791.appli.titan_shifters_content.entity.TitanSkinBoltEntity;

import java.util.function.Predicate;

public class TitanSkinBowItem extends BowItem {
    public TitanSkinBowItem(Settings settings) {
        super(settings);
    }

    @Override
    public Predicate<ItemStack> getProjectiles() {
        return itemStack -> itemStack.isOf(TitanShiftersItems.TITAN_SKIN_BOLT);
    }

    @Override
    public int getRange() {
        return 8;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity player && !player.getAbilities().creativeMode) {
            if (!player.getOffHandStack().isOf(TitanShiftersItems.TITAN_SKIN_BOLT)) return;
            player.getOffHandStack().decrement(1);
        }

        int i = getMaxUseTime(stack) - remainingUseTicks;

        TitanSkinBoltEntity bolt = new TitanSkinBoltEntity(world, user);
        bolt.setVelocity(user, user.getPitch(), user.getYaw(), 0, (float) Math.min(i / 10.0, 3.0), 1f);
        world.spawnEntity(bolt);

        user.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1, 0.7f);

        Stats.USED.getOrCreateStat(this);
    }

    @Override
    public boolean isUsedOnRelease(ItemStack stack) {
        return stack.isOf(this);
    }
}
