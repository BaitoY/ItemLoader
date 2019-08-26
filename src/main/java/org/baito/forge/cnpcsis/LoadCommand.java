package org.baito.forge.cnpcsis;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.server.permission.PermissionAPI;
import org.baito.forge.cnpcsis.config.Config;
import org.baito.forge.cnpcsis.config.SaveItem;

public class LoadCommand extends CommandBase {

    @Override
    public String getName() {
        return "loaditem";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "Loads the item specified.";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender instanceof EntityPlayer && !PermissionAPI.hasPermission((EntityPlayer)sender, "itemloader.operator")) {
            sender.sendMessage(new TextComponentString(Main.prefix + "You do not have permission to use this command."));
            return;
        }
        if (sender instanceof EntityPlayer) {
            if (args.length < 1) {
                sender.sendMessage(new TextComponentString(Main.prefix + "No name was given. Please input a name."));
                return;
            }
            if (!Config.items.containsKey(args[0])) {
                sender.sendMessage(new TextComponentString(Main.prefix + "Item \"§f" + args[0] + "§6\" does not exist."));
            } else {
                SaveItem i = Config.items.get(args[0]);
                ((EntityPlayer) sender).addItemStackToInventory(i.item.copy());
                if (args.length > 1 && args[1].equals("-s")) {
                    return;
                } else {
                    sender.sendMessage(new TextComponentString(Main.prefix + "Loaded \"§f" + args[0] + "§6\"."));
                    return;
                }
            }
        }
    }
}
