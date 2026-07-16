package com.demonicrous.furusato.ui;

import com.demonicrous.furusato.ui.client.ClientGuiHandler;
import com.demonicrous.furusato.ui.config.FurusatoUIConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = FurusatoUI.MOD_ID,
        name = FurusatoUI.NAME,
        version = FurusatoUI.VERSION,
        acceptedMinecraftVersions = "[1.12.2]",
        dependencies = "required-after:forge@[14.23.5.2847,);required-after:furusatocore@[0.1.0,)",
        clientSideOnly = true
)
public final class FurusatoUI {
    public static final String MOD_ID = "furusatoui";
    public static final String NAME = "Furusato UI";
    public static final String VERSION = "@VERSION@";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Logger logger = event.getModLog();
        FurusatoUIConfig.load(event.getSuggestedConfigurationFile());
        MinecraftForge.EVENT_BUS.register(new ClientGuiHandler());
        logger.info("Furusato UI {} initialized", VERSION);
    }
}
