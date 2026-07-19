package net.wiwi.wiwismegatrees.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MangrovePropaguleBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.wiwi.wiwismegatrees.WiwisMegaTrees;
import net.wiwi.wiwismegatrees.worldgen.ModConfiguredFeatures;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(TreeGrower.class)
public abstract class TreeGrowerMixin {
    @Shadow @Final private String name;

    @Inject(
            method = "growTree(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/chunk/ChunkGenerator;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/util/RandomSource;)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/grower/TreeGrower;getConfiguredMegaFeature(Lnet/minecraft/util/RandomSource;)Lnet/minecraft/resources/ResourceKey;"
            ),
            cancellable = true,
            remap = false
    )
    private void wiwismegatrees$growTree(
            ServerLevel level,
            ChunkGenerator generator,
            BlockPos pos,
            BlockState state,
            RandomSource random,
            CallbackInfoReturnable<Boolean> cir
    ) {
        var registry = level.registryAccess().lookupOrThrow(Registries.CONFIGURED_FEATURE);

        switch (this.name) {
            case "oak" -> {
                var holderOpt = registry.get(ModConfiguredFeatures.OAK_MEGA_TREE_KEY);

                if (holderOpt.isEmpty()) {
                    WiwisMegaTrees.LOGGER.error("mega_oak configured feature not found");
                    return;
                }
                wiwismegatrees$growMegaTree(state, level, pos, generator, random, holderOpt, cir);
            }
            case "birch" -> {
                var holderOpt = registry.get(ModConfiguredFeatures.BIRCH_MEGA_TREE_KEY);

                if (holderOpt.isEmpty()) {
                    WiwisMegaTrees.LOGGER.error("mega_birch configured feature not found");
                    return;
                }
                wiwismegatrees$growMegaTree(state, level, pos, generator, random, holderOpt, cir);
            }
            case "acacia" -> {
                var holderOpt = registry.get(ModConfiguredFeatures.ACACIA_MEGA_TREE_KEY);

                if (holderOpt.isEmpty()) {
                    WiwisMegaTrees.LOGGER.error("mega_acacia configured feature not found");
                    return;
                }
                wiwismegatrees$growMegaTree(state, level, pos, generator, random, holderOpt, cir);
            }
            case "cherry" -> {
                var holderOpt = registry.get(ModConfiguredFeatures.CHERRY_MEGA_TREE_KEY);

                if (holderOpt.isEmpty()) {
                    WiwisMegaTrees.LOGGER.error("mega_cherry configured feature not found");
                    return;
                }
                wiwismegatrees$growMegaTree(state, level, pos, generator, random, holderOpt, cir);
            }
            case "mangrove" -> {
                var holderOpt = registry.get(ModConfiguredFeatures.MANGROVE_MEGA_TREE_KEY);

                if (holderOpt.isEmpty()) {
                    WiwisMegaTrees.LOGGER.error("mega_mangrove configured feature not found");
                    return;
                }
                wiwismegatrees$growMegaTree(state, level, pos, generator, random, holderOpt, cir);
            }
        }
    }

    @Unique
    private static boolean wiwismegatrees$isTwoByTwo(BlockState state, BlockGetter level, BlockPos pos, int dx, int dz) {
        Block block = state.getBlock();
        return level.getBlockState(pos.offset(dx, 0, dz)).is(block)
                && level.getBlockState(pos.offset(dx + 1, 0, dz)).is(block)
                && level.getBlockState(pos.offset(dx, 0, dz + 1)).is(block)
                && level.getBlockState(pos.offset(dx + 1, 0, dz + 1)).is(block);
    }

    @Unique
    private static void wiwismegatrees$clearSaplings(ServerLevel level, BlockPos pos, int dx, int dz) {
        if (level.getBlockState(pos).getBlock() instanceof MangrovePropaguleBlock) {
            level.destroyBlock(pos.offset(dx, 0, dz), false);
            level.destroyBlock(pos.offset(dx + 1, 0, dz), false);
            level.destroyBlock(pos.offset(dx, 0, dz + 1), false);
            level.destroyBlock(pos.offset(dx + 1, 0, dz + 1), false);
        } else {
            BlockState air = Blocks.AIR.defaultBlockState();
            level.setBlock(pos.offset(dx, 0, dz), air, 4);
            level.setBlock(pos.offset(dx + 1, 0, dz), air, 4);
            level.setBlock(pos.offset(dx, 0, dz + 1), air, 4);
            level.setBlock(pos.offset(dx + 1, 0, dz + 1), air, 4);
        }
    }

    @Unique
    private static void wiwismegatrees$restoreSaplings(ServerLevel level, BlockPos pos, BlockState state, int dx, int dz) {
        level.setBlock(pos.offset(dx, 0, dz), state, 4);
        level.setBlock(pos.offset(dx + 1, 0, dz), state, 4);
        level.setBlock(pos.offset(dx, 0, dz + 1), state, 4);
        level.setBlock(pos.offset(dx + 1, 0, dz + 1), state, 4);
    }

    @Unique
    private static void wiwismegatrees$growMegaTree(BlockState state, ServerLevel level, BlockPos pos, ChunkGenerator generator, RandomSource random, Optional<Holder.Reference<ConfiguredFeature<?, ?>>> holderOpt, CallbackInfoReturnable<Boolean> cir) {
        for (int dx = 0; dx >= -1; dx--) {
            for (int dz = 0; dz >= -1; dz--) {
                if (wiwismegatrees$isTwoByTwo(state, level, pos, dx, dz)) {

                    wiwismegatrees$clearSaplings(level, pos, dx, dz);

                    ConfiguredFeature<?, ?> feature = holderOpt.get().value();

                    boolean success = feature.place(level, generator, random, pos.offset(dx, 0, dz));

                    if (!success) {
                        WiwisMegaTrees.LOGGER.error("Failed to generate mega tree");
                        wiwismegatrees$restoreSaplings(level, pos, state, dx, dz);
                    }

                    cir.setReturnValue(success);
                    return;
                }
            }
        }
    }
}
