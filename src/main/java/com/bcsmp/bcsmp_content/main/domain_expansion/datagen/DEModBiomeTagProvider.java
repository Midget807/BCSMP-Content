package com.bcsmp.bcsmp_content.main.domain_expansion.datagen;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import com.bcsmp.bcsmp_content.main.domain_expansion.world.gen.biome.DEModBiomes;
import net.minecraft.data.DataOutput;
import net.minecraft.data.server.tag.vanilla.VanillaBiomeTagProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;

import java.util.concurrent.CompletableFuture;

public class DEModBiomeTagProvider extends VanillaBiomeTagProvider {
    public static final TagKey<Biome> DARK_PLAINS = TagKey.of(RegistryKeys.BIOME, BCSMPContentMain.domainExpansionId("dark_plains"));
    public DEModBiomeTagProvider(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookupFuture) {
        super(output, registryLookupFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup lookup) {
        super.configure(lookup);
        this.getOrCreateTagBuilder(DARK_PLAINS)
                .add(DEModBiomes.DARK_PLAINS);
    }
}
