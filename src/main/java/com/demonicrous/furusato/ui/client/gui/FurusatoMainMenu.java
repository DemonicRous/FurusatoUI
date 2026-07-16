package com.demonicrous.furusato.ui.client.gui;

import com.demonicrous.furusato.ui.FurusatoUI;
import com.demonicrous.furusato.ui.client.gui.component.FurusatoButton;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.GuiModList;
import org.lwjgl.input.Keyboard;

/** Modern title screen with a crisp, directly rendered cubemap. */
public final class FurusatoMainMenu extends GuiScreen {
    private static final ResourceLocation TITLE =
            new ResourceLocation(FurusatoUI.MOD_ID, "textures/gui/title/minecraft.png");
    private static final ResourceLocation EDITION =
            new ResourceLocation(FurusatoUI.MOD_ID, "textures/gui/title/edition.png");
    private static final int SINGLEPLAYER = 1;
    private static final int MULTIPLAYER = 2;
    private static final int OPTIONS = 0;
    private static final int QUIT = 4;
    private static final int LANGUAGE = 5;
    private static final int MODS = 6;
    private int focusedButton;

    @Override
    public void initGui() {
        buttonList.clear();
        int left = width / 2 - 100;
        int top = Math.max(118, height / 2 + 4);
        buttonList.add(new FurusatoButton(SINGLEPLAYER, left, top, 200, 20,
                I18n.format("menu.singleplayer")));
        buttonList.add(new FurusatoButton(MULTIPLAYER, left, top + 24, 200, 20,
                I18n.format("menu.multiplayer")));
        buttonList.add(new FurusatoButton(MODS, left, top + 48, 200, 20,
                I18n.format("fml.menu.mods")));
        buttonList.add(new FurusatoButton(LANGUAGE, left, top + 76, 20, 20, "L"));
        buttonList.add(new FurusatoButton(OPTIONS, left + 24, top + 76, 108, 20,
                I18n.format("menu.options")));
        buttonList.add(new FurusatoButton(QUIT, left + 136, top + 76, 64, 20,
                I18n.format("menu.quit")));
        setFocusedButton(0);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        setFocusedButton(buttonList.indexOf(button));
        switch (button.id) {
            case SINGLEPLAYER: mc.displayGuiScreen(new GuiWorldSelection(this)); break;
            case MULTIPLAYER: mc.displayGuiScreen(new GuiMultiplayer(this)); break;
            case MODS: mc.displayGuiScreen(new GuiModList(this)); break;
            case OPTIONS: mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings)); break;
            case LANGUAGE:
                mc.displayGuiScreen(new GuiLanguage(this, mc.gameSettings, mc.getLanguageManager()));
                break;
            case QUIT: mc.shutdown(); break;
            default: break;
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_TAB || keyCode == Keyboard.KEY_DOWN
                || keyCode == Keyboard.KEY_RIGHT) {
            moveFocus(1);
        } else if (keyCode == Keyboard.KEY_UP || keyCode == Keyboard.KEY_LEFT) {
            moveFocus(-1);
        } else if (keyCode == Keyboard.KEY_RETURN || keyCode == Keyboard.KEY_NUMPADENTER
                || keyCode == Keyboard.KEY_SPACE) {
            GuiButton button = buttonList.get(focusedButton);
            if (button.enabled && button.visible) {
                button.playPressSound(mc.getSoundHandler());
                actionPerformed(button);
            }
        } else {
            super.keyTyped(typedChar, keyCode);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        PanoramaRenderer.draw(mc);
        drawGradientRect(0, 0, width, 44, 0x8A000000, 0x00000000);
        drawGradientRect(0, height - 42, width, height, 0x00000000, 0xA0000000);

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableBlend();
        mc.getTextureManager().bindTexture(TITLE);
        int titleWidth = Math.min(256, width - 32);
        int titleHeight = titleWidth / 4;
        drawModalRectWithCustomSizedTexture(width / 2 - titleWidth / 2, 18,
                0, 0, titleWidth, titleHeight, 1024, 256);
        mc.getTextureManager().bindTexture(EDITION);
        int editionWidth = Math.min(128, width - 64);
        drawModalRectWithCustomSizedTexture(width / 2 - editionWidth / 2,
                18 + titleHeight - 2, 0, 0, editionWidth, editionWidth / 8, 512, 64);

        drawString(fontRenderer, "Minecraft 1.12.2 / Forge", 4, height - 12, 0xFFE0E0E0);
        drawCenteredString(fontRenderer, "Furusato UI " + FurusatoUI.VERSION,
                width / 2, height - 12, 0xFFE0E0E0);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void moveFocus(int direction) {
        int next = focusedButton;
        do {
            next = (next + direction + buttonList.size()) % buttonList.size();
        } while (!buttonList.get(next).enabled || !buttonList.get(next).visible);
        setFocusedButton(next);
    }

    private void setFocusedButton(int index) {
        if (index < 0 || index >= buttonList.size()) return;
        focusedButton = index;
        for (int i = 0; i < buttonList.size(); i++) {
            if (buttonList.get(i) instanceof FurusatoButton) {
                ((FurusatoButton) buttonList.get(i)).setFocused(i == focusedButton);
            }
        }
    }
}
