package net.thephantompig791.appli.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.thephantompig791.appli.Appli;

import java.util.List;

public class RadialMenu {
    private List<RadialMenuEntry> entries;
    public RadialMenu(List<RadialMenuEntry> entries) {
        this.entries = entries;
    }

    public void draw(MatrixStack matrixStack, MinecraftClient client) {

    }

    public RadialMenuEntry getRadialMenuEntry(MinecraftClient client) {
        int angleInterval = 360 / entries.size();

        double x0 = client.getWindow().getWidth() / 2F;
        double y0 = client.getWindow().getHeight() / 2F;
        double x = client.mouse.getX() - x0;
        double y = y0 - client.mouse.getY();

        float angle = (float) Math.tan((x) / (y));

        Appli.LOGGER.info("entry: " + Math.ceil(angle / angleInterval));


        return null;
    }
}
