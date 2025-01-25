package com.bcsmp.main.domain_expansion.screen.custom.client;

import com.bcsmp.BCSMPS2ContentMain;
import com.bcsmp.main.domain_expansion.network.DEModMessages;
import com.bcsmp.main.domain_expansion.screen.custom.DomainPillarScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.List;

@Environment(EnvType.CLIENT)
public class DomainPillarScreen extends HandledScreen<DomainPillarScreenHandler> implements ScreenHandlerProvider<DomainPillarScreenHandler> {
    public static final ButtonWidget.NarrationSupplier DEFAULT_NARRATION_SUPPLIER = textSupplier -> (MutableText)textSupplier.get();
    private static final Identifier TEXTURE = BCSMPS2ContentMain.domainExpansionId("textures/gui/container/domain_window.png");
    public DefaultedList<ItemStack> team1Heads = DefaultedList.of();
    public List<PlayerEntity> team1Players = List.of();
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
            World world = this.client.player.getWorld();
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeBlockPos(this.client.player.getBlockPos());
            ClientPlayNetworking.send(DEModMessages.TELEPORT_DOMAIN, buf);
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