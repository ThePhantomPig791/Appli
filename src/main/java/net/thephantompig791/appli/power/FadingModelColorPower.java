package net.thephantompig791.appli.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.thephantompig791.appli.Appli;

public class FadingModelColorPower extends Power {
    private final float maxRed, maxGreen, maxBlue, maxAlpha;
    private final int maxTicks;

    private float red = 1, green = 1, blue = 1, alpha = 1;
    private int ticks;

    public static PowerFactory<?> getFactory() {
        return new PowerFactory<>(Appli.identifier("fading_model_color"),
                new SerializableData()
                        .add("red", SerializableDataTypes.FLOAT, -1.0F)
                        .add("green", SerializableDataTypes.FLOAT, -1.0F)
                        .add("blue", SerializableDataTypes.FLOAT, -1.0F)
                        .add("alpha", SerializableDataTypes.FLOAT, -1.0F)
                        .add("ticks", SerializableDataTypes.INT, 20),
                data ->
                    (type, player) ->
                            new FadingModelColorPower(type, player, data.getFloat("red"), data.getFloat("green"), data.getFloat("blue"), data.getFloat("alpha"), data.getInt("ticks"))
                ).allowCondition();
    }

    public FadingModelColorPower(PowerType<?> type, LivingEntity entity, float red, float green, float blue, float alpha, int ticks) {
        super(type, entity);
        this.maxRed = red;
        this.maxGreen = green;
        this.maxBlue = blue;
        this.maxAlpha = alpha;
        this.maxTicks = ticks;

        this.setTicking(true);
    }

    @Override
    public NbtElement toTag() {
        NbtCompound tag = new NbtCompound();
        tag.putFloat("red", red);
        tag.putFloat("green", green);
        tag.putFloat("blue", blue);
        tag.putFloat("alpha", alpha);
        tag.putInt("ticks", ticks);
        return tag;
    }

    @Override
    public void fromTag(NbtElement tag) {
        if (tag instanceof NbtCompound compound) {
            red = compound.getFloat("red");
            green = compound.getFloat("green");
            blue = compound.getFloat("blue");
            alpha = compound.getFloat("alpha");
            ticks = compound.getInt("ticks");
            Appli.LOGGER.info("red: " + red + ", alpha: " + alpha + ", ticks: " + ticks);
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isActive() && ticks < maxTicks) ticks++;
        if (!this.isActive() && ticks > 0) ticks--;

        float progress = (float) ticks / maxTicks;
        if (maxRed >= 0) red = maxRed * progress;
        if (maxGreen >= 0) green = maxGreen * progress;
        if (maxBlue >= 0) blue = maxBlue * progress;
        if (maxAlpha >= 0) alpha = maxAlpha * progress;
    }

    public boolean isTranslucent() {
        return alpha < 1;
    }

    public float getRed() {
        return red;
    }
    public float getGreen() {
        return green;
    }
    public float getBlue() {
        return blue;
    }
    public float getAlpha() {
        return alpha;
    }

    public int getTicks() {
        return ticks;
    }
}
