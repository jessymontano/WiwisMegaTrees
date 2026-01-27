package net.wiwi.wiwismegatrees;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.ServiceLoader;
import java.util.function.Supplier;

/**
 * allows placer registration in common without depending on a specific modloader
 * */
public interface PlatformHandler {
    PlatformHandler INSTANCE = load(PlatformHandler.class);

    <T extends TrunkPlacer> Supplier<TrunkPlacerType<T>> registerTrunkPlacerType(
            String id,
            Codec<T> codec
    );

    <T extends FoliagePlacer> Supplier<FoliagePlacerType<T>> registerFoliagePlacerType(
            String id,
            Codec<T> codec
    );

    enum Platform {
        FABRIC,
        FORGE
    }
    Platform getPlatform();

    private static <T> T load(Class<T> clazz) {
        return ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
    }
}
