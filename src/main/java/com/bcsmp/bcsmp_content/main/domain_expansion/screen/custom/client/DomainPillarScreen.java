package com.bcsmp.bcsmp_content.main.domain_expansion.screen.custom.client;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import com.bcsmp.bcsmp_content.main.domain_expansion.item.DEModItems;
import com.bcsmp.bcsmp_content.main.domain_expansion.item.custom.DomainExpansionItem;
import com.bcsmp.bcsmp_content.main.domain_expansion.screen.custom.DomainPillarScreenHandler;
import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.dimension.DEModDimensions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.UUID;

@Environment(EnvType.CLIENT)
public class DomainPillarScreen extends HandledScreen<DomainPillarScreenHandler> implements ScreenHandlerProvider<DomainPillarScreenHandler> {
    private static final Identifier TEXTURE = BCSMPContentMain.domainExpansionId("textures/gui/container/domain_window.png");
    public DomainPillarScreen(DomainPillarScreenHandler handler, PlayerInventory playerInventory, Text title) {
        super(handler, playerInventory, title);
        this.backgroundHeight = 133;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        this.renderTeleportButton(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    private void renderTeleportButton(DrawContext context, int mouseX, int mouseY, float delta) {
        PublicButtonWidget buttonWidget = new PublicButtonWidget(40, 80, 120, 20, Text.literal("button"), button -> {
            this.client = MinecraftClient.getInstance();
            DomainPillarScreenHandler domainPillarScreenHandler = (DomainPillarScreenHandler) this.client.player.currentScreenHandler;
            if (domainPillarScreenHandler.getInventory().getStack(0).getItem() == DEModItems.DOMAIN_EXPANDER && this.client.world != null) {
                ItemStack expander = domainPillarScreenHandler.getInventory().getStack(0);
                if (expander.getNbt() != null &&
                        expander.getNbt().getUuid(DomainExpansionItem.OWNER_KEY) != null &&
                        expander.getNbt().getList(DomainExpansionItem.TARGETS_KEY, NbtElement.COMPOUND_TYPE) != null &&
                        expander.getNbt().getFloat(DomainExpansionItem.RADIUS_KEY) >= 10
                ) {
                    UUID ownerPlayer = expander.getNbt().getUuid(DomainExpansionItem.OWNER_KEY);
                    NbtList targetNbtList = expander.getNbt().getList(DomainExpansionItem.TARGETS_KEY, NbtElement.COMPOUND_TYPE);
                    MinecraftServer server = this.client.getServer();
                    if (server != null) {
                        ServerPlayerEntity serverOwnerPlayer = server.getPlayerManager().getPlayer(ownerPlayer);
                        RegistryKey<World> domainKey = this.getRandomDomain(serverOwnerPlayer.getRandom());
                        ServerWorld domain = server.getWorld(domainKey);
                        for (int i = 0; i < targetNbtList.size(); i++) {
                            NbtCompound nbtCompound = targetNbtList.getCompound(i);
                            ServerPlayerEntity serverPlayer = server.getPlayerManager().getPlayer(nbtCompound.getUuid(DomainExpansionItem.TARGETS_KEY));
                            if (serverPlayer != null) {
                                serverPlayer.teleport(domain, 0, 0, 0, 0.0f, 0.0f);
                            }
                        }
                        if (serverOwnerPlayer != null) {
                            ItemStack compressor = new ItemStack(DEModItems.DOMAIN_COMPRESSOR);
                            NbtCompound compressorNbt = compressor.getOrCreateNbt();
                            compressorNbt.putInt("X", serverOwnerPlayer.getBlockX());
                            compressorNbt.putInt("Y", serverOwnerPlayer.getBlockY());
                            compressorNbt.putInt("Z", serverOwnerPlayer.getBlockZ());
                            serverOwnerPlayer.giveItemStack(compressor);
                            serverOwnerPlayer.teleport(domain, 0, 0, 0, 0.0f, 0.0f);
                        }
                        
                    }
                }
            }
        });
        this.addDrawableChild(buttonWidget);
    }

    private RegistryKey<World> getRandomDomain(Random random) { // TODO: 22/02/2025 make it check for empty domains
        int index = random.nextBetween(0, 3);
        /*return switch (index) {
            case 1 -> DEModDimensions.DOMAIN_2_LEVEL_KEY;
            case 2 -> DEModDimensions.DOMAIN_3_LEVEL_KEY;
            case 3 -> DEModDimensions.DOMAIN_4_LEVEL_KEY;
            default -> DEModDimensions.DOMAIN_1_LEVEL_KEY;
        };*/
        return DEModDimensions.DOMAIN_1_LEVEL_KEY;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        context.drawTexture(TEXTURE, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }
}