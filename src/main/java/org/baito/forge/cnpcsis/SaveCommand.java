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

public class SaveCommand extends CommandBase {

    @Override
    public String getName() {
        return "saveitem";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "Saves the item's NBT and ID to a file, and can be loaded again.";
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
            if (Config.items.containsKey(args[0])) {
                if (args.length > 1 && args[1].equals("-o")) {
                    SaveItem i = new SaveItem((EntityPlayer)sender, args[0]);
                    Config.createFile(i);
                    Config.load();
                    sender.sendMessage(new TextComponentString(Main.prefix + "Saved \"§f" + args[0] + "§6\"."));
                } else {
                    sender.sendMessage(new TextComponentString(Main.prefix + "An item with that name already exists."));
                }
            } else {
                SaveItem i = new SaveItem((EntityPlayer)sender, args[0]);
                Config.createFile(i);
                Config.load();
                sender.sendMessage(new TextComponentString(Main.prefix + "Saved \"§f" + args[0] + "§6\"."));
            }
        }
    }
}
