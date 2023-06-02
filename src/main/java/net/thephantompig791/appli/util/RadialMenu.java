package net.thephantompig791.appli.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.thephantompig791.appli.Appli;

import java.util.List;

public class RadialMenu {
    private List<RadialMenuEntry> entries;
    public RadialMenu(List<RadialMenuEntry> entries) {
        if (entries.size() == 0) return;
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

        //add 90 degrees for each quadrant going clockwise based on the negative-ness of x and y
        int angleToAdd = 0; //quadrant 1
        if (!isNegative(x) && isNegative(y)) angleToAdd = 90; //quadrant 4
        else if (isNegative(x) && isNegative(y)) angleToAdd = 180; //quadrant 3
        else if (isNegative(x) && !isNegative(y)) angleToAdd = 270; //quadrant 2
        float angle = (float) Math.tan((Math.abs(x)) / (Math.abs(y))) + angleToAdd;

        Appli.LOGGER.info("entry #: " + Math.ceil(angle / angleInterval));
        Appli.LOGGER.info("entry action: " + entries.get((int) (Math.ceil(angle / angleInterval) - 1)).getEntityAction().getClass().descriptorString());

        return entries.get((int) (Math.ceil(angle / angleInterval) - 1));
    }

    private boolean isNegative(double i) {
        return i < 0.0;
    }
}
