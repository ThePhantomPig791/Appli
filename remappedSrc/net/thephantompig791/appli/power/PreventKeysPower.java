package net.thephantompig791.appli.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;
import net.thephantompig791.appli.Appli;

import java.util.ArrayList;
import java.util.List;

public class PreventKeysPower extends Power {
    private final List<Integer> keys;

    public static PowerFactory<?> getFactory() {
        return new PowerFactory<>(Appli.identifier("prevent_keys"),
                new SerializableData()
                        .add("keys", SerializableDataTypes.INTS, new ArrayList<>()),
                data ->
                        (type, player) ->
                                new PreventKeysPower(type, player, data.get("keys")))
                .allowCondition();
    }

    public PreventKeysPower(PowerType<?> type, LivingEntity entity, List<Integer> keys) {
        super(type, entity);
        this.keys = keys;
    }

    public List<Integer> getKeys() {
        return keys;
    }
}
