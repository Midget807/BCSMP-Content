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

    public int getDomainRadius(PlayerEntity player) {
        player.getExperienceLevel
    }

    public List<PlayerEntity> getPlayersInRange(World world, PlayerEntity player) {
        Box box = player.getBoundingBox().expand(-10.0, -10.0, -10.0, 10.0, 10.0, 10.0);
    }
}