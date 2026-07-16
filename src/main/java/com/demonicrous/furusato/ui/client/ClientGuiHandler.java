package com.demonicrous.furusato.ui.client;

import com.demonicrous.furusato.ui.client.gui.FurusatoMainMenu;
import com.demonicrous.furusato.ui.config.FurusatoUIConfig;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class ClientGuiHandler {
    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if (FurusatoUIConfig.isModernMainMenuEnabled()
                && event.getGui() instanceof GuiMainMenu
                && !(event.getGui() instanceof FurusatoMainMenu)) {
            event.setGui(new FurusatoMainMenu());
        }
    }
}
