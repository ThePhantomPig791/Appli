package net.thephantompig791.appli.data;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.thephantompig791.appli.util.ModelPartTransformation;
import net.thephantompig791.appli.util.RadialMenuEntry;

import java.util.List;

public class AppliDataTypes {
    public static final SerializableDataType<RadialMenuEntry> RADIAL_MENU_ENTRY = SerializableDataType.compound(
            RadialMenuEntry.class,
            new SerializableData()
                    .add("item", SerializableDataTypes.ITEM_STACK)
                    .add("entity_action", ApoliDataTypes.ENTITY_ACTION)
                    .add("condition", ApoliDataTypes.ENTITY_CONDITION, null)
                    .add("distance", SerializableDataTypes.INT, -1)
                    .add("velocity", SerializableDataTypes.INT, -1),
            data -> new RadialMenuEntry(
                    data.get("item"),
                    data.get("entity_action"),
                    data.get("condition"),
                    data.get("distance"),
                    data.get("velocity")
            ),
            (data, inst) -> {
                SerializableData.Instance dataInst = data.new Instance();
                dataInst.set("item", inst.getStack());
                dataInst.set("entity_action", inst.getEntityAction());
                dataInst.set("condition", inst.getCondition());
                dataInst.set("distance", inst.getDistance());
                dataInst.set("velocity", inst.getVelocity());
                return dataInst;
            });

    public static final SerializableDataType<List<RadialMenuEntry>> RADIAL_MENU_ENTRIES =
            SerializableDataType.list(RADIAL_MENU_ENTRY);


    public static final SerializableDataType<ModelPartTransformation> MODEL_PART_TRANSFORMATION = SerializableDataType.compound(
            ModelPartTransformation.class,
            new SerializableData()
                    .add("model_part", SerializableDataTypes.STRING)
                    .add("type", SerializableDataTypes.STRING)
                    .add("value", SerializableDataTypes.FLOAT),
            data -> new ModelPartTransformation(
                    data.get("model_part"),
                    data.get("type"),
                    data.get("value")
            ),
            (data, inst) -> {
                SerializableData.Instance dataInst = data.new Instance();
                dataInst.set("model_part", inst.getModelPart());
                dataInst.set("type", inst.getType());
                dataInst.set("value", inst.getValue());
                return dataInst;
            });

    public static final SerializableDataType<List<ModelPartTransformation>> MODEL_PART_TRANSFORMATIONS =
            SerializableDataType.list(MODEL_PART_TRANSFORMATION);
}
