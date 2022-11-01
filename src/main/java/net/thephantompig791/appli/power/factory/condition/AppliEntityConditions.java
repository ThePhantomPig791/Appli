package net.thephantompig791.appli.power.factory.condition;

import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.util.registry.Registry;
import net.thephantompig791.appli.Appli;

public class AppliEntityConditions {
    public static void register() {
        register(new ConditionFactory<>(Appli.identifier("name"), new SerializableData()
            .add("name", SerializableDataTypes.STRING, null)
            .add("match_case", SerializableDataTypes.BOOLEAN, true),
            (data, entity) -> {
                String name = data.getString("name");
                boolean match_case = data.getBoolean("match_case");
                String entityName = entity.getName().toString()
                        .replace("literal{", "")
                        .replace("}", "")
                        .replace("[style={]", "")
                        .replace("translation{key='entity.minecraft.", "")
                        .replace("', args=[]", "");
                return match_case ? entityName.equals(name) : entityName.equalsIgnoreCase(name);
            }));


        register(new ConditionFactory<>(Appli.identifier("uuid"), new SerializableData()
                .add("uuid", SerializableDataTypes.STRING, null),
                (data, entity) -> {
                    String uuid = data.getString("uuid");
                    return entity.getUuidAsString().equals(uuid);
                }));
    }

    private static void register(ConditionFactory<Entity> conditionFactory) {
        Registry.register(ApoliRegistries.ENTITY_CONDITION, conditionFactory.getSerializerId(), conditionFactory);
    }
}
