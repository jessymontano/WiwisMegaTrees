package net.wiwi.wiwismegatrees.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaJungleFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaPineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.MegaJungleTrunkPlacer;
import net.wiwi.wiwismegatrees.WiwisMegaTrees;
import net.wiwi.wiwismegatrees.worldgen.tree.custom.trunkplacers.MegaOakTrunkPlacer;

import java.sql.Blob;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> OAK_MEGA_TREE_KEY = registerKey("mega_oak");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BIRCH_MEGA_TREE_KEY = registerKey("mega_birch");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        // mega oak tree feature
        register(context, OAK_MEGA_TREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.OAK_LOG),
                new MegaOakTrunkPlacer(9, 2, 4),
                BlockStateProvider.simple(Blocks.OAK_LEAVES),
                new FancyFoliagePlacer(
                        ConstantInt.of(3),
                        ConstantInt.of(0),
                        3
                ),
                new TwoLayersFeatureSize(1, 1, 2)
        ).dirt(BlockStateProvider.simple(Blocks.DIRT)).ignoreVines().build());

        // mega birch tree feature
        register(context, BIRCH_MEGA_TREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.BIRCH_LOG),
                new GiantTrunkPlacer(13, 2, 7),
                BlockStateProvider.simple(Blocks.BIRCH_LEAVES),
                new BlobFoliagePlacer(
                        ConstantInt.of(2), ConstantInt.of(0), 4
                ),
                new TwoLayersFeatureSize(1, 1, 2)
        ).dirt(BlockStateProvider.simple(Blocks.DIRT)).ignoreVines().build());
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name){
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(WiwisMegaTrees.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
