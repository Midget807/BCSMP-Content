package com.bcsmp.bcsmp_content.main.common.util;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;

public class SubModState extends PersistentState {
    public static final String DOMAIN_EXPANSION_ENABLED_KEY = "DomainExpansionEnabled";
    public static final String CHARTER_FIX_ENABLED_KEY = "CharterFixEnabled";
    public static final String DOMAIN_ROBES_ENABLED_KEY = "DomainRobesEnabled";
    public static final String ARCANUS_CLOTHES_ENABLED_KEY = "ArcanusClothesEnabled";
    public boolean domainExpansionModEnabled = true;
    public boolean charterFixModEnabled = false;
    public boolean domainRobesModEnabled = true;
    public boolean arcanusClothesModEnabled = true;
    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.putBoolean(DOMAIN_EXPANSION_ENABLED_KEY, domainExpansionModEnabled);
        nbt.putBoolean(CHARTER_FIX_ENABLED_KEY, charterFixModEnabled);
        nbt.putBoolean(DOMAIN_ROBES_ENABLED_KEY, domainRobesModEnabled);
        nbt.putBoolean(ARCANUS_CLOTHES_ENABLED_KEY, arcanusClothesModEnabled);
        return nbt;
    }
    public static SubModState createFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        SubModState state = new SubModState();
        state.domainExpansionModEnabled = nbt.getBoolean(DOMAIN_EXPANSION_ENABLED_KEY);
        state.charterFixModEnabled = nbt.getBoolean(CHARTER_FIX_ENABLED_KEY);
        state.domainRobesModEnabled = nbt.getBoolean(DOMAIN_ROBES_ENABLED_KEY);
        state.arcanusClothesModEnabled = nbt.getBoolean(ARCANUS_CLOTHES_ENABLED_KEY);
        return state;
    }
    public static SubModState createNew() {
        SubModState state = new SubModState();
        state.domainExpansionModEnabled = true;
        state.charterFixModEnabled = false;
        state.domainRobesModEnabled = true;
        state.arcanusClothesModEnabled = true;
        return state;
    }
    public static final Type<SubModState> type = new Type<>(
            SubModState::createNew,
            SubModState::createFromNbt,
            null
    );
    public static SubModState getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = server.getOverworld().getPersistentStateManager();
        SubModState state = persistentStateManager.getOrCreate(type, "sub_mod_enabling");
        state.markDirty();
        return state;
    }

    public void setDomainExpansionModEnabled(boolean domainExpansionModEnabled) {
        this.domainExpansionModEnabled = domainExpansionModEnabled;
    }
    public boolean getDomainExpansionModEnabled() {
        return this.domainExpansionModEnabled;
    }

    public void setCharterFixModEnabled(boolean charterFixModEnabled) {
        this.charterFixModEnabled = charterFixModEnabled;
    }
    public boolean getCharterFixModEnabled() {
        return this.charterFixModEnabled;
    }

    public void setDomainRobesModEnabled(boolean domainRobesModEnabled) {
        this.domainRobesModEnabled = domainRobesModEnabled;
    }
    public boolean getDomainRobesModEnabled() {
        return this.domainRobesModEnabled;
    }

    public void setArcanusClothesModEnabled(boolean arcanusClothesModEnabled) {
        this.arcanusClothesModEnabled = arcanusClothesModEnabled;
    }
    public boolean getArcanusClothesModEnabled() {
        return this.arcanusClothesModEnabled;
    }
}
