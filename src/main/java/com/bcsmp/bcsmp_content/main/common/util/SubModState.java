package com.bcsmp.bcsmp_content.main.common.util;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;

public class SubModState extends PersistentState {
    public static final String DOMAIN_EXPANSION_ENABLED_KEY = "DomainExpansionEnabled";
    public static final String CHARTER_FIX_ENABLED_KEY = "CharterFixEnabled";
    public boolean domainExpansionModEnabled = true;
    public boolean charterFixModEnabled = false;
    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.putBoolean(DOMAIN_EXPANSION_ENABLED_KEY, domainExpansionModEnabled);
        nbt.putBoolean(CHARTER_FIX_ENABLED_KEY, charterFixModEnabled);
        return nbt;
    }
    public static SubModState createFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        SubModState state = new SubModState();
        state.domainExpansionModEnabled = nbt.getBoolean(DOMAIN_EXPANSION_ENABLED_KEY);
        state.charterFixModEnabled = nbt.getBoolean(CHARTER_FIX_ENABLED_KEY);
        return state;
    }
    public static SubModState createNew() {
        SubModState state = new SubModState();
        state.domainExpansionModEnabled = true;
        state.charterFixModEnabled = false;
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
}
