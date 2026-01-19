package net.wiwi.wiwismegatrees;

import com.mojang.serialization.MapCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.Services;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.ServiceLoader;
import java.util.function.Supplier;

public interface PlatformHandler {
    PlatformHandler INSTANCE = load(PlatformHandler.class);

    <T extends TrunkPlacer> Supplier<TrunkPlacerType<T>> registerTrunkPlacerType(
            String id,
            MapCodec<T> codec
    );

    <T extends FoliagePlacer> Supplier<FoliagePlacerType<T>> registerFoliagePlacerType(
            String id,
            MapCodec<T> codec
    );

    enum Platform {
        FABRIC,
        NEOFORGE
    }
    Platform getPlatform();

    private static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        return loadedService;
    }
}
