package com.jacksonjude.DisableCraft;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class DisableCraftPlugin extends JavaPlugin implements Listener 
{
	public static final String BYPASS_PERMISSION = "disablecraft.bypass";
	public static final String ADMIN_PERMISSION = "disablecraft.admin";
	public static final String ADMIN_COMMAND = "disablecraft";
	public static final String DISABLED_ITEMS_KEY = "items";
	
	private List<String> disabledItems;
	private FileConfiguration fileConfig;
	
	@Override
    public void onEnable()
	{
		saveDefaultConfig();
		loadDisabledItems();
		
		getCommand(ADMIN_COMMAND).setExecutor(new DisableCraftCommand(this));
		getCommand(ADMIN_COMMAND).setTabCompleter(new DisableCraftCompleter(this));
		this.getServer().getPluginManager().registerEvents(this, this);
    }
    
    public void loadDisabledItems()
    {
    	fileConfig = getConfig();
    	disabledItems = fileConfig.getStringList(DISABLED_ITEMS_KEY);
    }
    
    public void saveDisabledItems()
    {
    	fileConfig.set(DISABLED_ITEMS_KEY, disabledItems);
    	saveConfig();
    }
    
    public List<String> getDisabledItems()
    {
    	return disabledItems;
    }
    
    @EventHandler
    public void craftItem(PrepareItemCraftEvent e)
    {
    	if (e.getRecipe() == null || e.getRecipe().getResult() == null) return;
    	
        Material itemType = e.getRecipe().getResult().getType();
        if (disabledItems.contains(itemType.toString()))
        {
            for (HumanEntity human : e.getViewers())
            {
            	if (!human.hasPermission(BYPASS_PERMISSION))
            	{
                	human.sendMessage(ChatColor.RED + "You cannot craft this!");
            		e.getInventory().setResult(new ItemStack(Material.AIR));
            	}
            }
        }
    }
}
