package net.wiwi.wiwismegatrees.forge;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;

import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.wiwi.wiwismegatrees.WiwisMegaTrees;

@Mod(WiwisMegaTrees.MOD_ID)
public final class WiwisMegaTreesForge {
    public WiwisMegaTreesForge(FMLJavaModLoadingContext context) {
        IEventBus modBus = context.getModEventBus();

        ForgePlatformHandler.register(modBus);
        // Run our common setup.
        WiwisMegaTrees.init();
    }
}
