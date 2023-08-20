package net.thephantompig791.appli.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;
import net.thephantompig791.appli.Appli;

public class ResourcePackPower extends Power {
    private final String url;
    private final int priority;


    public static PowerFactory<?> getFactory() {
        return new PowerFactory<>(Appli.identifier("resource_pack"),
                new SerializableData()
                        .add("url", SerializableDataTypes.STRING, null)
                        .add("priority", SerializableDataTypes.INT, 0),
                data ->
                        (type, player) ->
                            new ResourcePackPower(type, player, data.getString("url"), data.getInt("priority")))
                .allowCondition();
    }

    public ResourcePackPower(PowerType<?> type, LivingEntity entity, String url, int priority) {
        super(type, entity);
        if (url == null)
            Appli.LOGGER.warn("Power '" + this.getType().getIdentifier() + "' does not have a valid pack url field.");
        this.url = url;
        this.priority = priority;

    }


    public String getUrl() {
        return url;
    }

    public int getPriority() {
        return priority;
    }
}
