package net.thephantompig791.appli.titan_shifters_content.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.thephantompig791.appli.titan_shifters_content.entity.TitanSkinBoltEntity;

public class TitanSkinCrossbowItem extends Item {
    public TitanSkinCrossbowItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack item = user.getStackInHand(hand);
        if (item.getOrCreateNbt().getBoolean("loaded")) {
            TitanSkinBoltEntity bolt = new TitanSkinBoltEntity(world, user);
            bolt.setVelocity(user, user.getPitch(), user.getYaw(), 0, 2, 0.1f);
            world.spawnEntity(bolt);

            user.playSound(SoundEvents.ITEM_CROSSBOW_SHOOT, 1, 1.5f);

            item.getOrCreateNbt().putBoolean("loaded", false);

            Stats.USED.getOrCreateStat(this);

            return TypedActionResult.consume(item);
        } else if (user.getOffHandStack().isOf(TitanShiftersItems.TITAN_SKIN_BOLT)) {
            if (!user.getAbilities().creativeMode) user.getOffHandStack().decrement(1);
            item.getOrCreateNbt().putBoolean("loaded", true);
            user.swingHand(Hand.OFF_HAND);

            user.playSound(SoundEvents.ITEM_CROSSBOW_QUICK_CHARGE_3, 1, 0.5f);

            Stats.USED.getOrCreateStat(TitanShiftersItems.TITAN_SKIN_BOLT);

            return TypedActionResult.consume(item);
        }
        return TypedActionResult.pass(item);
    }
}
