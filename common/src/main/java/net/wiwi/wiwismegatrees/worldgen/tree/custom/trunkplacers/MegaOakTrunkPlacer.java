package net.wiwi.wiwismegatrees.worldgen.tree.custom.trunkplacers;

import com.google.common.collect.Lists;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.wiwi.wiwismegatrees.worldgen.tree.custom.ModTrunkPlacers;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

public class MegaOakTrunkPlacer extends GiantTrunkPlacer {
    public static final MapCodec<MegaOakTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> trunkPlacerParts(instance).apply(instance, MegaOakTrunkPlacer::new));

    public MegaOakTrunkPlacer(int i, int j, int k) {
        super(i, j, k);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacers.MEGA_OAK_TRUNK_PLACER.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader levelSimulatedReader, BiConsumer<BlockPos, BlockState> biConsumer, RandomSource randomSource, int height, BlockPos blockPos, TreeConfiguration treeConfiguration) {
        List<FoliagePlacer.FoliageAttachment> list = Lists.newArrayList();
        list.addAll(super.placeTrunk(levelSimulatedReader, biConsumer, randomSource, height, blockPos, treeConfiguration));

        for(int j = height - 2 - randomSource.nextInt(2); j > height / 2; j -= randomSource.nextInt(2)) {
            float f = randomSource.nextFloat() * Mth.TWO_PI;
            int k = 0;
            int l = 0;

            for(int m = 0; m < 5; ++m) {
                k = (int)(1.5F + Mth.cos(f) * (float)m);
                l = (int)(1.5F + Mth.sin(f) * (float)m);
                BlockPos pos = blockPos.offset(k, j - 3 + m / 2, l);
                this.placeLog(levelSimulatedReader, biConsumer, randomSource, pos, treeConfiguration);
            }

            list.add(new FoliagePlacer.FoliageAttachment(blockPos.offset(k, j  , l), 2, false));
        }
        return list;
    }
}
