package net.thephantompig791.appli.power.factory.condition;

import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;
import net.thephantompig791.appli.Appli;

public class AppliBiEntityConditions {

    public static void register() {
        register(new ConditionFactory<>(Appli.identifier("nbt"), new SerializableData()
                //this only works if you're checking root tags or a single sub tag (so OnGround works, data.testValue works, but data.object.testValue doesn't work)
                .add("actor_nbt", SerializableDataTypes.STRING)
                .add("target_nbt", SerializableDataTypes.STRING),
                (data, entities) -> {
                    Entity actor = entities.getLeft(), target = entities.getRight();

                    NbtCompound actorNbt = new NbtCompound();
                    actor.writeNbt(actorNbt);

                    String actorNbtValue;
                    if (data.getString("actor_nbt").contains(".")) {
                        NbtCompound actorNbtCompound = actorNbt.getCompound(data.getString("actor_nbt").substring(0, data.getString("actor_nbt").indexOf(".")));
                        actorNbtValue = String.valueOf(actorNbtCompound.get(data.getString("actor_nbt").substring(data.getString("actor_nbt").indexOf("."))));
                    }
                    else {
                        actorNbtValue = String.valueOf(actorNbt.get(data.getString("actor_nbt")));
                    }


                    NbtCompound targetNbt = new NbtCompound();
                    target.writeNbt(targetNbt);

                    String targetNbtValue;
                    if (data.getString("target_nbt").contains(".")) {
                        NbtCompound targetNbtCompound = targetNbt.getCompound(data.getString("target_nbt").substring(0, data.getString("target_nbt").indexOf(".")));
                        targetNbtValue = String.valueOf(targetNbtCompound.get(data.getString("target_nbt").substring(data.getString("target_nbt").indexOf(".")).replaceFirst("\\.", "")));
                    }
                    else {
                        targetNbtValue = String.valueOf(targetNbt.get(data.getString("target_nbt")));
                    }


                    return actorNbtValue.equals(targetNbtValue);
                }));
    }


    private static void register(ConditionFactory<Pair<Entity, Entity>> conditionFactory) {
        Registry.register(ApoliRegistries.BIENTITY_CONDITION, conditionFactory.getSerializerId(), conditionFactory);
    }
}
