package net.wiwi.wiwismegatrees.forge;

import com.google.auto.service.AutoService;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.wiwi.wiwismegatrees.PlatformHandler;
import net.wiwi.wiwismegatrees.WiwisMegaTrees;

import java.util.function.Supplier;

@AutoService(PlatformHandler.class)
public class ForgePlatformHandler implements PlatformHandler {
    private static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS =
            DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, WiwisMegaTrees.MOD_ID);

    private static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACERS =
            DeferredRegister.create(Registries.FOLIAGE_PLACER_TYPE, WiwisMegaTrees.MOD_ID);

    @Override
    public <T extends TrunkPlacer> Supplier<TrunkPlacerType<T>> registerTrunkPlacerType(String id, Codec<T> codec) {
        return TRUNK_PLACERS.register(id, () -> new TrunkPlacerType<>(codec));
    }

    @Override
    public <T extends FoliagePlacer> Supplier<FoliagePlacerType<T>> registerFoliagePlacerType(String id, Codec<T> codec) {
        return FOLIAGE_PLACERS.register(id, () -> new FoliagePlacerType<>(codec));
    }

    @Override
    public Platform getPlatform() {
        return Platform.FORGE;
    }

    public static void register(IEventBus eventBus) {
        TRUNK_PLACERS.register(eventBus);
        FOLIAGE_PLACERS.register(eventBus);
    }
}
