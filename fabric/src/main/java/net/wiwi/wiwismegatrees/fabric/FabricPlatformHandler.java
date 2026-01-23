package net.wiwi.wiwismegatrees.fabric;

import com.google.auto.service.AutoService;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.wiwi.wiwismegatrees.PlatformHandler;

import java.util.function.Supplier;

@AutoService(PlatformHandler.class)
public class FabricPlatformHandler implements PlatformHandler{

    @Override
    public <T extends TrunkPlacer> Supplier<TrunkPlacerType<T>> registerTrunkPlacerType(String id, MapCodec<T> codec) {
        TrunkPlacerType<T> type = new TrunkPlacerType<>(codec);
        Registry.register(BuiltInRegistries.TRUNK_PLACER_TYPE, id, type);
        return () -> type;
    }

    @Override
    public <T extends FoliagePlacer> Supplier<FoliagePlacerType<T>> registerFoliagePlacerType(String id, MapCodec<T> codec) {
        FoliagePlacerType<T> type = new FoliagePlacerType<>(codec);
        Registry.register(BuiltInRegistries.FOLIAGE_PLACER_TYPE, id, type);
        return () -> type;
    }

    @Override
    public Platform getPlatform() {
        return Platform.FABRIC;
    }
}
