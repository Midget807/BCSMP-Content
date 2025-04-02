package com.bcsmp.bcsmp_content.main.domain_expansion.network.packet;

import com.bcsmp.bcsmp_content.main.domain_expansion.network.packet.S2C.*;

public interface DEClientPlayerListener {
    void onExpansionBorderInitialize(InitializeExpansionBoxPacket packet);

    void onExpansionBoxCenterChanged(ExpansionBoxCenterChangedPacket packet);

    void onExpansionBoxSizeChanged(ExpansionBoxSizeChangedPacket packet);

    void onExpansionBoxInterpolateSize(ExpansionBoxInterpolateSizePacket packet);

    void onExpansionBoxWarningTimeChanged(ExpansionBoxWarningTimeChangedPacket packet);

    void onExpansionBoxWarningBlocksChanged(ExpansionBoxWarningBlocksChangedPacket packet);
}
