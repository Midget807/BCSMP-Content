package com.bcsmp.main.domain_expansion.screen.custom;

public class DomainWindowScreenHandler extends ScreenHandler {
    public final Inventory inventory;
    
    public DomainWindowScreenHandler(ScreenHandlerType<?> type, int syncId) {
        this(type, syncId, new SimpleInventory(10));
    }
    public DomainWindowScreenHandler(ScreenHandlerType<?> type, int syncId, Inventory inventory) {
        super(type, syncId);
        this.inventory = inventory;
        checkSize(inventory);
        
        //adds available domain occupance slots
        for (int i = 0; i < 10; i++) {
            this.addSlot(new Slot(<inventory>, i, 17 + i * 18));
        }
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
        
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }
}