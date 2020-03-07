package com.jacksonjude.DisableCraft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class DisableCraftCompleter implements TabCompleter {
	private final DisableCraftPlugin plugin;
	
	public DisableCraftCompleter(DisableCraftPlugin plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		switch (args.length)
		{
		case 1:
			List<String> subcommands = new ArrayList<String>(Arrays.asList("add", "remove", "reload"));
			
			for (int i=subcommands.size()-1; i >= 0; i--)
				if (args.length > 0 && args[0] != "" && !subcommands.get(i).startsWith(args[0]) && !subcommands.get(i).contains(args[0]))
					subcommands.remove(i);
			
			return subcommands;
		case 2:
			switch (args[0])
			{
			case "add":
				List<Material> materials = Arrays.asList(Material.values());
				List<String> materialStrings = new ArrayList<String>();
				for (int i=0; i < materials.size(); i++)
				{
					String materialString = materials.get(i).toString();
					
					if (args.length < 2 || materialString.startsWith(args[1].toUpperCase()) || materialString.contains(args[1].toUpperCase()))
						materialStrings.add(materialString);
				}
				
				for (int j=0; j < plugin.getDisabledItems().size(); j++)
				{
					if (!materialStrings.contains(plugin.getDisabledItems().get(j))) continue;
					materialStrings.remove(plugin.getDisabledItems().get(j));
				}
				
				return materialStrings;
			case "remove":
				List<String> disabledMaterialStrings = new ArrayList<String>();
				for (int i=0; i < plugin.getDisabledItems().size(); i++)
				{
					String materialString = plugin.getDisabledItems().get(i).toString();
					
					if (args.length < 2 || materialString.startsWith(args[1].toUpperCase()) || materialString.contains(args[1].toUpperCase()))
						disabledMaterialStrings.add(materialString);
				}
				
				return disabledMaterialStrings;
			}
		}
		
		return null;
	}
	
}
