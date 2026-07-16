package com.demonicrous.furusato.ui.client.gui;

import com.demonicrous.furusato.ui.FurusatoUI;
import com.demonicrous.furusato.ui.client.gui.component.FurusatoButton;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.GuiModList;
import org.lwjgl.input.Keyboard;

/** First Furusato screen: a modernized title-menu layout on the vanilla panorama. */
public final class FurusatoMainMenu extends GuiMainMenu {
    private static final int SINGLEPLAYER = 1;
    private static final int MULTIPLAYER = 2;
    private static final int OPTIONS = 0;
    private static final int QUIT = 4;
    private static final int LANGUAGE = 5;
    private static final int MODS = 6;
    private int focusedButton;

    @Override
    public void initGui() {
        // Vanilla initializes its panorama, splash text and notification state.
        super.initGui();
        buttonList.clear();

        int buttonWidth = 204;
        int left = width / 2 - buttonWidth / 2;
        int top = height / 4 + 48;
        int gap = 24;

        buttonList.add(new FurusatoButton(SINGLEPLAYER, left, top, buttonWidth, 20,
                I18n.format("menu.singleplayer")));
        buttonList.add(new FurusatoButton(MULTIPLAYER, left, top + gap, buttonWidth, 20,
                I18n.format("menu.multiplayer")));
        buttonList.add(new FurusatoButton(MODS, left, top + gap * 2, buttonWidth, 20,
                I18n.format("fml.menu.mods")));
        buttonList.add(new FurusatoButton(OPTIONS, left, top + gap * 3, 99, 20,
                I18n.format("menu.options")));
        buttonList.add(new FurusatoButton(QUIT, left + 105, top + gap * 3, 99, 20,
                I18n.format("menu.quit")));
        buttonList.add(new FurusatoButton(LANGUAGE, left, top + gap * 4, buttonWidth, 20,
                I18n.format("options.language")));
        setFocusedButton(0);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        setFocusedButton(buttonList.indexOf(button));
        switch (button.id) {
            case SINGLEPLAYER:
                mc.displayGuiScreen(new GuiWorldSelection(this));
                break;
            case MULTIPLAYER:
                mc.displayGuiScreen(new GuiMultiplayer(this));
                break;
            case MODS:
                mc.displayGuiScreen(new GuiModList(this));
                break;
            case OPTIONS:
                mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
                break;
            case LANGUAGE:
                mc.displayGuiScreen(new GuiLanguage(this, mc.gameSettings, mc.getLanguageManager()));
                break;
            case QUIT:
                mc.shutdown();
                break;
            default:
                super.actionPerformed(button);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_TAB || keyCode == Keyboard.KEY_DOWN
                || keyCode == Keyboard.KEY_RIGHT) {
            moveFocus(1);
            return;
        }
        if (keyCode == Keyboard.KEY_UP || keyCode == Keyboard.KEY_LEFT) {
            moveFocus(-1);
            return;
        }
        if (keyCode == Keyboard.KEY_RETURN || keyCode == Keyboard.KEY_NUMPADENTER
                || keyCode == Keyboard.KEY_SPACE) {
            GuiButton button = buttonList.get(focusedButton);
            if (button.enabled && button.visible) {
                button.playPressSound(mc.getSoundHandler());
                actionPerformed(button);
            }
            return;
        }
        super.keyTyped(typedChar, keyCode);
    }

    private void moveFocus(int direction) {
        int next = focusedButton;
        do {
            next = (next + direction + buttonList.size()) % buttonList.size();
        } while (!buttonList.get(next).enabled || !buttonList.get(next).visible);
        setFocusedButton(next);
    }

    private void setFocusedButton(int index) {
        if (index < 0 || index >= buttonList.size()) {
            return;
        }
        focusedButton = index;
        for (int i = 0; i < buttonList.size(); i++) {
            if (buttonList.get(i) instanceof FurusatoButton) {
                ((FurusatoButton) buttonList.get(i)).setFocused(i == focusedButton);
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        drawCenteredString(fontRenderer, "Furusato UI " + FurusatoUI.VERSION,
                width / 2, height - 20, 0xFFBDBDBD);
    }
}
