package org.langricr.mcmachina;

import java.io.File;

import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;

public class McMachina extends JavaPlugin {
	private static McMachina instance = null;
	
	public static McMachina getInstance() {
		return instance;
	}
	
	public McMachina(PluginLoader pluginLoader, Server server,
			PluginDescriptionFile desc, File folder, File plugin,
			ClassLoader cLoader) {
		super(pluginLoader, server, desc, folder, plugin, cLoader);

		if ( !( folder.exists() ) )
			folder.mkdir();
		
		instance = this;
	}
	
	public void onDisable() {
		// TODO Auto-generated method stub
		
	}

	public void onEnable() {
		// TODO Auto-generated method stub
		
	}
}
