package com.bcsmp.bcsmp_content.main.domain_expansion.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class DebuggerItem extends Item {

    public DebuggerItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack handStack = player.getStackInHand(hand);
        if (!world.isClient) {
            MinecraftServer server = player.getServer();
            if (server != null) {
                if (player.isSneaking()) {
                } else {
                }
            }
        }
        return TypedActionResult.pass(handStack);
    }
}
