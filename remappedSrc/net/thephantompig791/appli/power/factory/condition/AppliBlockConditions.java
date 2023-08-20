package net.thephantompig791.appli.power.factory.condition;

import io.github.apace100.apoli.Apoli;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.apoli.util.Comparison;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.registry.Registry;
import net.minecraft.world.World;
import net.thephantompig791.appli.Appli;

public class AppliBlockConditions {
    public static void register() {
        register(new ConditionFactory<>(Appli.identifier("command"), new SerializableData()
            .add("command", SerializableDataTypes.STRING)
            .add("comparison", ApoliDataTypes.COMPARISON)
            .add("compare_to", SerializableDataTypes.INT),
            (data, block) -> {
                MinecraftServer server = ((World)block.getWorld()).getServer();
                String blockName = block.getBlockState().getBlock().getTranslationKey();
                if(server != null) {
                    ServerCommandSource source = new ServerCommandSource(
                            Apoli.config.executeCommand.showOutput ? server : CommandOutput.DUMMY,
                            new Vec3d(block.getBlockPos().getX() + 0.5, block.getBlockPos().getY() + 0.5, block.getBlockPos().getZ() + 0.5),
                            new Vec2f(0, 0),
                            (ServerWorld) block.getWorld(),
                            Apoli.config.executeCommand.permissionLevel,
                            blockName,
                            Text.translatable(blockName),
                            server,
                            null);
                    int output = server.getCommandManager().executeWithPrefix(source, data.getString("command"));
                    return ((Comparison)data.get("comparison")).compare(output, data.getInt("compare_to"));
                }
                return false;
            }));
    }

    private static void register(ConditionFactory<CachedBlockPosition> conditionFactory) {
        Registry.register(ApoliRegistries.BLOCK_CONDITION, conditionFactory.getSerializerId(), conditionFactory);
    }
}
