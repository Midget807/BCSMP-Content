package com.bcsmp.bcsmp_content.main.domain_expansion.component;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import net.minecraft.component.ComponentType;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.BlockPos;

import java.util.function.UnaryOperator;

public class DEModDataComponentTypes {
    public static final ComponentType<NbtComponent> EXPANDER_OWNER = register("compressor_owner", builder -> builder.codec(NbtComponent.CODEC));
    public static final ComponentType<NbtComponent> EXPANDER_TARGETS = register("compressor_owner", builder -> builder.codec(NbtComponent.CODEC));
    public static final ComponentType<Integer> EXPANDER_RADIUS = register("compressor_owner", builder -> builder.codec(Codecs.NONNEGATIVE_INT).packetCodec(PacketCodecs.VAR_INT));
    public static final ComponentType<BlockPos> COMPRESSOR_ORIGIN_POS = register("compressor_owner", builder -> builder.codec(BlockPos.CODEC));

    public static <T> ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, BCSMPContentMain.domainExpansionId(name), builderOperator.apply(ComponentType.builder()).build());
    }

    public static void registerDomainExpansionDataComponentTypes() {
        BCSMPContentMain.LOGGER.info("Domain Expansion -> Registering Mod Data Component Types");
    }
}
