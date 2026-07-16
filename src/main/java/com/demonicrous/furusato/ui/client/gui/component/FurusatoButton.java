package com.demonicrous.furusato.ui.client.gui.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

/**
 * A polished Minecraft button using the standard widget texture, sounds and
 * interaction states, with an additional visible keyboard-focus state.
 */
public class FurusatoButton extends GuiButton {
    private boolean focused;

    public FurusatoButton(int id, int x, int y, int width, int height, String text) {
        super(id, x, y, width, height, text);
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    public boolean isFocused() {
        return focused;
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
        if (!visible) {
            return;
        }

        hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
        int state = getHoverState(hovered || focused);
        minecraft.getTextureManager().bindTexture(BUTTON_TEXTURES);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.blendFunc(770, 771);

        int half = width / 2;
        drawTexturedModalRect(x, y, 0, 46 + state * 20, half, height);
        drawTexturedModalRect(x + half, y, 200 - (width - half), 46 + state * 20,
                width - half, height);

        if (focused && enabled) {
            int pulse = 150 + (int) (55 * Math.sin(System.currentTimeMillis() / 180.0));
            int focusColor = (pulse << 24) | 0x00FFD36A;
            Gui.drawRect(x, y, x + width, y + 1, focusColor);
            Gui.drawRect(x, y + height - 1, x + width, y + height, focusColor);
            Gui.drawRect(x, y, x + 1, y + height, focusColor);
            Gui.drawRect(x + width - 1, y, x + width, y + height, focusColor);
        }

        int textColor = !enabled ? 0xFFA0A0A0 : hovered || focused ? 0xFFFFFFA0 : 0xFFE0E0E0;
        drawCenteredString(
                minecraft.fontRenderer,
                displayString,
                x + width / 2,
                y + (height - 8) / 2,
                textColor
        );
    }
}
