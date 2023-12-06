package net.thephantompig791.appli.power;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import net.minecraft.world.World;
import net.thephantompig791.appli.Appli;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class ActionOnTradePower extends Power {
    public Consumer<Pair<World, ItemStack>> itemAction;
    public Consumer<Pair<Entity, Entity>> bientityAction;
    public Predicate<Pair<Entity, Entity>> bientityCondition;


    public static PowerFactory<?> getFactory() {
        return new PowerFactory<>(Appli.identifier("action_on_trade"),
                new SerializableData()
                        .add("item_action", ApoliDataTypes.ITEM_ACTION)
                        .add("bientity_action", ApoliDataTypes.BIENTITY_ACTION)
                        .add("bientity_condition", ApoliDataTypes.BIENTITY_CONDITION, null),
                data ->
                        (type, player) ->
                                new ActionOnTradePower(type, player, data.get("item_action"), data.get("bientity_action"), data.get("bientity_condition"))
        );
    }

    public ActionOnTradePower(PowerType<?> type, LivingEntity entity, Consumer<Pair<World, ItemStack>> itemAction, Consumer<Pair<Entity, Entity>> action, Predicate<Pair<Entity, Entity>> condition) {
        super(type, entity);
        this.itemAction = itemAction;
        this.bientityAction = action;
        this.bientityCondition = condition;
    }
}
