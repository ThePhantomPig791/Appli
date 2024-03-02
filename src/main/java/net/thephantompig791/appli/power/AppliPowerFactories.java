package net.thephantompig791.appli.power;

import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import net.minecraft.registry.Registry;
import net.thephantompig791.appli.Appli;

public class AppliPowerFactories {
    public static void register() {
        register(PreventKeysPower.getFactory());
        register(PreventMousePower.createSimpleFactory(PreventMousePower::new, Appli.identifier("prevent_mouse")));
        register(ModifyModelPartsPower.getFactory());
        register(ActionOnTradePower.getFactory());
        register(PreventItemPickupPower.getFactory());
        register(ActionOnKillPower.getFactory());
        register(PreventTradePower.getFactory());
        register(FadingModelColorPower.getFactory());
    }

    private static void register(PowerFactory<?> p) {
        Registry.register(ApoliRegistries.POWER_FACTORY, p.getSerializerId(), p);
    }
}
