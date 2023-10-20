package net.thephantompig791.appli.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.thephantompig791.appli.util.RadialMenu;

@Environment(EnvType.CLIENT)
public class RadialMenuScreen extends Screen {
    private int elapsedTime;

    private final RadialMenu radialMenu;

    protected RadialMenuScreen(RadialMenu radialMenu) {
        super(Text.literal("Radial Menu"));
        this.radialMenu = radialMenu;
        elapsedTime = 0;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }

    public RadialMenu getRadialMenu() {
        return radialMenu;
    }

    @Override
    public void tick() {
        elapsedTime += 1;
        radialMenu.draw(this.client, elapsedTime);
    }

    @Override
    public void init() {
        radialMenu.draw(this.client, elapsedTime);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (client != null) {
            clearChildren();
            this.radialMenu.getEntries().forEach(radialMenuEntry -> {
                if (radialMenuEntry.getButton() != null) radialMenuEntry.getButton().render(context, mouseX, mouseY, delta);
                context.drawItem(
                        radialMenuEntry.getStack(),
                        Math.round(radialMenuEntry.getPosition().x()),
                        Math.round(radialMenuEntry.getPosition().y()),
                        0,
                        100
                );
                addDrawableChild(radialMenuEntry.getButton());
            });
        }
    }
}
