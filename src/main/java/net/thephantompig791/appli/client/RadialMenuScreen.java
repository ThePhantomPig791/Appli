package net.thephantompig791.appli.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
        radialMenu.getEntries().forEach(entry -> {
            if (entry.getButton() != null) addDrawableChild(entry.getButton());
            //Appli.LOGGER.info("wah | " + entry.getButton() + " | " + children());
        });
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        //super.render(matrices, mouseX, mouseY, delta);
        if (client != null) {
            this.setZOffset(100);
            this.radialMenu.getEntries().forEach(radialMenuEntry -> {
                if (radialMenuEntry.getButton() != null) radialMenuEntry.getButton().render(matrices, mouseX, mouseY, delta);
                client.getItemRenderer().renderInGui(
                        radialMenuEntry.getStack(),
                        Math.round(radialMenuEntry.getPosition().x()),
                        Math.round(radialMenuEntry.getPosition().y())
                );
                addDrawableChild(radialMenuEntry.getButton());
            });
            this.setZOffset(0);
        }
    }
}
