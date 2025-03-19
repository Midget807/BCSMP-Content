package com.bcsmp.bcsmp_content.main.domain_expansion.world.gen.biome;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;

public class DEModBiomes {
    public static final RegistryKey<Biome> DARK_PLAINS = registerBiomeKey("dark_plains");
    public static final RegistryKey<Biome> BARRENS = registerBiomeKey("barrens");
    public static final RegistryKey<Biome> TUNDRA = registerBiomeKey("tundra");
    public static final RegistryKey<Biome> CRYSTAL_CAVE = registerBiomeKey("crystal_cave"); //todo actually make complicated world gen you pussy

    private static RegistryKey<Biome> registerBiomeKey(String name) {
        return RegistryKey.of(RegistryKeys.BIOME, BCSMPContentMain.domainExpansionId(name));
    }
    public static void bootstrap(Registerable<Biome> context) {
        context.register(DARK_PLAINS, domainBiome(context));
        context.register(BARRENS, domainBiome(context));
        context.register(TUNDRA, domainBiome(context));
    }
    public static void globalOverworldGeneration(GenerationSettings.LookupBackedBuilder builder) {
        DefaultBiomeFeatures.addLandCarvers(builder);
        DefaultBiomeFeatures.addMossyRocks(builder);
    }

    public static Biome domainBiome(Registerable<Biome> biomeRegisterable) {

        SpawnSettings.Builder spawnBuilder = new SpawnSettings.Builder();

        GenerationSettings.LookupBackedBuilder biomeBuilder =
                new GenerationSettings.LookupBackedBuilder(
                        biomeRegisterable.getRegistryLookup(RegistryKeys.PLACED_FEATURE),
                        biomeRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER)
                );

        globalOverworldGeneration(biomeBuilder);

        //DefaultBiomeFeatures.addMossyRocks(biomeBuilder);

        return new Biome.Builder()
                .precipitation(false)
                .downfall(0.0f)
                .temperature(0.7f)
                .generationSettings(biomeBuilder.build())
                .spawnSettings(spawnBuilder.build())
                .effects((new BiomeEffects.Builder())
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .fogColor(10518688)
                        .skyColor(0)
                        .moodSound(BiomeMoodSound.CAVE)
                        .build()
                )
                .build();
    }

}
