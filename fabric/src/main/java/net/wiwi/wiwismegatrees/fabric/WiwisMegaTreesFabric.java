package net.wiwi.wiwismegatrees.fabric;

import net.fabricmc.api.ModInitializer;

import net.wiwi.wiwismegatrees.WiwisMegaTrees;
import net.wiwi.wiwismegatrees.worldgen.tree.custom.ModTrunkPlacers;

public final class WiwisMegaTreesFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        ModTrunkPlacers.init();

        // Run our common setup.
        WiwisMegaTrees.init();
    }
}
