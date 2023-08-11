package net.thephantompig791.appli.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.Vector2f;
import oshi.util.tuples.Pair;

import java.util.List;

public class RadialMenu {
    private List<RadialMenuEntry> entries;

    public RadialMenu(List<RadialMenuEntry> entries) {
        if (entries != null && entries.size() == 0) return;
        this.entries = entries;
    }

    public void draw(MinecraftClient client, long elapsedTime) {
        positionEntries(client, elapsedTime);
        entries.forEach((radialMenuEntry -> {
            ButtonWidget button = radialMenuEntry.getButton();
            button.setPos(
                    Math.round(radialMenuEntry.getPosition().getX()),
                    Math.round(radialMenuEntry.getPosition().getY() - 1)
            );
            radialMenuEntry.setButton(button);
        }));
    }

    private void positionEntries(MinecraftClient client, long elapsedTime) {
        float angleInterval = 360f / entries.size();
        for (int i = 0; i < entries.size(); i++) {
            float angle = angleInterval * i;
            Vector2f center = new Vector2f(
                    client.getWindow().getScaledWidth() / 2f,
                    client.getWindow().getScaledHeight() / 2f
            );

            int maxDistance = entries.get(i).getDistance() != -1 ? entries.get(i).getDistance() : client.getWindow().getScaledHeight() / 4;
            float velocity = entries.get(i).getVelocity() != -1 ? entries.get(i).getVelocity() : maxDistance / 3f;
            float distance = velocity * elapsedTime < maxDistance ? velocity * elapsedTime : maxDistance;

            Vector2f position = getPosFromAngle(angle, distance, center);
            entries.get(i).setPosition(new Vector2f(position.getX() - 8f, position.getY() - 10f));
        }
    }


    public static Vector2f getPosFromAngle(float angle, float distance, Vector2f center) {
        return new Vector2f((float) (center.getX() + distance * Math.cos(angle * (Math.PI / 180))), (float) (center.getY() + distance * Math.sin(angle * (Math.PI / 180))));
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

    public List<RadialMenuEntry> getEntries() {
        return entries;
    }
}
