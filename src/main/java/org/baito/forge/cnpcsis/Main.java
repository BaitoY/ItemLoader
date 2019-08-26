package org.baito.forge.cnpcsis;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.PermissionAPI;
import org.baito.forge.cnpcsis.config.Config;

@Mod(
        modid = "itemloader",
        name = "Item Loader",
        serverSideOnly = true,
        acceptableRemoteVersions = "*"
)

public class Main {

    public static String prefix = "§f[§dItemLoader§f] §6";

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent e) {
        e.registerServerCommand(new SaveCommand());
        e.registerServerCommand(new LoadCommand());
        e.registerServerCommand(new GiveCommand());
        e.registerServerCommand(new ReloadCommand());
        Config.setup();
        Config.load();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        Config.conf = e.getModConfigurationDirectory().toPath();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        PermissionAPI.registerNode("itemloader.operator", DefaultPermissionLevel.OP, "The node to use all commands.");
    }
}
