package net.wiwi.wiwismegatrees.fabric;


import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.wiwi.wiwismegatrees.fabric.datagen.FabricRegistryDataGenerator;
import net.wiwi.wiwismegatrees.worldgen.ModConfiguredFeatures;

public class WiwisMegaTreesDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(FabricRegistryDataGenerator::new);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
    }
}
