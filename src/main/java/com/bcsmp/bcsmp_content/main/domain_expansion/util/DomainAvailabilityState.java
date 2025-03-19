package com.bcsmp.bcsmp_content.main.domain_expansion.util;

import com.bcsmp.bcsmp_content.main.domain_expansion.world.gen.dimension.DEModDimensions;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

public class DomainAvailabilityState extends PersistentState {
    public static final String DOMAIN_1_AVAILABLE_KEY = "Domain1Available";
    public static final String DOMAIN_2_AVAILABLE_KEY = "Domain2Available";
    public static final String DOMAIN_3_AVAILABLE_KEY = "Domain3Available";
    public static final String DOMAIN_4_AVAILABLE_KEY = "Domain4Available";
    public Boolean domain1Available = true;
    public Boolean domain2Available = true;
    public Boolean domain3Available = true;
    public Boolean domain4Available = true;
    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.putBoolean(DOMAIN_1_AVAILABLE_KEY, domain1Available);
        nbt.putBoolean(DOMAIN_2_AVAILABLE_KEY, domain2Available);
        nbt.putBoolean(DOMAIN_3_AVAILABLE_KEY, domain3Available);
        nbt.putBoolean(DOMAIN_4_AVAILABLE_KEY, domain4Available);
        return nbt;
    }
    public static DomainAvailabilityState createFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        DomainAvailabilityState state = new DomainAvailabilityState();
        state.domain1Available = nbt.getBoolean(DOMAIN_1_AVAILABLE_KEY);
        state.domain2Available = nbt.getBoolean(DOMAIN_2_AVAILABLE_KEY);
        state.domain3Available = nbt.getBoolean(DOMAIN_3_AVAILABLE_KEY);
        state.domain4Available = nbt.getBoolean(DOMAIN_4_AVAILABLE_KEY);
        return state;
    }
    public static DomainAvailabilityState createNew() {
        DomainAvailabilityState state = new DomainAvailabilityState();
        state.domain1Available = true;
        state.domain2Available = true;
        state.domain3Available = true;
        state.domain4Available = true;
        return state;
    }
    public static final Type<DomainAvailabilityState> type = new Type<>(
            DomainAvailabilityState::createNew,
            DomainAvailabilityState::createFromNbt,
            null
    );
    public static DomainAvailabilityState getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = server.getOverworld().getPersistentStateManager();
        DomainAvailabilityState state = persistentStateManager.getOrCreate(type, "domain_availability");
        state.markDirty();
        return state;
    }
    public static void setDomainAvailable(RegistryKey<World> domainKey, MinecraftServer server) {
        DomainAvailabilityState state = getServerState(server);
        if (domainKey == DEModDimensions.DOMAIN_1_LEVEL_KEY) {
            state.domain1Available = true;
        } else if (domainKey == DEModDimensions.DOMAIN_2_LEVEL_KEY) {
            state.domain2Available = true;
        } else if (domainKey == DEModDimensions.DOMAIN_3_LEVEL_KEY) {
            state.domain3Available = true;
        } else if (domainKey == DEModDimensions.DOMAIN_4_LEVEL_KEY) {
            state.domain4Available = true;
        }
    }
    public static void setDomainUnavailable(RegistryKey<World> domainKey, MinecraftServer server) {
        DomainAvailabilityState state = getServerState(server);
        if (domainKey == DEModDimensions.DOMAIN_1_LEVEL_KEY) {
            state.domain1Available = false;
        } else if (domainKey == DEModDimensions.DOMAIN_2_LEVEL_KEY) {
            state.domain2Available = false;
        } else if (domainKey == DEModDimensions.DOMAIN_3_LEVEL_KEY) {
            state.domain3Available = false;
        } else if (domainKey == DEModDimensions.DOMAIN_4_LEVEL_KEY) {
            state.domain4Available = false;
        }
    }
    public static DefaultedList<RegistryKey<World>> getAvailableDomains(MinecraftServer server) {
        DomainAvailabilityState state = getServerState(server);
        DefaultedList<RegistryKey<World>> domains = DefaultedList.of();
        if (state.domain1Available) {
            domains.add(DEModDimensions.DOMAIN_1_LEVEL_KEY);
        }
        if (state.domain2Available) {
            domains.add(DEModDimensions.DOMAIN_2_LEVEL_KEY);
        }
        if (state.domain3Available) {
            domains.add(DEModDimensions.DOMAIN_3_LEVEL_KEY);
        }
        if (state.domain4Available) {
            domains.add(DEModDimensions.DOMAIN_4_LEVEL_KEY);
        }
        return domains;
    }
    public Boolean queryAvailability(RegistryKey<World> domainKey) {
        if (domainKey == DEModDimensions.DOMAIN_1_LEVEL_KEY) {
            return this.domain1Available;
        } else if (domainKey == DEModDimensions.DOMAIN_2_LEVEL_KEY) {
            return this.domain2Available;
        } else if (domainKey == DEModDimensions.DOMAIN_3_LEVEL_KEY) {
            return this.domain3Available;
        } else if (domainKey == DEModDimensions.DOMAIN_4_LEVEL_KEY) {
            return this.domain4Available;
        } else {
            return null;
        }
    }
}
