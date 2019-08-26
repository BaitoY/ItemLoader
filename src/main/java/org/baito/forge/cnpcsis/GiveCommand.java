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

public class GiveCommand extends CommandBase {

    @Override
    public String getName() {
        return "givesaveditem";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "Gives a player the item specified. First parameter is the player, second is the item.";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender instanceof EntityPlayer && !PermissionAPI.hasPermission((EntityPlayer)sender, "itemloader.operator")) {
            sender.sendMessage(new TextComponentString(Main.prefix + "You do not have permission to use this command."));
            return;
        }
        if (args.length < 1) {
            sender.sendMessage(new TextComponentString(Main.prefix + "No player was given. Please input a name."));
            return;
        }
        EntityPlayer target = getEntity(server, sender, args[0], EntityPlayer.class);
        if (args.length < 2) {
            sender.sendMessage(new TextComponentString(Main.prefix + "No item was given. Please input an item."));
            return;
        }
        if (!Config.items.containsKey(args[1])) {
            sender.sendMessage(new TextComponentString(Main.prefix + "Item \"§f" + args[1] + "§6\" does not exist."));
        } else {
            SaveItem i = Config.items.get(args[1]);
            target.addItemStackToInventory(i.item.copy());
            sender.sendMessage(new TextComponentString(Main.prefix + "Gave item \"§f" + args[1] + "§6\" to " + target.getName() + "."));
        }
    }
}
