package com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.border;

import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.border.DomainBorder;

public interface DomainBorderListener {
    void onSizeChange(DomainBorder border, double size);

    void onInterpolateSize(DomainBorder border, double fromSize, double toSize, long time);

    void onCenterChanged(DomainBorder border, double centerX, double centerZ);

    void onWarningTimeChanged(DomainBorder border, int warningTime);

    void onWarningBlocksChanged(DomainBorder border, int warningBlockDistance);

    void onDamagePerBlockChanged(DomainBorder border, double damagePerBlock);

    void onSafeZoneChanged(DomainBorder border, double safeZoneRadius);
    public static class DomainBorderSyncer implements DomainBorderListener {
        public final DomainBorder border;

        public DomainBorderSyncer(DomainBorder border) {
            this.border = border;
        }

        @Override
        public void onSizeChange(DomainBorder border, double size) {
            this.border.setSize(size);
        }

        @Override
        public void onInterpolateSize(DomainBorder border, double fromSize, double toSize, long time) {
            this.border.interpolateSize(fromSize, toSize, time);
        }

        @Override
        public void onCenterChanged(DomainBorder border, double centerX, double centerZ) {
            this.border.setCenter(centerX, centerZ);
        }

        @Override
        public void onWarningTimeChanged(DomainBorder border, int warningTime) {
        }

        @Override
        public void onWarningBlocksChanged(DomainBorder border, int warningBlockDistance) {

        }

        @Override
        public void onDamagePerBlockChanged(DomainBorder border, double damagePerBlock) {

        }

        @Override
        public void onSafeZoneChanged(DomainBorder border, double safeZoneRadius) {

        }
    }
}
