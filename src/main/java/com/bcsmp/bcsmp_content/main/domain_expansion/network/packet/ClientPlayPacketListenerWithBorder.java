package com.bcsmp.bcsmp_content.main.domain_expansion.network.packet;

import com.bcsmp.bcsmp_content.main.domain_expansion.network.packet.S2C.DomainBorderInitializePacket;
import com.bcsmp.bcsmp_content.main.domain_expansion.network.packet.S2C.DomainBorderInterpolateSizePacket;

public interface ClientPlayPacketListenerWithBorder {
    void onDomainBorderInitialize(DomainBorderInitializePacket packet);
    void onDomainBorderInterpolateSize(DomainBorderInterpolateSizePacket packet);
    //void onDomainBorderSizeChanged(DomainBorderSizeChangedPacket packet);
    //void onDomainBorderWarningTimeChanged(DomainBorderWarningTimeChanged packet);
    //void onDomainBorderWarningBlocksChanged(DomainBorderWarningBlocksChanged packet);
    //void onDomainBorderCenterChanged(DomainBorderCenterChangedPacket packet);
}
