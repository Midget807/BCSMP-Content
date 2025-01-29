package com.bcsmp.bcsmp_content.main.domain_expansion.screen.custom.client;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class PublicButtonWidget extends ButtonWidget {
    public PublicButtonWidget(int x, int y, int width, int height, Text message, PressAction onPress) {
        super(x, y, width, height, message, onPress, ButtonWidget.DEFAULT_NARRATION_SUPPLIER);
    }
}
