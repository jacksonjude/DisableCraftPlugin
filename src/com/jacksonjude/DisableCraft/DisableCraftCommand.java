package com.jacksonjude.DisableCraft;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DisableCraftCommand implements CommandExecutor
{
	private final DisableCraftPlugin plugin;
	
	public DisableCraftCommand(DisableCraftPlugin plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (!sender.hasPermission(DisableCraftPlugin.ADMIN_PERMISSION))
		{
			sender.sendMessage(ChatColor.RED + "You cannot edit DisableCraft config");
			return true;
		}
		
		if (args.length == 0)
		{
			sender.sendMessage(ChatColor.GOLD + "/disablecraft reload" + ChatColor.GRAY + " - reloads configuration" + "\n" + ChatColor.GOLD + "/disablecraft <add | remove> <item>" + ChatColor.GRAY + " - edits configuration");
			return true;
		}
		
		switch (args[0].toLowerCase())
		{
		case "add":
			if (args.length == 1)
			{
				sender.sendMessage(ChatColor.GOLD + "/disablecraft add <item>" + ChatColor.GRAY + " - adds item to disabled list");
				return true;
			}
			
			Material itemToAdd = Material.getMaterial(args[1].toUpperCase());
			if (itemToAdd == null)
			{
				sender.sendMessage(ChatColor.RED + "Item " + args[1].toUpperCase() + " not found!");
				return true;
			}
			
			if (plugin.getDisabledItems().contains(args[1].toUpperCase()))
			{
				sender.sendMessage(ChatColor.RED + "Item " + args[1].toUpperCase() + " already disabled!");
				return true;
			}
			
			plugin.getDisabledItems().add(args[1].toUpperCase());
			plugin.saveDisabledItems();
			sender.sendMessage(ChatColor.GREEN + args[1].toUpperCase() + " crafting disabled");
			
			return true;
		case "remove":
			if (args.length == 1)
			{
				sender.sendMessage(ChatColor.GOLD + "/disablecraft remove <item>" + ChatColor.GRAY + " - removes item from disabled list");
				return true;
			}
			
			Material itemToRemove = Material.getMaterial(args[1].toUpperCase());
			if (itemToRemove == null)
			{
				sender.sendMessage(ChatColor.RED + "Item " + args[1].toUpperCase() + " not found!");
				return true;
			}
			
			if (!plugin.getDisabledItems().contains(args[1].toUpperCase()))
			{
				sender.sendMessage(ChatColor.RED + "Item " + args[1].toUpperCase() + " not disabled!");
				return true;
			}
			
			plugin.getDisabledItems().remove(args[1].toUpperCase());
			plugin.saveDisabledItems();
			sender.sendMessage(ChatColor.GREEN + args[1].toUpperCase() + " crafing re-enabled");
			
			return true;			
		case "reload":
			plugin.reloadConfig();
			plugin.loadDisabledItems();
			sender.sendMessage(ChatColor.GREEN + "DisableCraft config reloaded");
			
			return true;
		}
		
		return false;
	}
}
