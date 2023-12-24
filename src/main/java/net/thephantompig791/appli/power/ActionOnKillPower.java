package net.thephantompig791.appli.power;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.CooldownPower;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.util.HudRender;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import net.minecraft.world.World;
import net.thephantompig791.appli.Appli;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class ActionOnKillPower extends CooldownPower {
    public Consumer<Pair<Entity, Entity>> bientityAction;
    public Predicate<Pair<Entity, Entity>> bientityCondition;

    public Predicate<Pair<DamageSource, Float>> damageCondition;

    public static PowerFactory<?> getFactory() {
        return new PowerFactory<>(Appli.identifier("action_on_kill"),
                new SerializableData()
                        .add("bientity_action", ApoliDataTypes.BIENTITY_ACTION)
                        .add("bientity_condition", ApoliDataTypes.BIENTITY_CONDITION, null)
                        .add("damage_condition", ApoliDataTypes.DAMAGE_CONDITION, null)
                        .add("cooldown", SerializableDataTypes.INT, 0)
                        .add("hud_render", ApoliDataTypes.HUD_RENDER, HudRender.DONT_RENDER),
                data ->
                        (type, player) ->
                                new ActionOnKillPower(type, player, data.getInt("cooldown"), data.get("hud_render"), data.get("bientity_action"), data.get("bientity_condition"), data.get("damage_condition"))
                ).allowCondition();
    }

    public ActionOnKillPower(PowerType<?> type, LivingEntity entity, int cooldownDuration, HudRender hudRender, Consumer<Pair<Entity, Entity>> action, Predicate<Pair<Entity, Entity>> condition, Predicate<Pair<DamageSource, Float>> damageCondition) {
        super(type, entity, cooldownDuration, hudRender);
        this.bientityAction = action;
        this.bientityCondition = condition;
        this.damageCondition = damageCondition;
    }

    public void onKill(Entity actor, Entity target, DamageSource damageSource, float damageAmount) {
        if (
                (this.bientityCondition == null || this.bientityCondition.test(new Pair<>(actor, target))) &&
                (this.damageCondition == null || this.damageCondition.test(new Pair<>(damageSource, damageAmount))) &&
                this.canUse()
        ) {
            this.bientityAction.accept(new Pair<>(actor, target));
            this.use();
        }
    }
}
