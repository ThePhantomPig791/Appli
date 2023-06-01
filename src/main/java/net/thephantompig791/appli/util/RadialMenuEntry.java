package net.thephantompig791.appli.util;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class RadialMenuEntry {
    private final ItemStack stack;
    private final ActionFactory<LivingEntity>.Instance action;

    public RadialMenuEntry(ItemStack stack, ActionFactory<LivingEntity>.Instance action) {
        this.stack = stack;
        this.action = action;
    }

    public ItemStack getStack() {
        return stack;
    }

    public ActionFactory<LivingEntity>.Instance getEntityAction() {
        return action;
    }
}