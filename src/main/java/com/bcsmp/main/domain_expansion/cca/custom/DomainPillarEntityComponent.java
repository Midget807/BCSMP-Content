package com.bcsmp.main.domain_expansion.cca.custom;

import com.bcsmp.main.domain_expansion.block.custom.entity.DomainPillarBlockEntity;
import com.bcsmp.main.domain_expansion.cca.DEModComponents;
import com.bcsmp.main.domain_expansion.item.custom.DomainExpansionItem;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class DomainPillarEntityComponent implements AutoSyncedComponent, CommonTickingComponent {
    public boolean domain1Available = true;
    public boolean domain2Available = true;
    public boolean domain3Available = true;
    public boolean domain4Available = true;
    private static final String DOMAIN_1_TAG_KEY = "Domain1Available";
    private static final String DOMAIN_2_TAG_KEY = "Domain2Available";
    private static final String DOMAIN_3_TAG_KEY = "Domain3Available";
    private static final String DOMAIN_4_TAG_KEY = "Domain4Available";
    private final DomainPillarBlockEntity domainPillarBlockEntity;
    public PlayerEntity owner;

    public DomainPillarEntityComponent(DomainPillarBlockEntity domainPillarBlockEntity) {
        this.domainPillarBlockEntity = domainPillarBlockEntity;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.domain1Available = tag.getBoolean(DOMAIN_1_TAG_KEY);
        this.domain2Available = tag.getBoolean(DOMAIN_2_TAG_KEY);
        this.domain3Available = tag.getBoolean(DOMAIN_3_TAG_KEY);
        this.domain4Available = tag.getBoolean(DOMAIN_4_TAG_KEY);
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putBoolean(DOMAIN_1_TAG_KEY, this.domain1Available);
        tag.putBoolean(DOMAIN_2_TAG_KEY, this.domain2Available);
        tag.putBoolean(DOMAIN_3_TAG_KEY, this.domain3Available);
        tag.putBoolean(DOMAIN_4_TAG_KEY, this.domain4Available);
    }
    private void sync() {
        DEModComponents.DOMAIN_PILLAR.sync(this.domainPillarBlockEntity);
    }

    @Override
    public void clientTick() {
        CommonTickingComponent.super.clientTick();
    }

    @Override
    public void serverTick() {
        CommonTickingComponent.super.serverTick();
    }

    @Override
    public void tick() {
        if (!(this.domainPillarBlockEntity.inventory.isEmpty() || this.domainPillarBlockEntity.inventory.get(0).isEmpty())) {
            ItemStack itemStack = this.domainPillarBlockEntity.inventory.get(0);
            if (itemStack.getItem() instanceof DomainExpansionItem domainExpansionItem) {
                if (domainExpansionItem.owner != null) {
                    this.owner = domainExpansionItem.owner;
                }
            }
        }
        domainPillarBlockEntity.owner = this.owner;
    }
}