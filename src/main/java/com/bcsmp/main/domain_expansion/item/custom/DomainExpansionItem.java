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

    public DomainExpansionItem(Settings settings) {
        super(settings);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack handStack = player.getStackInHand(hand);
        if (!world.isClient) {
            player.sendMessage(Text.literal("balls").formatted(Formatting.BOLD).formatted(Formatting.AQUA), true);
            if (!player.isSneaking()) {
                this.getPlayersInRange(world, player);
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