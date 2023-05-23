package net.thephantompig791.appli.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;
import net.thephantompig791.appli.Appli;

import java.util.List;

public class DisableMousePower extends Power {
    public DisableMousePower(PowerType<?> type, LivingEntity entity) {
        super(type, entity);
    }
}
