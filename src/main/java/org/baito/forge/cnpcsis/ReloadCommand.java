package org.baito.forge.cnpcsis;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.server.permission.PermissionAPI;
import org.baito.forge.cnpcsis.config.Config;

public class ReloadCommand extends CommandBase {

    @Override
    public String getName() {
        return "reloaditemloader";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "Reloads all items.";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender instanceof EntityPlayer && !PermissionAPI.hasPermission((EntityPlayer)sender, "itemloader.operator")) {
            sender.sendMessage(new TextComponentString(Main.prefix + "You do not have permission to use this command."));
            return;
        }
        Config.load();
        sender.sendMessage(new TextComponentString(Main.prefix + "Reloaded the config."));
    }
}
