package com.bcsmp.bcsmp_content.mixin.domain_expansion.client;

import com.bcsmp.bcsmp_content.main.domain_expansion.network.DEModMessages;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.network.*;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.listener.ClientConfigurationPacketListener;
import net.minecraft.network.listener.TickablePacketListener;
import net.minecraft.network.packet.s2c.config.ReadyS2CPacket;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.resource.ResourceFactory;
import net.minecraft.resource.featuretoggle.FeatureSet;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Mixin(ClientConfigurationNetworkHandler.class)
public abstract class ClientConfigurationNetworkHandlerMixin extends ClientCommonNetworkHandler implements ClientConfigurationPacketListener, TickablePacketListener {
    @Shadow @Final private GameProfile profile;

    @Shadow private FeatureSet enabledFeatures;

    @Shadow @Nullable protected ChatHud.@Nullable ChatState chatState;


    @Shadow protected abstract <T> T openClientDataPack(Function<ResourceFactory, T> opener);

    @Shadow @Final private ClientRegistries clientRegistries;

    @Shadow @Final private DynamicRegistryManager.Immutable registryManager;

    public ClientConfigurationNetworkHandlerMixin(MinecraftClient client, ClientConnection connection, ClientConnectionState connectionState) {
        super(client, connection, connectionState);
    }
    @Inject(method = "onReady", at = @At("TAIL"))
    private void domainExpansion$addPackets(ReadyS2CPacket packet, CallbackInfo ci) {
        DynamicRegistryManager.Immutable immutable = this.openClientDataPack(
                factory -> this.clientRegistries.createRegistryManager(factory, this.registryManager, this.connection.isLocal())
        );
        this.connection.transitionInbound(DEModMessages.S2C.bind(RegistryByteBuf.makeFactory(immutable)), new ClientPlayNetworkHandler(
                this.client,
                this.connection,

                new ClientConnectionState(
                        this.profile,
                        this.worldSession,
                        immutable,
                        this.enabledFeatures,
                        this.brand,
                        this.serverInfo,
                        this.postDisconnectScreen,
                        this.serverCookies,
                        this.chatState,
                        this.strictErrorHandling,
                        this.customReportDetails,
                        this.serverLinks
                )
        ));

    }
}
