package net.thephantompig791.appli.power;

import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import net.minecraft.registry.Registry;

public class AppliPowerFactories {
    public static void register() {
        register(ResourcePackPower.getFactory());
    }

    private static void register(PowerFactory<?> p) {
        Registry.register(ApoliRegistries.POWER_FACTORY, p.getSerializerId(), p);
    }
}
