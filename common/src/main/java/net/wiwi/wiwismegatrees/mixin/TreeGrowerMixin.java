package net.wiwi.wiwismegatrees.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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
            method = "growTree",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/grower/TreeGrower;getConfiguredMegaFeature(Lnet/minecraft/util/RandomSource;)Lnet/minecraft/resources/ResourceKey;"
            ),
            cancellable = true
    )
    private void wiwismegatrees$growTree(
            ServerLevel level,
            ChunkGenerator generator,
            BlockPos pos,
            BlockState state,
            RandomSource random,
            CallbackInfoReturnable<Boolean> cir
    ) {
        if (!this.name.equals("oak")) return;

        WiwisMegaTrees.LOGGER.debug("Mixin executing for Oak at pos: {}", pos);
        for (int dx = 0; dx >= -1; dx --) {
            for (int dz = 0; dz >= -1; dz--) {
                if (isTwoByTwo(state, level, pos, dx, dz)) {
                    WiwisMegaTrees.LOGGER.debug("2 x 2 sapling pattern detected for Oak");


                    var registry = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);

                    var holderOpt = registry.getHolder(ModConfiguredFeatures.OAK_MEGA_TREE_KEY);

                    if (holderOpt.isEmpty()) {
                       // restoreSaplings(level, pos, state, dx, dz);
                        //cir.setReturnValue(false);
                        WiwisMegaTrees.LOGGER.error("mega_oak configured feature not found");
                        return;
                    }

                    clearSaplings(level, pos, dx, dz);

                    ConfiguredFeature<?, ?> feature = holderOpt.get().value();

                    boolean success = feature.place(level, generator, random, pos.offset(dx, 0, dz));

                    if (!success) {
                        WiwisMegaTrees.LOGGER.error("Failed to generate mega oak tree");
                        restoreSaplings(level, pos, state, dx, dz);
                    } else {
                        WiwisMegaTrees.LOGGER.info("Mega oak generated successfully");
                    }

                    cir.setReturnValue(success);
                    return;
                }
            }
        }
        WiwisMegaTrees.LOGGER.debug("2x2 sapling pattern not detected for Oak");
    }

    @Unique
    private static boolean isTwoByTwo(BlockState state, BlockGetter level, BlockPos pos, int dx, int dz) {
        Block block = state.getBlock();
        return level.getBlockState(pos.offset(dx, 0, dz)).is(block)
                && level.getBlockState(pos.offset(dx + 1, 0, dz)).is(block)
                && level.getBlockState(pos.offset(dx, 0, dz + 1)).is(block)
                && level.getBlockState(pos.offset(dx + 1, 0, dz + 1)).is(block);
    }

    @Unique
    private static void clearSaplings(ServerLevel level, BlockPos pos, int dx, int dz) {
        BlockState air = Blocks.AIR.defaultBlockState();
        level.setBlock(pos.offset(dx, 0, dz), air, 4);
        level.setBlock(pos.offset(dx + 1, 0, dz), air, 4);
        level.setBlock(pos.offset(dx, 0, dz + 1), air, 4);
        level.setBlock(pos.offset(dx + 1, 0, dz + 1), air, 4);
    }

    @Unique
    private static void restoreSaplings(ServerLevel level, BlockPos pos, BlockState state, int dx, int dz) {
        level.setBlock(pos.offset(dx, 0, dz), state, 4);
        level.setBlock(pos.offset(dx + 1, 0, dz), state, 4);
        level.setBlock(pos.offset(dx, 0, dz + 1), state, 4);
        level.setBlock(pos.offset(dx + 1, 0, dz + 1), state, 4);
    }
}
