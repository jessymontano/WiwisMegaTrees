package net.wiwi.wiwismegatrees;

import com.mojang.logging.LogUtils;
import net.wiwi.wiwismegatrees.worldgen.tree.custom.ModFoliagePlacers;
import net.wiwi.wiwismegatrees.worldgen.tree.custom.ModTrunkPlacers;
import org.slf4j.Logger;

public final class WiwisMegaTrees {
    public static final String MOD_ID = "wiwismegatrees";

    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        ModTrunkPlacers.init();
        ModFoliagePlacers.init();
    }
}
