package net.wiwi.wiwismegatrees.worldgen.tree.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.wiwi.wiwismegatrees.PlatformHandler;
import net.wiwi.wiwismegatrees.WiwisMegaTrees;
import net.wiwi.wiwismegatrees.worldgen.tree.custom.foliageplacers.MegaAcaciaFoliagePlacer;

import java.util.function.Supplier;

public class ModFoliagePlacers {
    public static final Supplier<FoliagePlacerType<MegaAcaciaFoliagePlacer>> MEGA_ACACIA_FOLIAGE_PLACER =
            register("mega_acacia_foliage_placer", MegaAcaciaFoliagePlacer.CODEC);

    private static <T extends FoliagePlacer> Supplier<FoliagePlacerType<T>> register(String key, MapCodec<T> codec) {
        return PlatformHandler.INSTANCE.registerFoliagePlacerType(key, codec);
    }

    public static void init() {
        WiwisMegaTrees.LOGGER.info("Registering mod Foliage Placer Types...");
    }
}
