package com.demonicrous.furusato.ui.client.gui.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;

/** A flat, high-contrast button inspired by the modern Minecraft UI. */
public class FurusatoButton extends GuiButton {
    private static final int BACKGROUND = 0xD0202020;
    private static final int HOVERED = 0xFFF0F0F0;
    private static final int DISABLED = 0xA0181818;
    private static final int BORDER = 0xFFFFFFFF;

    public FurusatoButton(int id, int x, int y, int width, int height, String text) {
        super(id, x, y, width, height, text);
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
        if (!visible) {
            return;
        }

        hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
        int fill = !enabled ? DISABLED : hovered ? HOVERED : BACKGROUND;
        int textColor = !enabled ? 0xFF777777 : hovered ? 0xFF101010 : 0xFFFFFFFF;

        Gui.drawRect(x, y, x + width, y + height, fill);
        if (hovered && enabled) {
            Gui.drawRect(x, y, x + width, y + 1, BORDER);
            Gui.drawRect(x, y + height - 1, x + width, y + height, BORDER);
            Gui.drawRect(x, y, x + 1, y + height, BORDER);
            Gui.drawRect(x + width - 1, y, x + width, y + height, BORDER);
        }

        drawCenteredString(
                minecraft.fontRenderer,
                displayString,
                x + width / 2,
                y + (height - 8) / 2,
                textColor
        );
    }
}
