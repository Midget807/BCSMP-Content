package com.bcsmp.main.domain_expansion.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

public class DomainExpansionItem extends Item {
    public PlayerEntity owner;
    public DefaultedList<PlayerEntity> targets = DefaultedList.of();

    public DomainExpansionItem(Settings settings) {
        super(settings);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack handStack = player.getStackInHand(hand);
        if (!world.isClient) {
            player.sendMessage(Text.literal("balls").formatted(Formatting.BOLD).formatted(Formatting.AQUA), true);
            if (!player.isSneaking()) {
                //Player Check
                targets = this.getPlayersInRange(world, player);
                if (targets.isEmpty()) {
                    player.sendMessage(Text.literal("No players within range").formatted(Formatting.RED), true);
                } else {
                    for (PlayerEntity target : targets) {
                        target.sendMessage(Text.literal("You are within range of ").formatted(Formatting.RED).append(Text.literal(player.getName() + "'s").formatted(Formatting.BOLD).formatted(Formatting.RED).append(Text.literal(" domain expansion").formatted(Formatting.RED))), true);
                        target.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 3), player);
                        if (targets.size() == 1) {
                            player.sendMessage(Text.literal(targets.size() + " player within range").formatted(Formatting.AQUA), true);
                        } else {
                            player.sendMessage(Text.literal(targets.size() + " players within range").formatted(Formatting.AQUA), true);
                        }
                    }
                }
                targets = DefaultedList.of();
                if (this.owner != null) {
                    this.owner = null;
                }
                
            } else {
                //Collects nearby targets
                targets = this.getPlayersInRange(world, player);
                this.owner = player;
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
            }
        }
        //Damages player using item to avoid the glowing effect being abused outside the intended purposes
        player.damage(player.getDamageSources().magic(), 14.0f);
        return entitiesInSphere;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        if (this.owner == null) {
            tooltip.add(Text.literal("Owner not found").formatted(Formatting.DARK_GRAY));
        } else {
            tooltip.add(Text.literal("Owner: ").append(this.owner.getName()).formatted(Formatting.GRAY));
        }
    }
}