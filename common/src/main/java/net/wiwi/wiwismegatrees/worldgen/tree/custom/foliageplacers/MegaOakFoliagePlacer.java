package net.wiwi.wiwismegatrees.worldgen.tree.custom.foliageplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class MegaOakFoliagePlacer extends FoliagePlacer {
    public static final MapCodec<MegaOakFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(
            megaOakFoliagePlacerInstance
            -> megaOakFoliagePlacerInstance.group(
                    IntProvider.codec(0,16).fieldOf("radius").forGetter(fp -> fp.radius),
                    IntProvider.codec(0, 16).fieldOf("offset").forGetter(fp -> fp.offset),
                    Codec.intRange(0, 24).fieldOf("height").forGetter(fp -> fp.height)
            ).apply(megaOakFoliagePlacerInstance, MegaOakFoliagePlacer::new));

    private final int height;

    public MegaOakFoliagePlacer(IntProvider radius, IntProvider offset, int height) {
        super(radius, offset);
        this.height = height;
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return null;
    }

    @Override
    protected void createFoliage(LevelSimulatedReader levelSimulatedReader, FoliageSetter foliageSetter, RandomSource randomSource, TreeConfiguration treeConfiguration, int i, FoliageAttachment foliageAttachment, int foliageHeight, int foliageRadius, int offset) {
        BlockPos center = foliageAttachment.pos();

        for (int y = 0; y < foliageHeight; y++) {
            int currentRadius = foliageRadius * (foliageHeight - y) / foliageHeight;
            int yPos = center.getY() + offset + y;

            for (int x = -currentRadius; x <= currentRadius; x++) {
                for (int z = -currentRadius; z <= currentRadius; z++) {
                    double distance = Math.sqrt(x*x + z*z);
                    if (distance <= currentRadius + 0.5) {
                        if (randomSource.nextFloat() > 0.1f) {
                            BlockPos leafPos = new BlockPos(
                                    center.getX() + x,
                                    yPos,
                                    center.getZ() + z
                            );
                            tryPlaceLeaf(levelSimulatedReader, foliageSetter, randomSource, treeConfiguration, leafPos);
                        }
                    }
                }
            }
        }
    }

    @Override
    public int foliageHeight(RandomSource randomSource, int i, TreeConfiguration treeConfiguration) {
        return this.height;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource randomSource, int dx, int y, int dz, int radius, boolean giantTrunk) {
       if (giantTrunk) {
           return (dx >= -1 && dx <= 2 && dz >= -1 && dz <= 2);
       }

       if (dx == 0 && dz == 0) {
           return randomSource.nextFloat() < 0.3f;
       }

       return false;
    }
}
