package net.wiwi.wiwismegatrees.worldgen.tree.custom.trunkplacers;

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
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.wiwi.wiwismegatrees.worldgen.tree.custom.ModTrunkPlacers;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiConsumer;

public class MegaMangroveTrunkPlacer extends TrunkPlacer {
    public static final MapCodec<MegaMangroveTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(
            (instance) -> trunkPlacerParts(instance).apply(instance, MegaMangroveTrunkPlacer::new)
    );

    public MegaMangroveTrunkPlacer(int i, int j, int k) {
        super(i, j, k);
    }

    @Override
    protected @NotNull TrunkPlacerType<?> type() {
        return ModTrunkPlacers.MEGA_MANGROVE_TRUNK_PLACER.get();
    }

    @Override
    public @NotNull List<FoliagePlacer.FoliageAttachment> placeTrunk(@NotNull LevelSimulatedReader levelSimulatedReader, @NotNull BiConsumer<BlockPos, BlockState> biConsumer, @NotNull RandomSource randomSource, int height, @NotNull BlockPos blockPos, @NotNull TreeConfiguration treeConfiguration) {
        List<FoliagePlacer.FoliageAttachment> list = Lists.newArrayList();
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        for(int j = 0; j < height; ++j) {
            this.placeLogIfFreeWithOffset(levelSimulatedReader, biConsumer, randomSource, mutableBlockPos, treeConfiguration, blockPos, 0, j, 0);
            if (j < height - 1) {
                this.placeLogIfFreeWithOffset(levelSimulatedReader, biConsumer, randomSource, mutableBlockPos, treeConfiguration, blockPos, 1, j, 0);
                this.placeLogIfFreeWithOffset(levelSimulatedReader, biConsumer, randomSource, mutableBlockPos, treeConfiguration, blockPos, 1, j, 1);
                this.placeLogIfFreeWithOffset(levelSimulatedReader, biConsumer, randomSource, mutableBlockPos, treeConfiguration, blockPos, 0, j, 1);
            }
        }

        list.add(new FoliagePlacer.FoliageAttachment(blockPos.above(height - 1), 2, false));

        int branches = 3 + randomSource.nextInt(2);
        int branchStart = Mth.nextInt(randomSource, height / 2, height - 3);

        for (int b = 0; b < branches; b++) {
            float f = (float)(b * (Math.PI * 2 / branches)) + randomSource.nextFloat() * 0.4F;

            int branchLength = 6 + randomSource.nextInt(3);

            for(int m = 0; m < branchLength; ++m) {
                int k = (int)(Mth.cos(f) * (float)m);
                int l = (int)(Mth.sin(f) * (float)m);
                int y = branchStart + m / 2;

                BlockPos pos = blockPos.offset(k, y, l);

                this.placeLog(levelSimulatedReader, biConsumer, randomSource, pos, treeConfiguration, (blockState) -> blockState.trySetValue(RotatedPillarBlock.AXIS, this.getLogAxis(blockPos, pos)));

                if (m == branchLength / 2 || m == branchLength - 1) {
                    list.add(new FoliagePlacer.FoliageAttachment(
                            pos.above(),
                            2 + randomSource.nextInt(2),
                            false
                    ));
                }
            }
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

    private void placeLogIfFreeWithOffset(LevelSimulatedReader levelSimulatedReader, BiConsumer<BlockPos, BlockState> biConsumer, RandomSource randomSource, BlockPos.MutableBlockPos mutableBlockPos, TreeConfiguration treeConfiguration, BlockPos blockPos, int i, int j, int k) {
        mutableBlockPos.setWithOffset(blockPos, i, j, k);
        this.placeLogIfFree(levelSimulatedReader, biConsumer, randomSource, mutableBlockPos, treeConfiguration);
    }
}
