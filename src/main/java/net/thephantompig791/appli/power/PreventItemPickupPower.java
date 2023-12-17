package net.thephantompig791.appli.power;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.thephantompig791.appli.Appli;
import oshi.util.tuples.Pair;

import java.util.function.Predicate;

public class PreventItemPickupPower extends Power {
    public Predicate<ItemStack> itemCondition;
    public Predicate<Pair<Entity, Entity>> bientityCondition;

    public static PowerFactory<?> getFactory() {
        return new PowerFactory<>(Appli.identifier("prevent_item_pickup"),
                new SerializableData()
                        .add("item_condition", ApoliDataTypes.ITEM_CONDITION, null)
                        .add("bientity_condition", ApoliDataTypes.BIENTITY_CONDITION, null),
                data ->
                    (type, player) ->
                            new PreventItemPickupPower(type, player, data.get("item_condition"), data.get("bientity_condition"))
                ).allowCondition();
    }

    public PreventItemPickupPower(PowerType<?> type, LivingEntity entity, Predicate<ItemStack> itemCondition, Predicate<Pair<Entity, Entity>> bientityCondition) {
        super(type, entity);
        this.itemCondition = itemCondition;
        this.bientityCondition = bientityCondition;
    }
}
