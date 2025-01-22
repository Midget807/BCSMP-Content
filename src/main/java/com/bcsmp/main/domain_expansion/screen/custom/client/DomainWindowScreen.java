package com.bcsmo.main.domain_expansion.screen.custom.client;

public class DomainWindowScreen extends HandledScreen<DomainWindowScreenHandler> implements ScreenHandlerProvider<DomainWindowScreenHandler> {
    private static final Identifier TEXTURE = BCSMPS2ContentMain.domainExpansionId("textures/gui/container/domain_window.png");

    public DomainWindowScreen(DomainWindowScreenHandler handler, PlayerInventory playerInventory, Text title) {
        super(handler, playerInventory, title);
        this.backgroundHeight = 133
        this.playerInventoryTitleY = this.backgroudHeight - 94;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        context.drawTexture(TEXTURE, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }
}