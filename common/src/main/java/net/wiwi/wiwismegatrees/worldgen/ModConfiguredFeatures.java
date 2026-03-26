package net.wiwi.wiwismegatrees.worldgen;

import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MangrovePropaguleBlock;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.*;
import net.minecraft.world.level.levelgen.feature.rootplacers.MangroveRootPlacement;
import net.minecraft.world.level.levelgen.feature.rootplacers.MangroveRootPlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.AttachedToLeavesDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.wiwi.wiwismegatrees.WiwisMegaTrees;
import net.wiwi.wiwismegatrees.worldgen.tree.custom.foliageplacers.MegaAcaciaFoliagePlacer;
import net.wiwi.wiwismegatrees.worldgen.tree.custom.trunkplacers.MegaAcaciaTrunkPlacer;
import net.wiwi.wiwismegatrees.worldgen.tree.custom.trunkplacers.MegaCherryTrunkPlacer;
import net.wiwi.wiwismegatrees.worldgen.tree.custom.trunkplacers.MegaMangroveTrunkPlacer;
import net.wiwi.wiwismegatrees.worldgen.tree.custom.trunkplacers.MegaOakTrunkPlacer;

import java.util.List;
import java.util.Optional;


public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> OAK_MEGA_TREE_KEY = registerKey("mega_oak");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BIRCH_MEGA_TREE_KEY = registerKey("mega_birch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ACACIA_MEGA_TREE_KEY = registerKey("mega_acacia");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CHERRY_MEGA_TREE_KEY = registerKey("mega_cherry");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MANGROVE_MEGA_TREE_KEY = registerKey("mega_mangrove");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);
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

        // mega acacia tree feature
        register(context, ACACIA_MEGA_TREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.ACACIA_LOG),
                new MegaAcaciaTrunkPlacer(6, 2, 4),
                BlockStateProvider.simple(Blocks.ACACIA_LEAVES),
                new MegaAcaciaFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                new TwoLayersFeatureSize(1, 1, 2)
        ).dirt(BlockStateProvider.simple(Blocks.DIRT)).ignoreVines().build());

        // mega cherry tree feature
        register(context, CHERRY_MEGA_TREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.CHERRY_LOG),
                new MegaCherryTrunkPlacer(8, 3, 6),
                BlockStateProvider.simple(Blocks.CHERRY_LEAVES),
                new CherryFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), ConstantInt.of(4), 0.25F, 0.5F, 0.16666667F, 0.33333334F),
                new TwoLayersFeatureSize(1, 1, 2)
        ).dirt(BlockStateProvider.simple(Blocks.DIRT)).ignoreVines().build());

        // mega mangrove tree feature
        register(context, MANGROVE_MEGA_TREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.MANGROVE_LOG),
                new MegaMangroveTrunkPlacer(8, 3, 5),
                BlockStateProvider.simple(Blocks.MANGROVE_LEAVES),
                new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(2), 70),
                Optional.of(new MangroveRootPlacer(UniformInt.of(3, 6),
                        BlockStateProvider.simple(Blocks.MANGROVE_ROOTS),
                        Optional.empty(),
                        new MangroveRootPlacement(
                                blocks.getOrThrow(BlockTags.MANGROVE_ROOTS_CAN_GROW_THROUGH),
                                HolderSet.direct(BuiltInRegistries.BLOCK::wrapAsHolder, Blocks.MUD, Blocks.MUDDY_MANGROVE_ROOTS, Blocks.DIRT),
                                BlockStateProvider.simple(Blocks.MUDDY_MANGROVE_ROOTS), 12, 25, 0.35F))),
                new TwoLayersFeatureSize(2, 0, 2)
        ).decorators(List.of(new LeaveVineDecorator(0.125F),
                new AttachedToLeavesDecorator(0.14F, 1, 0,
                        new RandomizedIntStateProvider(BlockStateProvider.simple(Blocks.MANGROVE_PROPAGULE.defaultBlockState().setValue(MangrovePropaguleBlock.HANGING, true)),
                                MangrovePropaguleBlock.AGE, UniformInt.of(0, 4)), 2, List.of(Direction.DOWN)))).ignoreVines().build());
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name){
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Identifier.fromNamespaceAndPath(WiwisMegaTrees.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<@org.jetbrains.annotations.NotNull FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                                                             ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
