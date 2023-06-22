package net.thephantompig791.appli.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.thephantompig791.appli.Appli;
import oshi.util.tuples.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RadialMenu {
    private List<RadialMenuEntry> entries;
    public RadialMenu(List<RadialMenuEntry> entries) {
        if (entries != null && entries.size() == 0) return;
        this.entries = entries;
    }

    public void draw(MinecraftClient client) {
        Map<Pair<Double, Double>, RadialMenuEntry> map = getPositionedEntries(client);
        map.forEach(((position, radialMenuEntry) -> {
            if (click(client).equals(radialMenuEntry)) RenderSystem.getModelViewMatrix().scale(2, 2, 0);
            if (click(client).equals(radialMenuEntry)) Appli.LOGGER.info("closest radial menu entry: " + radialMenuEntry);
            client.getItemRenderer().renderInGui(
                    radialMenuEntry.getStack(),
                    (int) Math.round(position.getA()),
                    (int) Math.round(position.getB())
            );
        }));
    }

    /*public RadialMenuEntry getRadialMenuEntry(MinecraftClient client) {
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
    }*/

    public RadialMenuEntry click(MinecraftClient client) {
        Map<Pair<Double, Double>, RadialMenuEntry> map = getPositionedEntries(client);
        double mouseX = client.mouse.getX();
        double mouseY = client.mouse.getY();

        RadialMenuEntry closestEntry = null;
        double closestDistance = Double.MAX_VALUE;

        for (Map.Entry<Pair<Double, Double>, RadialMenuEntry> entry : map.entrySet()) {
            Pair<Double, Double> position = entry.getKey();
            RadialMenuEntry radialMenuEntry = entry.getValue();
            double distance = Math.sqrt(Math.pow(mouseX - position.getA(), 2) + Math.pow(mouseY - position.getB(), 2));
            if (distance < closestDistance) {
                closestDistance = distance;
                closestEntry = radialMenuEntry;
            }
        }

        return closestEntry;
    }

    private Map<Pair<Double, Double>, RadialMenuEntry> getPositionedEntries(MinecraftClient client) {
        float angleInterval = 360f / entries.size();
        Map<Pair<Double, Double>, RadialMenuEntry> map = new HashMap<>();
        for (int i = 0; i < entries.size(); i++) {
            float angle = angleInterval * i;
            double x0 = client.getWindow().getWidth() / 4F;
            double y0 = client.getWindow().getHeight() / 4F;
            int distance = 40;
            Pair<Double, Double> position = getPosFromAngle(angle, distance, new Pair<>(x0, y0));
            map.put(new Pair<>(position.getA().floatValue() - 8d, position.getB().floatValue() - 8d), entries.get(i));
        }
        return map;
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
