package net.thephantompig791.appli.util;

import net.minecraft.client.MinecraftClient;
import net.thephantompig791.appli.Appli;
import oshi.util.tuples.Pair;

import java.util.List;

public class RadialMenu {
    private List<RadialMenuEntry> entries;
    public RadialMenu(List<RadialMenuEntry> entries) {
        if (entries != null && entries.size() == 0) return;
        this.entries = entries;
    }

    public void draw(MinecraftClient client) {
        float angleInterval = 360f / entries.size();
        for (int i = 0; i < entries.size(); i++) {
            float angle = angleInterval * i;
            double x0 = client.getWindow().getWidth() / 4F;
            double y0 = client.getWindow().getHeight() / 4F;
            int distance = 40;
            Pair<Double, Double> position = getPosFromAngle(angle, distance, new Pair<>(x0, y0));
            client.getItemRenderer().renderInGui(entries.get(i).getStack(),
                    Math.round(position.getA().floatValue()) - 8,
                    Math.round(position.getB().floatValue()) - 8
            );
        }
    }

    public RadialMenuEntry getRadialMenuEntry(MinecraftClient client) {
        int angleInterval = 360 / entries.size();

        double x0 = client.getWindow().getWidth() / 2F;
        double y0 = client.getWindow().getHeight() / 2F;

        double angle = getAngleFromPos(new Pair<>(getMousePosFromCenter(client).getA(), getMousePosFromCenter(client).getB()), new Pair<>(x0, y0));

        int entryNumber = (int) (Math.ceil(angle / angleInterval) - 1);
        Appli.LOGGER.info("entry: " + entryNumber + ", angle: " + angle);
        if (entryNumber < 0) return null;

        Appli.LOGGER.info("entry #: " + Math.ceil(angle / angleInterval) + ", angle: " + angle);
        Appli.LOGGER.info("entry action: " + entries.get(entryNumber).getEntityAction());

        return entries.get(entryNumber);
    }


    public static Pair<Double, Double> getPosFromAngle(float angle, float distance, Pair<Double, Double> center) {
        return new Pair<>(center.getA() + distance * Math.cos(angle * (Math.PI / 180)), center.getB() + distance * Math.sin(angle * (Math.PI / 180)));
    }

    public static double getAngleFromPos(Pair<Double, Double> position, Pair<Double, Double> center) {
        double deltaY = center.getB() - position.getB();
        double deltaX = center.getA() - position.getA();
        double angleInRadians = Math.atan2(deltaY, deltaX);
        double angleInDegrees = angleInRadians * (180 / Math.PI);
        if (angleInDegrees < 0)
            angleInDegrees += 360;
        return angleInDegrees;
    }

    public static Pair<Double, Double> getMousePosFromCenter(MinecraftClient client) {
        double x0 = client.getWindow().getWidth() / 2F;
        double y0 = client.getWindow().getHeight() / 2F;
        return new Pair<>(client.mouse.getX() - x0, y0 - client.mouse.getY());
    }
}
