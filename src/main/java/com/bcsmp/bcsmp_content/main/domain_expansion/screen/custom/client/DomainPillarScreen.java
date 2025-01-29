package com.bcsmp.bcsmp_content.main.domain_expansion.screen.custom.client;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import com.bcsmp.bcsmp_content.main.domain_expansion.item.DEModItems;
import com.bcsmp.bcsmp_content.main.domain_expansion.item.custom.DomainExpansionItem;
import com.bcsmp.bcsmp_content.main.domain_expansion.network.DEModMessages;
import com.bcsmp.bcsmp_content.main.domain_expansion.screen.custom.DomainPillarScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

@Environment(EnvType.CLIENT)
public class DomainPillarScreen extends HandledScreen<DomainPillarScreenHandler> implements ScreenHandlerProvider<DomainPillarScreenHandler> {
    public static final ButtonWidget.NarrationSupplier DEFAULT_NARRATION_SUPPLIER = textSupplier -> (MutableText)textSupplier.get();
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
        this.renderTeamSelector(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    private void renderTeamSelector(DrawContext context, int mouseX, int mouseY, float delta) {
        PublicButtonWidget buttonWidget = new PublicButtonWidget(40, 80, 120, 20, Text.literal("button"), button -> {
            this.client = MinecraftClient.getInstance();
            PacketByteBuf buf = PacketByteBufs.create();
            DomainPillarScreenHandler domainPillarScreenHandler = (DomainPillarScreenHandler) this.client.player.currentScreenHandler;
            if (domainPillarScreenHandler.getInventory().getStack(0).getItem() == DEModItems.DOMAIN_EXPANDER) {
                ItemStack expander = domainPillarScreenHandler.getInventory().getStack(0);
                if (expander.getNbt() != null) {
                    buf.writeBlockPos(this.client.player.getBlockPos());
                    buf.writeFloat(expander.getNbt().getFloat(DomainExpansionItem.RADIUS_KEY));

                    UUID ownerUuid = expander.getNbt().getUuid(DomainExpansionItem.OWNER_KEY);
                    DefaultedList<UUID> targetUuids = DefaultedList.of();
                    NbtList nbtTargetList = expander.getNbt().getList(DomainExpansionItem.TARGETS_KEY, NbtElement.COMPOUND_TYPE);
                    for (int i = 0; i < nbtTargetList.size() && !nbtTargetList.isEmpty(); i++) {
                        NbtCompound selectedTargetCompound = nbtTargetList.getCompound(i);
                        targetUuids.add(i, selectedTargetCompound.getUuid(DomainExpansionItem.TARGETS_KEY));
                    }

                    if (expander.getNbt().get(DomainExpansionItem.OWNER_KEY) != null) {
                        buf.writeBoolean(true);
                        buf.writeUuid(ownerUuid);
                        ClientPlayNetworking.send(DEModMessages.TELEPORT_DOMAIN, buf);
                    } else if (expander.getNbt().get(DomainExpansionItem.TARGETS_KEY) != null) {
                        buf.writeBoolean(false);
                        for (UUID target : targetUuids) {
                            buf.writeUuid(target);
                            ClientPlayNetworking.send(DEModMessages.TELEPORT_DOMAIN, buf);
                        }
                    }
                }
            }
        });
        this.addDrawableChild(buttonWidget);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        context.drawTexture(TEXTURE, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }
}