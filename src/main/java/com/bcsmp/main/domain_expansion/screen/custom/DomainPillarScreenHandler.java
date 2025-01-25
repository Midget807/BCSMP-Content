package com.bcsmp.main.domain_expansion.screen.custom;

import com.bcsmp.main.domain_expansion.screen.DEModScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;

public class DomainPillarScreenHandler extends ScreenHandler {
    public final Inventory inventory;

    public DomainPillarScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory) {
        this(type, syncId, playerInventory, new SimpleInventory(1));
    }
    public static DomainPillarScreenHandler create(int syncId, PlayerInventory playerInventory) {
        return new DomainPillarScreenHandler(DEModScreenHandlers.DOMAIN_PILLAR_SCREEN_HANDLER, syncId, playerInventory);
    }
    public static DomainPillarScreenHandler create(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        return new DomainPillarScreenHandler(DEModScreenHandlers.DOMAIN_PILLAR_SCREEN_HANDLER, syncId, playerInventory, inventory);
    }
    public DomainPillarScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(type, syncId);
        this.inventory = inventory;
        checkSize(inventory, 1);
        inventory.onOpen(playerInventory.player);

        this.addSlot(new Slot(inventory, 0, 17 + 3 * 18, 20));

    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = this.slots.get(slot);
        if (slot2 != null && slot2.hasStack()) {
            ItemStack itemStack2 = slot2.getStack();
            itemStack = itemStack2.copy();
            if (slot < this.inventory.size()) {
                if (!this.insertItem(itemStack2, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemStack2, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            } else {
                slot2.markDirty();
            }
        }

        return itemStack;
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.inventory.onClose(player);
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }
}