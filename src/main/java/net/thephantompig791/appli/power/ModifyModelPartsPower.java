package net.thephantompig791.appli.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.LivingEntity;
import net.thephantompig791.appli.Appli;
import net.thephantompig791.appli.data.AppliDataTypes;
import net.thephantompig791.appli.util.ModelPartTransformation;

import java.util.List;

public class ModifyModelPartsPower extends Power {
    private final List<ModelPartTransformation> transformations;


    public static PowerFactory<?> getFactory() {
        return new PowerFactory<>(Appli.identifier("modify_model_parts"),
                new SerializableData()
                        .add("transformations", AppliDataTypes.MODEL_PART_TRANSFORMATIONS, null),
                data ->
                        (type, player) ->
                            new ModifyModelPartsPower(type, player, data.get("transformations")))
                .allowCondition();
    }

    public ModifyModelPartsPower(PowerType<?> type, LivingEntity entity, List<ModelPartTransformation> transformations) {
        super(type, entity);
        this.transformations = transformations;
    }

    public List<ModelPartTransformation> getTransformations() {
        return transformations;
    }
}
