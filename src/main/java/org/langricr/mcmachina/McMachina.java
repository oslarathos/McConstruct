package org.langricr.mcmachina;

import java.io.File;

import org.bukkit.Server;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.langricr.mcmachina.construct.ConstructLoader;
import org.langricr.mcmachina.construct.ConstructManager;
import org.langricr.mcmachina.construct.blueprint.BlueprintManager;
import org.langricr.mcmachina.listeners.MMBlockListener;

public class McMachina extends JavaPlugin {
	private static McMachina instance = null;
	
	public static McMachina getInstance() {
		return instance;
	}
	
	private MMBlockListener blockListener = new MMBlockListener();
	
	public McMachina(PluginLoader pluginLoader, Server server,
			PluginDescriptionFile desc, File folder, File plugin,
			ClassLoader cLoader) {
		super(pluginLoader, server, desc, folder, plugin, cLoader);

		if ( !( folder.exists() ) )
			folder.mkdir();
		
		instance = this;
	}
	
	public void onDisable() {
		ConstructManager.getInstance().saveAllConstructs();
	}

	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		
		pm.registerEvent( Type.BLOCK_PLACED, blockListener, Priority.High, this );
		pm.registerEvent( Type.BLOCK_DAMAGED, blockListener, Priority.High, this );
		pm.registerEvent( Type.BLOCK_RIGHTCLICKED, blockListener, Priority.High, this );
		
		System.out.println( "\nDirectories" );
		System.out.println( getDataFolder().getPath() );
		System.out.println( ConstructLoader.getInstance().getFolder().getPath() );
		System.out.println( BlueprintManager.getInstance().getFolder().getPath() );
		System.out.println( ConstructManager.getInstance().getFolder().getPath() );
		System.out.println( "\nStartup" );
		ConstructLoader.getInstance().reloadClasses();
		BlueprintManager.getInstance().reloadBlueprints();
		ConstructManager.getInstance().loadAllConstructs();
	}
}
