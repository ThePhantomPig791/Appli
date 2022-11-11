package net.thephantompig791.appli.power.factory.action;

import io.github.apace100.apoli.Apoli;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.thephantompig791.appli.Appli;

public class AppliBiEntityActions {

    public static void register() {

        register(new ActionFactory<>(Appli.identifier("teleport"), new SerializableData()
                .add("swap", SerializableDataTypes.BOOLEAN, false),
                (data, entities) -> {
                    Entity actor = entities.getLeft(), target = entities.getRight();
                    if (data.getBoolean("swap")) {
                        Vec3d actorNewPos = target.getPos();
                        target.teleport(actor.getX(), actor.getY(), actor.getZ());
                        actor.teleport(actorNewPos.x, actorNewPos.y, actorNewPos.z);
                    }
                    else {
                        actor.setPosition(target.getPos());
                        actor.teleport(target.getX(), target.getY(), target.getZ());
                    }
                }));


        register(new ActionFactory<>(Appli.identifier("execute_command"), new SerializableData()
                .add("as", SerializableDataTypes.STRING)
                .add("at", SerializableDataTypes.STRING)
                .add("command", SerializableDataTypes.STRING),
                (data, entities) -> {
                    Entity actor = entities.getLeft(), target = entities.getRight();

                    Entity commandExecutor = data.getString("as").equalsIgnoreCase("actor") ? actor : target;
                    Entity commandPositionExecutor = data.getString("at").equalsIgnoreCase("actor") ? actor : target;

                    MinecraftServer server = commandExecutor.world.getServer();
                    if(server != null) {
                        boolean validOutput = !(commandExecutor instanceof ServerPlayerEntity) || ((ServerPlayerEntity)commandExecutor).networkHandler != null;
                        ServerCommandSource source = new ServerCommandSource(
                                Apoli.config.executeCommand.showOutput && validOutput ? commandExecutor : CommandOutput.DUMMY,
                                commandPositionExecutor.getPos(),
                                commandPositionExecutor.getRotationClient(),
                                commandExecutor.world instanceof ServerWorld ? (ServerWorld)commandExecutor.world : null,
                                Apoli.config.executeCommand.permissionLevel,
                                commandExecutor.getName().getString(),
                                commandExecutor.getDisplayName(),
                                commandExecutor.world.getServer(),
                                commandExecutor);
                        server.getCommandManager().executeWithPrefix(source, data.getString("command"));
                    }
                }));
    }

    private static void register(ActionFactory<Pair<Entity, Entity>> actionFactory) {
        Registry.register(ApoliRegistries.BIENTITY_ACTION, actionFactory.getSerializerId(), actionFactory);
    }
}
