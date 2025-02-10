package com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.dimension;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;

import java.util.OptionalLong;

public class DEModDimensions {
    public static final RegistryKey<DimensionOptions> DOMAIN_1_KEY = registerDimensionKey("domain_1");
    public static final RegistryKey<World> DOMAIN_1_LEVEL_KEY = registerDimensionLevelKey("domain_1");
    public static final RegistryKey<DimensionType> GLOBAL_DOMAIN_DAY_TYPE = registerDimensionType("global_domain_day_type");
    public static final RegistryKey<DimensionType> GLOBAL_DOMAIN_NIGHT_TYPE = registerDimensionType("global_domain_night_type");
    public static void bootstrapType(Registerable<DimensionType> context) {
        context.register(GLOBAL_DOMAIN_DAY_TYPE, new DimensionType(
           OptionalLong.of(6000),
                false,
                false,
                false,
                true,
                1.0f,
                true,
                true,
                0,
                320,
                320,
                BlockTags.INFINIBURN_OVERWORLD,
                DimensionTypes.THE_END_ID,
                1.0f,
                new DimensionType.MonsterSettings(true, false, UniformIntProvider.create(0, 0), 0)
        ));
        context.register(GLOBAL_DOMAIN_NIGHT_TYPE, new DimensionType(
           OptionalLong.of(18000),
                false,
                false,
                false,
                true,
                1.0f,
                true,
                true,
                0,
                320,
                320,
                BlockTags.INFINIBURN_OVERWORLD,
                DimensionTypes.THE_END_ID,
                1.0f,
                new DimensionType.MonsterSettings(true, false, UniformIntProvider.create(0, 0), 0)
        ));
    }
    private static RegistryKey<DimensionOptions> registerDimensionKey(String name) {
        return RegistryKey.of(RegistryKeys.DIMENSION, BCSMPContentMain.domainExpansionId(name));
    }
    private static RegistryKey<World> registerDimensionLevelKey(String name) {
        return RegistryKey.of(RegistryKeys.WORLD, BCSMPContentMain.domainExpansionId(name));
    }
    private static RegistryKey<DimensionType> registerDimensionType(String name) {
        return RegistryKey.of(RegistryKeys.DIMENSION_TYPE, BCSMPContentMain.domainExpansionId(name));
    }
}
