package net.thephantompig791.appli.power;

import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import net.minecraft.registry.Registry;
import net.thephantompig791.appli.Appli;

public class AppliPowerFactories {
    public static void register() {
        register(DisableKeysPower.getFactory());
        register(DisableMousePower.createSimpleFactory(DisableMousePower::new, Appli.identifier("disable_mouse")));
    }

    private static void register(PowerFactory<?> p) {
        Registry.register(ApoliRegistries.POWER_FACTORY, p.getSerializerId(), p);
    }
}
