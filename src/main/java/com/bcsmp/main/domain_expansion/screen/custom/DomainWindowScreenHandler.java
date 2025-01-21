package com.bcsmp.main.domain_expansion.screen.custom;

public class DomainWindowScreenHandler extends ScreenHandler {

    public DomainWindowScreenHandler(ScreenHandlerType<?> type, int syncId) {
        this(type, syncId);
    }
    public DomainWindowScreenHandler(ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
        //adds available domain occupance slots
        for (int i = 0; i < 10; i++) {
            this.addSlot(new Slot(<inventory>, i, 17 + ));
        }
        
    }
}