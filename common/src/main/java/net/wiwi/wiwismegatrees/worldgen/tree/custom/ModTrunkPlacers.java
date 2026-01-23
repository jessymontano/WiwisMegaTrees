package net.wiwi.wiwismegatrees.worldgen.tree.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.wiwi.wiwismegatrees.PlatformHandler;
import net.wiwi.wiwismegatrees.WiwisMegaTrees;
import net.wiwi.wiwismegatrees.worldgen.tree.custom.trunkplacers.MegaAcaciaTrunkPlacer;
import net.wiwi.wiwismegatrees.worldgen.tree.custom.trunkplacers.MegaCherryTrunkPlacer;
import net.wiwi.wiwismegatrees.worldgen.tree.custom.trunkplacers.MegaMangroveTrunkPlacer;
import net.wiwi.wiwismegatrees.worldgen.tree.custom.trunkplacers.MegaOakTrunkPlacer;

import java.util.function.Supplier;

public class ModTrunkPlacers {
    public static final Supplier<TrunkPlacerType<MegaOakTrunkPlacer>> MEGA_OAK_TRUNK_PLACER =
           register("mega_oak_trunk_placer", MegaOakTrunkPlacer.CODEC);
    public static final Supplier<TrunkPlacerType<MegaAcaciaTrunkPlacer>> MEGA_ACACIA_TRUNK_PLACER =
            register("mega_acacia_trunk_placer", MegaAcaciaTrunkPlacer.CODEC);
    public static final Supplier<TrunkPlacerType<MegaCherryTrunkPlacer>> MEGA_CHERRY_TRUNK_PLACER =
            register("mega_cherry_trunk_placer", MegaCherryTrunkPlacer.CODEC);
    public static final Supplier<TrunkPlacerType<MegaMangroveTrunkPlacer>> MEGA_MANGROVE_TRUNK_PLACER =
            register("mega_mangrove_trunk_placer", MegaMangroveTrunkPlacer.CODEC);

    private static <T extends TrunkPlacer> Supplier<TrunkPlacerType<T>> register(String key, MapCodec<T> codec) {
        return PlatformHandler.INSTANCE.registerTrunkPlacerType(key, codec);
    }

    public static void init() {
        WiwisMegaTrees.LOGGER.info("Registering mod Trunk Placer Types...");
    }
}

