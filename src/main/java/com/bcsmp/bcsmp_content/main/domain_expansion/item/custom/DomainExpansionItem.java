package com.bcsmp.bcsmp_content.main.domain_expansion.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class DomainExpansionItem extends Item {
    public static final String OWNER_KEY = "Owner";
    public static final String TARGETS_KEY = "Targets";
    public static final String RADIUS_KEY = "Radius";
    public static PlayerEntity owner;
    public UUID ownerUuid;
    public DefaultedList<PlayerEntity> targets = DefaultedList.of();
    public DefaultedList<UUID> targetUuids = DefaultedList.of();
    public float domainRadius = 10;

    public DomainExpansionItem(Settings settings) {
        super(settings);
        owner = null;
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack handStack = player.getStackInHand(hand);
        NbtCompound nbtCompound = handStack.getOrCreateNbt();
        if (!nbtCompound.contains(TARGETS_KEY)) {
            nbtCompound.put(TARGETS_KEY, new NbtList());
        }
        NbtList targetList = nbtCompound.getList(TARGETS_KEY, NbtElement.COMPOUND_TYPE);
        if (!world.isClient) {
            player.sendMessage(Text.literal("balls").formatted(Formatting.BOLD).formatted(Formatting.AQUA), true);
            if (!player.isSneaking()) {
                //Info Check
                targets = this.getPlayersInRange(world, player);
                if (targets.isEmpty()) {
                    player.sendMessage(Text.literal("No players within range").formatted(Formatting.RED), true);
                } else {
                    for (PlayerEntity target : targets) {
                        target.sendMessage(Text.literal("You are within range of ").formatted(Formatting.RED).append(player.getName() + "'s").formatted(Formatting.BOLD).formatted(Formatting.RED).append(Text.literal(" domain expansion").formatted(Formatting.RED)), true);
                        if (targets.size() == 1) {
                            player.sendMessage(Text.literal(targets.size() + " player within range").formatted(Formatting.AQUA), true);
                        } else {
                            player.sendMessage(Text.literal(targets.size() + " players within range").formatted(Formatting.AQUA), true);
                        }
                    }
                }
                targets = DefaultedList.of();
                if (owner != null) {
                    if (ownerUuid != null) {
                        ownerUuid = null;
                    }
                    owner = null;
                }
                this.domainRadius = 10;
                
            } else {
                //Applies info
                targets = this.getPlayersInRange(world, player);
                owner = player;
                ownerUuid = owner.getUuid();

                nbtCompound.putUuid(OWNER_KEY, ownerUuid);
                for (PlayerEntity playerEntity : targets) {
                    targetUuids.add(playerEntity.getUuid());
                }
                for (int i = 0; i < targetUuids.size(); i++) {
                    NbtCompound newTargetNbt = new NbtCompound();
                    newTargetNbt.putUuid(TARGETS_KEY, targetUuids.get(i));
                    targetList.add(i, newTargetNbt);
                }
                this.domainRadius = getDomainRadius(player);
                nbtCompound.putFloat(RADIUS_KEY, this.domainRadius);
            }
        }
        return TypedActionResult.pass(handStack);
    }

    public float getDomainRadius(PlayerEntity player) {
        return (float) (player.experienceLevel + 10);
    }

    public DefaultedList<PlayerEntity> getPlayersInRange(World world, PlayerEntity player) {
        Box box = player.getBoundingBox().expand(this.getDomainRadius(player), this.getDomainRadius(player), this.getDomainRadius(player));
        List<PlayerEntity> entitiesInBox = world.getEntitiesByClass(PlayerEntity.class, box, EntityPredicates.EXCEPT_SPECTATOR);
        DefaultedList<PlayerEntity> entitiesInSphere = DefaultedList.of();
        for (PlayerEntity target : entitiesInBox) {
            if (player.distanceTo(target) <= this.getDomainRadius(player) && target != player) {
                entitiesInSphere.add(target);
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 20), player);
            }
        }
        //Damages player using item to avoid the glowing effect being abused outside the intended purposes
        player.damage(player.getDamageSources().magic(), 14.0f);
        return entitiesInSphere;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        if (owner == null) {
            tooltip.add(Text.literal("Owner not found").formatted(Formatting.DARK_GRAY));
        } else {
            tooltip.add(Text.literal("Owner: ").append(owner.getName()).formatted(Formatting.GRAY));
        }
        tooltip.add(Text.literal("Radius: " + this.domainRadius));
    }

    @Override
    public boolean isNbtSynced() {
        return true;
    }

}