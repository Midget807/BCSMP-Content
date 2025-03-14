package com.bcsmp.bcsmp_content.main.domain_expansion.item.custom;

import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.border.DomainBorder;
import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.border.DomainBorderWorldLink;
import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.dimension.DEModDimensions;
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
                    World domainWorld = server.getWorld(DEModDimensions.DOMAIN_1_LEVEL_KEY);
                    DomainBorder domainBorder = DomainBorderWorldLink.domain1Border;
                    domainBorder.setSize(20.0);
                } else {
                }
            }
        }
        return TypedActionResult.pass(handStack);
    }
}
