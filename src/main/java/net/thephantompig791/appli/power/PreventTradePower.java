package net.thephantompig791.appli.power;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.util.Pair;
import net.minecraft.world.World;
import net.thephantompig791.appli.Appli;

import java.util.function.Predicate;

public class PreventTradePower extends Power {
    public Predicate<Pair<World, StackReference>> sellItemCondition;
    public Predicate<Pair<World, StackReference>> buyItemCondition;
    public Predicate<Pair<World, StackReference>> secondBuyItemCondition;
    public Predicate<Pair<Entity, Entity>> bientityCondition;
    public boolean buyItemConditionConsiderAdjustments;

    public static PowerFactory<?> getFactory() {
        return new PowerFactory<>(Appli.identifier("prevent_trade"),
                new SerializableData()
                        .add("buy_item_condition", ApoliDataTypes.ITEM_CONDITION, null)
                        .add("sell_item_condition", ApoliDataTypes.ITEM_CONDITION, null)
                        .add("second_sell_item_condition", ApoliDataTypes.ITEM_CONDITION, null)
                        .add("bientity_condition", ApoliDataTypes.BIENTITY_CONDITION, null)
                        .add("buy_item_condition_consider_adjustments", SerializableDataTypes.BOOLEAN, true),
                data ->
                    (type, player) ->
                            new PreventTradePower(type, player, data.get("buy_item_condition"), data.get("sell_item_condition"), data.get("second_sell_item_condition"), data.get("bientity_condition"), data.getBoolean("buy_item_condition_consider_adjustments"))
                ).allowCondition();
    }

    public PreventTradePower(PowerType<?> type, LivingEntity entity, Predicate<Pair<World, StackReference>> buyItemCondition, Predicate<Pair<World, StackReference>> sellItemCondition, Predicate<Pair<World, StackReference>> secondSellItemCondition, Predicate<Pair<Entity, Entity>> bientityCondition, boolean buyItemConditionConsiderAdjustments) {
        super(type, entity);
        this.buyItemCondition = buyItemCondition;
        this.sellItemCondition = sellItemCondition;
        this.secondBuyItemCondition = secondSellItemCondition;
        this.bientityCondition = bientityCondition;
        this.buyItemConditionConsiderAdjustments = buyItemConditionConsiderAdjustments;
    }
}
