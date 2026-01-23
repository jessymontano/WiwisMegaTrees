package net.wiwi.wiwismegatrees.worldgen.tree.custom.foliageplacers;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.wiwi.wiwismegatrees.worldgen.tree.custom.ModFoliagePlacers;

public class MegaAcaciaFoliagePlacer extends FoliagePlacer {
    public static final MapCodec<MegaAcaciaFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> foliagePlacerParts(instance).apply(instance, MegaAcaciaFoliagePlacer::new));

    public MegaAcaciaFoliagePlacer(IntProvider intProvider, IntProvider intProvider2) {
        super(intProvider, intProvider2);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return ModFoliagePlacers.MEGA_ACACIA_FOLIAGE_PLACER.get();
    }

    @Override
    protected void createFoliage(LevelSimulatedReader levelSimulatedReader, FoliageSetter foliageSetter, RandomSource randomSource, TreeConfiguration treeConfiguration, int i, FoliageAttachment foliageAttachment, int j, int k, int l) {
        boolean bl = foliageAttachment.doubleTrunk();
        BlockPos blockPos = foliageAttachment.pos().above(l);
        this.placeLeavesRow(levelSimulatedReader, foliageSetter, randomSource, treeConfiguration, blockPos, k , -1 - j, bl);
        this.placeLeavesRow(levelSimulatedReader, foliageSetter, randomSource, treeConfiguration, blockPos, k - 1, -j, bl);
        this.placeLeavesRow(levelSimulatedReader, foliageSetter, randomSource, treeConfiguration, blockPos, k  - 1, 0, bl);
    }

    @Override
    public int foliageHeight(RandomSource randomSource, int i, TreeConfiguration treeConfiguration) {
        return 0;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource randomSource, int i, int j, int k, int l, boolean bl) {
        if (j == 0) {
            return (i > 1 || k > 1) && i != 0 && k != 0;
        } else {
            return i == l && k == l && l > 0;
        }
    }
}
