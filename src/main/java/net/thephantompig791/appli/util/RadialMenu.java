package net.thephantompig791.appli.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.thephantompig791.appli.packet.AppliNetworkingConstants;
import oshi.util.tuples.Pair;

import java.util.List;

public class RadialMenu {
    private List<RadialMenuEntry> entries;

    public RadialMenu(List<RadialMenuEntry> entries) {
        if (entries != null && entries.size() == 0) return;
        this.entries = entries;
    }

    @Environment(EnvType.CLIENT)
    public void draw(MinecraftClient client, long elapsedTime) {
        positionEntries(client, elapsedTime);
        entries.forEach((radialMenuEntry -> {
            ButtonWidget button = new ButtonWidget(
                    -100,
                    0,
                    16,
                    20,
                    Text.empty(),
                    (widget -> {
                        PacketByteBuf buf = PacketByteBufs.create();
                        radialMenuEntry.getEntityAction().write(buf);
                        ClientPlayNetworking.send(AppliNetworkingConstants.RADIAL_MENU_CLIENT_TO_SERVER, buf);
                    }),
                    (buttonWidget, matrices, mouseX, mouseY) -> radialMenuEntry.getStack().getName().getString()
            );
            button.x = Math.round(radialMenuEntry.getPosition().x());
            button.y = Math.round(radialMenuEntry.getPosition().y() - 1);
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
            entries.get(i).setPosition(new Vector2f(position.x() - 8f, position.y() - 10f));
        }
    }


    public static Vector2f getPosFromAngle(float angle, float distance, Vector2f center) {
        return new Vector2f((float) (center.x() + distance * Math.cos(angle * (Math.PI / 180))), (float) (center.y() + distance * Math.sin(angle * (Math.PI / 180))));
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
