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

/** First Furusato screen: a modernized title-menu layout on the vanilla panorama. */
public final class FurusatoMainMenu extends GuiMainMenu {
    private static final int SINGLEPLAYER = 1;
    private static final int MULTIPLAYER = 2;
    private static final int OPTIONS = 0;
    private static final int QUIT = 4;
    private static final int LANGUAGE = 5;
    private static final int MODS = 6;

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
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
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
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        drawCenteredString(fontRenderer, "Furusato UI " + FurusatoUI.VERSION,
                width / 2, height - 20, 0xFFBDBDBD);
    }
}
