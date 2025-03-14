package com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.border;

public enum DomainBorderStage {
    GROWING(4259712),
    SHRINKING(16724016),
    STATIONARY(0xFCBA03);

    private final int color;

    DomainBorderStage(int color) {
        this.color = color;
    }

    public int getColor() {
        return this.color;
    }
}
