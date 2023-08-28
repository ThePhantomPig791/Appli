package net.thephantompig791.appli.util;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class RadialMenuEntry {
    private final ItemStack stack;
    private final ActionFactory<LivingEntity>.Instance action;
    private Vector2f position;
    private final int distance;
    private final int velocity;

    @Environment(EnvType.CLIENT)
    private ButtonWidget button;

    public RadialMenuEntry(ItemStack stack, ActionFactory<LivingEntity>.Instance action, int distance, int velocity) {
        this.stack = stack;
        this.action = action;
        position = new Vector2f(-100f, 0f);
        this.distance = distance;
        this.velocity = velocity;
    }

    public ItemStack getStack() {
        return stack;
    }

    public ActionFactory<LivingEntity>.Instance getEntityAction() {
        return action;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public int getDistance() {
        return distance;
    }

    public int getVelocity() {
        return velocity;
    }

    @Environment(EnvType.CLIENT)
    public ButtonWidget getButton() {
        return button;
    }

    @Environment(EnvType.CLIENT)
    public void setButton(ButtonWidget button) {
        this.button = button;
    }
}
