package com.bcsmp.bcsmp_content.main.domain_expansion.util;

import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.border.DomainBorder;
import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.dimension.DEModDimensions;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

public class DomainBorderState extends PersistentState {
    public boolean domain1HasBorder = false;
    public boolean domain2HasBorder = false;
    public boolean domain3HasBorder = false;
    public boolean domain4HasBorder = false;
    private double centerX = DomainBorder.DEFAULT_BORDER.getCenterX();
    private double centerZ = DomainBorder.DEFAULT_BORDER.getCenterZ();
    private double size = DomainBorder.DEFAULT_BORDER.getSize();
    private double safeZone = DomainBorder.DEFAULT_BORDER.getSafeZone();
    private double damagePerBlock = DomainBorder.DEFAULT_BORDER.getDamagePerBlock();
    private double warningBlocks = DomainBorder.DEFAULT_BORDER.getWarningBlocks();
    private double warningTime = DomainBorder.DEFAULT_BORDER.getWarningTime();
    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putDouble("DomainBorderCenterX", centerX);
        nbt.putDouble("DomainBorderCenterZ", centerZ);
        nbt.putDouble("DomainBorderSize", size);
        nbt.putDouble("DomainBorderSafeZone", safeZone);
        nbt.putDouble("DomainBorderDamagePerBlock", damagePerBlock);
        nbt.putDouble("DomainBorderWarningBlocks", warningBlocks);
        nbt.putDouble("DomainBorderWarningTime", warningTime);

        nbt.putBoolean("Domain1HasBorder", this.domain1HasBorder);
        nbt.putBoolean("Domain2HasBorder", this.domain2HasBorder);
        nbt.putBoolean("Domain3HasBorder", this.domain3HasBorder);
        nbt.putBoolean("Domain4HasBorder", this.domain4HasBorder);

        return nbt;
    }
    public static DomainBorderState createFromNbt(NbtCompound nbt) {
        DomainBorderState state = new DomainBorderState();
        state.centerX = nbt.getDouble("DomainBorderCenterX");
        state.centerZ = nbt.getDouble("DomainBorderCenterZ");
        state.size = nbt.getDouble("DomainBorderSize");
        state.safeZone = nbt.getDouble("DomainBorderSafeZone");
        state.damagePerBlock = nbt.getDouble("DomainBorderDamagePerBlock");
        state.warningBlocks = nbt.getDouble("DomainBorderWarningBlocks");
        state.warningTime = nbt.getDouble("DomainBorderWarningTime");

        state.domain1HasBorder = nbt.getBoolean("Domain1HasBorder");
        state.domain2HasBorder = nbt.getBoolean("Domain2HasBorder");
        state.domain3HasBorder = nbt.getBoolean("Domain3HasBorder");
        state.domain4HasBorder = nbt.getBoolean("Domain4HasBorder");
        return state;
    }
    public static DomainBorderState createNew() {
        DomainBorderState state = new DomainBorderState();
        state.centerX = DomainBorder.DEFAULT_BORDER.getCenterX();
        state.centerZ = DomainBorder.DEFAULT_BORDER.getCenterZ();
        state.size = DomainBorder.DEFAULT_BORDER.getSize();
        state.safeZone = DomainBorder.DEFAULT_BORDER.getSafeZone();
        state.damagePerBlock = DomainBorder.DEFAULT_BORDER.getDamagePerBlock();
        state.warningBlocks = DomainBorder.DEFAULT_BORDER.getWarningBlocks();
        state.warningTime = DomainBorder.DEFAULT_BORDER.getWarningTime();
        return state;
    }
    public static DomainBorderState getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();
        DomainBorderState state = persistentStateManager.getOrCreate(DomainBorderState::createFromNbt, DomainBorderState::createNew, "domain_border");
        state.markDirty();
        return state;
    }

    public boolean getDomainHasBorder(RegistryKey<World> domainKey) {
        if (domainKey == DEModDimensions.DOMAIN_1_LEVEL_KEY) {
            return domain1HasBorder;
        } else if (domainKey == DEModDimensions.DOMAIN_2_LEVEL_KEY) {
            return domain2HasBorder;
        } else if (domainKey == DEModDimensions.DOMAIN_3_LEVEL_KEY) {
            return domain3HasBorder;
        } else if (domainKey == DEModDimensions.DOMAIN_4_LEVEL_KEY) {
            return domain4HasBorder;
        } else {
            throw new NullPointerException();
        }
    }

    public void setDomainHasBorder(RegistryKey<World> domainKey, boolean hasBorder) {
        if (domainKey == DEModDimensions.DOMAIN_1_LEVEL_KEY) {
            domain1HasBorder = hasBorder;
        } else if (domainKey == DEModDimensions.DOMAIN_2_LEVEL_KEY) {
            domain2HasBorder = hasBorder;
        } else if (domainKey == DEModDimensions.DOMAIN_3_LEVEL_KEY) {
            domain3HasBorder = hasBorder;
        } else if (domainKey == DEModDimensions.DOMAIN_4_LEVEL_KEY) {
            domain4HasBorder = hasBorder;
        } else {
            throw new NullPointerException();
        }
    }


    public double getCenterX() {
        return centerX;
    }

    public double getCenterZ() {
        return centerZ;
    }

    public double getSize() {
        return size;
    }

    public double getSafeZone() {
        return safeZone;
    }

    public double getDamagePerBlock() {
        return damagePerBlock;
    }

    public double getWarningBlocks() {
        return warningBlocks;
    }

    public double getWarningTime() {
        return warningTime;
    }
    public void fromDomainBorder(DomainBorder domainBorder) {
        centerX = domainBorder.getCenterX();
        centerZ = domainBorder.getCenterZ();
        size = domainBorder.getSize();
        safeZone = domainBorder.getSafeZone();
        damagePerBlock = domainBorder.getDamagePerBlock();
        warningBlocks = domainBorder.getWarningBlocks();
        warningTime = domainBorder.getWarningTime();
        markDirty();
    }
}
