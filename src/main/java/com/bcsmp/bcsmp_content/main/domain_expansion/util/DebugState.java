package com.bcsmp.bcsmp_content.main.domain_expansion.util;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;

public class DebugState extends PersistentState {
    public static final String BOOL_KEY = "Bool";
    public static final String SHIFT_BOOL_KEY = "ShiftBool";
    public Boolean bool = false;
    public Boolean shiftBool = false;
    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.putBoolean(BOOL_KEY, bool);
        nbt.putBoolean(SHIFT_BOOL_KEY, shiftBool);
        return nbt;
    }
    public static DebugState createFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        DebugState state = new DebugState();
        state.bool = nbt.getBoolean(BOOL_KEY);
        state.shiftBool = nbt.getBoolean(SHIFT_BOOL_KEY);
        return state;
    }
    public static DebugState createNew() {
        DebugState state = new DebugState();
        state.bool = false;
        state.shiftBool = false;
        return state;
    }
    public static final Type<DebugState> type = new Type<>(
            DebugState::createNew,
            DebugState::createFromNbt,
            null
    );
    public static DebugState getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = server.getOverworld().getPersistentStateManager();
        DebugState state = persistentStateManager.getOrCreate(type, "debug");
        state.markDirty();
        return state;
    }

    public void setBool(Boolean bool) {
        this.bool = bool;
    }

    public void setShiftBool(Boolean shiftBool) {
        this.shiftBool = shiftBool;
    }

    public Boolean getBool() {
        return bool;
    }

    public Boolean getShiftBool() {
        return shiftBool;
    }
}
