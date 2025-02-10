package com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.feature.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.gen.feature.FeatureConfig;

public record ObsidianSpikeConfig(int height, Identifier blockId) implements FeatureConfig {
    public static Codec<ObsidianSpikeConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codecs.POSITIVE_INT.fieldOf("height").forGetter(ObsidianSpikeConfig::height),
                    Identifier.CODEC.fieldOf("blockId").forGetter(ObsidianSpikeConfig::blockId)
            ).apply(instance, ObsidianSpikeConfig::new)
    );
}
