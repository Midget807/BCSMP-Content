package com.bcsmp.bcsmp_content.main.domain_expansion.world.gen.feature.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.util.List;

public record ObsidianSpikeConfig(List<Identifier> obsidianSpikes) implements FeatureConfig {
    public static Codec<ObsidianSpikeConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Identifier.CODEC.listOf().fieldOf("obsidian_spikes").forGetter(ObsidianSpikeConfig::obsidianSpikes)
            ).apply(instance, ObsidianSpikeConfig::new)
    );
}
