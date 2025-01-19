package com.bcsmp.main.domain_expansion.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class DomainExpansionItem extends Item {
    public DefaultedList<PlayerEntity> targets;

    public DomainExpansionItem(Settings settings) {
        super(settings);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack handStack = player.getStackInHand(hand);
        if (!world.isClient) {
            player.sendMessage(Text.literal("balls").formatted(Formatting.BOLD).formatted(Formatting.AQUA), true);
            if (!player.isSneaking()) {
                targets = this.getPlayersInRange(world, player);
                //Sends Messages
                if (targets.isEmpty()) {
                    player.sendMessage(Text.literal("No players within range").formatted(Formatting.RED), true);
                } else {
                    for (PlayerEntity target : targets) {
                        target.sendMessage(Text.literal("You are within range of ").formatted(Formatting.RED).append(Text.literal(player.getName() + "'s").formatted(Formatting.BOLD).formatting(Formatted.RED).append(Text.literal(" domain expansion").formatted(Formatting.RED)))), true);
                        if (targets.getSize() == 1) {
                            player.sendMessage(Text.literal(targets.getsize() + " player within range").formatted(Formatting.AQUA), true);
                        } else {
                            player.sendMessage(Text.literal(targets.getsize() + " players within range").formatted(Formatting.AQUA), true);
                        }
                    }
                }
                
            } else {
                //Teleports players to domain dimension
            }
        }
        return TypedActionResult.pass(handStack);
    }

    public float getDomainRadius(PlayerEntity player) {
        return (float) (player.experienceLevel + 10);
    }

    public DefaultedList<PlayerEntity> getPlayersInRange(World world, PlayerEntity player) {
        Box box = player.getBoundingBox().expand(-this.getDomainRadius(player), -this.getDomainRadius(player), -this.getDomainRadius(player), this.getDomainRadius(player), this.getDomainRadius(player), this.getDomainRadius(player));
        List<PlayerEntity> entitiesInBox = world.getEntitiesByClass(PlayerEntity.class, box, EntityPredicates.EXCEPT_SPECTATOR);
        DefaultedList<PlayerEntity> entitiesInSphere = DefaultedList.of();
        for (PlayerEntity target : entitiesInBox) {
            if (player.distanceTo(target.getPos) <= this.getDomainRadius(player) && target != player) {
                entitiesInSphere.add(target);
            }
        }
        return entitiesInSphere;
    }
}