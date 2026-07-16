package com.demonicrous.furusato.ui.config;

import java.io.File;
import net.minecraftforge.common.config.Configuration;

public final class FurusatoUIConfig {
    private static boolean modernMainMenu = true;

    private FurusatoUIConfig() {
    }

    public static void load(File file) {
        Configuration configuration = new Configuration(file);
        configuration.load();
        modernMainMenu = configuration.getBoolean(
                "modernMainMenu",
                Configuration.CATEGORY_CLIENT,
                true,
                "Replace the Minecraft 1.12.2 title screen with the Furusato UI layout."
        );
        if (configuration.hasChanged()) {
            configuration.save();
        }
    }

    public static boolean isModernMainMenuEnabled() {
        return modernMainMenu;
    }
}
