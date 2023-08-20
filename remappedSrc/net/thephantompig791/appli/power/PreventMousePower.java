package net.thephantompig791.appli.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.LivingEntity;

public class PreventMousePower extends Power {
    public PreventMousePower(PowerType<?> type, LivingEntity entity) {
        super(type, entity);
    }
}
