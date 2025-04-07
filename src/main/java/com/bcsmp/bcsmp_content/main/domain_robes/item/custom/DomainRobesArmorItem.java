package com.bcsmp.bcsmp_content.main.domain_robes.item.custom;

import com.bcsmp.bcsmp_content.main.domain_robes.item.DRModItems;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.HashMap;
import java.util.Map;

public class DomainRobesArmorItem extends DimensionalArmorItem {
    private boolean isVanished = false;
    public DomainRobesArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient) {
            if (entity instanceof PlayerEntity player) {
                if (hasFullSuitOfArmor(player)) {
                    this.vanish(player, world);
                    this.escape(world, player);
                }
            }
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    private void vanish(PlayerEntity player, World world) {
        if (player.getStackInHand(Hand.MAIN_HAND).isEmpty() && player.getStackInHand(Hand.OFF_HAND).isEmpty() && player.isSneaking()) {
            if (player.hasStatusEffect(StatusEffects.INVISIBILITY)) {
                player.clearPotionSwirls();
            }
            if (!isVanished) {
                ((ServerWorld) world).spawnParticles(ParticleTypes.REVERSE_PORTAL, player.getX(), player.getY() + 0.5, player.getZ(), 10, 0.1, 0.1, 0.1, 1.0);
                this.isVanished = true;
            }
            player.setInvisible(true);
        } else {
            if (player.hasStatusEffect(StatusEffects.INVISIBILITY)) {
                player.updatePotionSwirls();
                player.setInvisible(true);
            } else {
                if (isVanished) {
                    player.setInvisible(false);
                    this.isVanished = false;
                    ((ServerWorld) world).spawnParticles(ParticleTypes.PORTAL, player.getX(), player.getY() + 0.5, player.getZ(), 10, 0.1, 0.1, 0.1, 1.0);
                }
            }
        }
    }

    private void escape(World world, PlayerEntity player) {
        if (player.getHealth() <= 4.0f) {
            if (!world.isClient
                    && !(player.getItemCooldownManager().isCoolingDown(player.getEquippedStack(EquipmentSlot.HEAD).getItem())
                        || player.getItemCooldownManager().isCoolingDown(player.getEquippedStack(EquipmentSlot.CHEST).getItem())
                        || player.getItemCooldownManager().isCoolingDown(player.getEquippedStack(EquipmentSlot.LEGS).getItem())
                        || player.getItemCooldownManager().isCoolingDown(player.getEquippedStack(EquipmentSlot.FEET).getItem())
                    )
            ) {
                for (int i = 0; i < 16; i++) {
                    double d = Math.max(8, player.getX() + (player.getRandom().nextDouble() - 0.5) * 32.0);
                    double e = MathHelper.clamp(
                            player.getY() + (player.getRandom().nextInt(32) - 8), (double)world.getBottomY(), (double)(world.getBottomY() + ((ServerWorld)world).getLogicalHeight() - 1)
                    );
                    double f = Math.max(8, player.getZ() + (player.getRandom().nextDouble() - 0.5) * 32.0);
                    if (player.hasVehicle()) {
                        player.stopRiding();
                    }

                    Vec3d vec3d = player.getPos();
                    if (player.teleport(d, e, f, true)) {
                        world.emitGameEvent(GameEvent.TELEPORT, vec3d, GameEvent.Emitter.of(player));
                        SoundCategory soundCategory;
                        SoundEvent soundEvent;
                        soundEvent = SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
                        soundCategory = SoundCategory.PLAYERS;


                        world.playSound(null, player.getX(), player.getY(), player.getZ(), soundEvent, soundCategory);
                        player.onLanding();
                        break;
                    }
                }

                player.clearCurrentExplosion();
                for (ItemStack stack : player.getArmorItems()) {
                    player.getItemCooldownManager().set(stack.getItem(), 5 * 20);
                }
                
            }
        }
    }

    public static boolean hasFullSuitOfArmor(PlayerEntity player) {
        return player.getInventory().getArmorStack(0).isOf(DRModItems.BOOTS)
                && player.getInventory().getArmorStack(1).isOf(DRModItems.PANTS)
                && player.getInventory().getArmorStack(2).isOf(DRModItems.ROBE)
                && player.getInventory().getArmorStack(3).isOf(DRModItems.HOOD);
    }
}
