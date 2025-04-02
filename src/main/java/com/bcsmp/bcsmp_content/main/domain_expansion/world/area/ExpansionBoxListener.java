package com.bcsmp.bcsmp_content.main.domain_expansion.world.area;

public interface ExpansionBoxListener {
    void onSizeChange(ExpansionBox box, double size);

    void onInterpolateSize(ExpansionBox box, double fromSize, double toSize, long time);

    void onCenterChanged(ExpansionBox box, double centerX, double centerY, double centerZ);

    //void onWarningTimeChanged(ExpansionBox box, int warningTime);

    //void onWarningBlocksChanged(ExpansionBox box, int warningBlockDistance);

    //void onDamagePerBlockChanged(ExpansionBox box, double damagePerBlock);

    //void onSafeZoneChanged(Worldbox box, double safeZoneRadius);

    public static class ExpansionBoxSyncer implements ExpansionBoxListener {
        public final ExpansionBox box;

        public ExpansionBoxSyncer(ExpansionBox box) {
            this.box = box;
        }

        @Override
        public void onSizeChange(ExpansionBox box, double size) {
            this.box.setSize(size);
        }

        @Override
        public void onInterpolateSize(ExpansionBox box, double fromSize, double toSize, long time) {
            this.box.interpolateSize(fromSize, toSize, time);
        }

        @Override
        public void onCenterChanged(ExpansionBox box, double centerX, double centerY, double centerZ) {
            this.box.setCenter(centerX, centerY, centerZ);
        }
    }

}
