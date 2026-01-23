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
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.wiwi.wiwismegatrees.worldgen.tree.custom.ModTrunkPlacers;
import org.apache.commons.compress.utils.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class MegaAcaciaTrunkPlacer extends GiantTrunkPlacer {
    public static MapCodec<MegaAcaciaTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(
            ( instance ) -> trunkPlacerParts(instance).apply(instance, MegaAcaciaTrunkPlacer::new));

    public MegaAcaciaTrunkPlacer(int i, int j, int k) {
        super(i, j, k);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacers.MEGA_ACACIA_TRUNK_PLACER.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader levelSimulatedReader, BiConsumer<BlockPos, BlockState> biConsumer, RandomSource randomSource, int height, BlockPos blockPos, TreeConfiguration treeConfiguration) {
        List<FoliagePlacer.FoliageAttachment> list = Lists.newArrayList();
        list.addAll(super.placeTrunk(levelSimulatedReader, biConsumer, randomSource, height, blockPos, treeConfiguration));

        int branches = 3 + randomSource.nextInt(2);
        int branchStart = Mth.nextInt(randomSource, height / 2, height - 3);
        int branchEnd = height - 1;

        for (int b = 0; b < branches; b++) {
            float f = (float)(b * (Math.PI * 2 / branches)) + randomSource.nextFloat() * 0.4F;
            int k = 0;
            int l = 0;

            int branchLength = 6 + randomSource.nextInt(3);

            for(int m = 0; m < branchLength; ++m) {
                k = (int)(Mth.cos(f) * (float)m);
                l = (int)(Mth.sin(f) * (float)m);

                branchEnd = branchStart + m / 2;

                BlockPos pos = blockPos.offset(k, branchStart + m / 2, l);
                this.placeLog(levelSimulatedReader, biConsumer, randomSource, pos, treeConfiguration, (blockState) -> (BlockState)blockState.trySetValue(RotatedPillarBlock.AXIS, this.getLogAxis(blockPos, pos)));
            }

            list.add(new FoliagePlacer.FoliageAttachment(blockPos.offset(k, branchEnd + 1, l), 2 + randomSource.nextInt(2), false));
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
