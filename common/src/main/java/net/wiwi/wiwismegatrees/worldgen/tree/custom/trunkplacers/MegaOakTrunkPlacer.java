package net.wiwi.wiwismegatrees.worldgen.tree.custom.trunkplacers;

import com.google.common.collect.Lists;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.wiwi.wiwismegatrees.worldgen.tree.custom.ModTrunkPlacers;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiConsumer;

public class MegaOakTrunkPlacer extends GiantTrunkPlacer {
    public static final MapCodec<MegaOakTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(
            (instance) -> trunkPlacerParts(instance).apply(instance, MegaOakTrunkPlacer::new));

    public MegaOakTrunkPlacer(int i, int j, int k) {
        super(i, j, k);
    }

    @Override
    protected @NotNull TrunkPlacerType<?> type() {
        return ModTrunkPlacers.MEGA_OAK_TRUNK_PLACER.get();
    }

    @Override
    public @NotNull List<FoliagePlacer.FoliageAttachment> placeTrunk(@NotNull LevelSimulatedReader levelSimulatedReader, @NotNull BiConsumer<BlockPos, BlockState> biConsumer, @NotNull RandomSource randomSource, int height, @NotNull BlockPos blockPos, @NotNull TreeConfiguration treeConfiguration) {
        List<FoliagePlacer.FoliageAttachment> list = Lists.newArrayList();
        list.addAll(super.placeTrunk(levelSimulatedReader, biConsumer, randomSource, height, blockPos, treeConfiguration));

        for (int b = 0; b < 2; b++) {
            int j = Mth.nextInt(randomSource, (int)(height * 0.6F), height - 2);
            float f = randomSource.nextFloat() * Mth.TWO_PI;
            int k = 0;
            int l = 0;

            int branchLength = 4 + randomSource.nextInt(2);

            for(int m = 0; m < branchLength; ++m) {
                k = (int)(1.5F + Mth.cos(f) * (float)m);
                l = (int)(1.5F + Mth.sin(f) * (float)m);
                BlockPos pos = blockPos.offset(k, j - 2 + m / 2, l);
                this.placeLog(levelSimulatedReader, biConsumer, randomSource, pos, treeConfiguration, (blockState) -> blockState.trySetValue(RotatedPillarBlock.AXIS, this.getLogAxis(blockPos, pos)));
            }

            list.add(new FoliagePlacer.FoliageAttachment(blockPos.offset(k, j+2  , l), 2 + randomSource.nextInt(2), false));
        }
        return list;
    }

    private Direction.Axis getLogAxis(BlockPos blockPos, BlockPos blockPos2) {
        Direction.Axis axis = Direction.Axis.Y;
        int i = Math.abs(blockPos2.getX() - blockPos.getX());
        int j = Math.abs(blockPos2.getZ() - blockPos.getZ());
        int k = Math.max(i, j);
        if (k > 0) {
            if (i == k) {
                axis = Direction.Axis.X;
            } else {
                axis = Direction.Axis.Z;
            }
        }

        return axis;
    }
}
