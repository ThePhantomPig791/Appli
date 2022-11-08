package net.thephantompig791.appli.power.factory.action;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.Entity;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.thephantompig791.appli.Appli;

public class AppliBiEntityActions {

    public static void register() {
        register(new ActionFactory<>(Appli.identifier("swap_positions"), new SerializableData(),
                (data, entities) -> {
                    Entity actor = entities.getLeft(), target = entities.getRight();
                    Vec3d actorNewPos = target.getPos();
                    target.setPosition(actor.getPos());
                    actor.setPosition(actorNewPos);
                }));


        register(new ActionFactory<>(Appli.identifier("teleport"), new SerializableData(),
                (data, entities) -> {
                    Entity actor = entities.getLeft(), target = entities.getRight();
                    actor.setPosition(target.getPos());
                }));
    }

    private static void register(ActionFactory<Pair<Entity, Entity>> actionFactory) {
        Registry.register(ApoliRegistries.BIENTITY_ACTION, actionFactory.getSerializerId(), actionFactory);
    }
}
