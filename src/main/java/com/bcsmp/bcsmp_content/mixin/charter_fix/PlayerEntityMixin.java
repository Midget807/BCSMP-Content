package com.bcsmp.bcsmp_content.mixin.charter_fix;

import com.bcsmp.bcsmp_content.main.charter_fix.effect.CFModEffects;
import com.bcsmp.bcsmp_content.main.charter_fix.entity.CharterAttacker;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements CharterAttacker {
    private boolean usingCharter;

    public boolean isUsingCharter() {
        return usingCharter;
    }

    @Override
    public void setUsingCharter(boolean usingCharter) {
        this.usingCharter = usingCharter;
    }

    public PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean isDead() {// TODO: 14/03/2025 edit for charter effect and attacker conditions 
        if (this.getHealth() <= 0.0f) {
            if (this.getAttacker() != null && ((CharterAttacker)this.getAttacker()).usingCharter()) {
                this.dead = false;
                this.setHealth(20.0f);
                this.addStatusEffect(new StatusEffectInstance(CFModEffects.CHAINED, 60, 0, true, false));
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    @Unique
    private boolean checkHasLesserDivinity(LivingEntity attacker) {
        if (this.getAttacker() != null && this.getAttacker().isPlayer()) {
            PlayerEntity attackerPlayer = (PlayerEntity) this.getAttacker();
            PlayerInventory playerInventory = attackingPlayer.getInventory();
            DefaultedList<Item> inventoryStackItems = DefaultedList.of();
            for (int i = 0; i < playerInventory.size(); i++) {
                inventoryStackItems.add(i, playerInventory.getStack(i).getItem());
            }
            if (!inventoryStackItems.isEmpty()) {
                for (Item item : inventoryStackItems) {
                    if (item == Registries.ITEM.get(Identifier.of("charter_the_religion", "lesser_divinity_1"))
                            || item == Registries.ITEM.get(Identifier.of("charter_the_religion", "lesser_divinity_2"))
                    ) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
