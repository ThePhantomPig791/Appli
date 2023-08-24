package net.thephantompig791.appli.util;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.thephantompig791.appli.Appli;
import net.thephantompig791.appli.packet.AppliNetworkingConstants;

public class RadialMenuEntry {
    private final ItemStack stack;
    private final ActionFactory<LivingEntity>.Instance action;
    private Vector2f position;
    private final int distance;
    private final int velocity;

    private ButtonWidget button;

    public RadialMenuEntry(ItemStack stack, ActionFactory<LivingEntity>.Instance action, int distance, int velocity) {
        this.stack = stack;
        this.action = action;
        position = new Vector2f(-100f, 0f);
        this.distance = distance;
        this.velocity = velocity;
        button = new ButtonWidget(
                -100,
                0,
                16,
                20,
                Text.empty(),
                (widget -> {
                    PacketByteBuf buf = PacketByteBufs.create();
                    this.action.write(buf);
                    ClientPlayNetworking.send(AppliNetworkingConstants.RADIAL_MENU_CLIENT_TO_SERVER, buf);
                }),
                (button, matrices, mouseX, mouseY) -> stack.getName().getString()
        );
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

    public ButtonWidget getButton() {
        return button;
    }

    public void setButton(ButtonWidget button) {
        this.button = button;
    }
}
