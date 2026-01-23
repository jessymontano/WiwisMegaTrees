package net.wiwi.wiwismegatrees.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

import net.wiwi.wiwismegatrees.WiwisMegaTrees;
import net.wiwi.wiwismegatrees.worldgen.tree.custom.ModFoliagePlacers;
import net.wiwi.wiwismegatrees.worldgen.tree.custom.ModTrunkPlacers;

@Mod(WiwisMegaTrees.MOD_ID)
public final class WiwisMegaTreesNeoForge {
    public WiwisMegaTreesNeoForge(IEventBus modBus) {
        NeoForgePlatformHandler.register(modBus);
        ModTrunkPlacers.init();
        ModFoliagePlacers.init();

        // Run our common setup.
        WiwisMegaTrees.init();
    }
}
