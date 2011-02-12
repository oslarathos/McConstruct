package org.langricr.mcconstruct;

import java.io.File;

import org.bukkit.Server;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.langricr.mcconstruct.construct.ConstructLoader;
import org.langricr.mcconstruct.construct.ConstructManager;
import org.langricr.mcconstruct.construct.blueprint.BlueprintManager;
import org.langricr.mcconstruct.listeners.MMBlockListener;

public class McConstruct extends JavaPlugin {
	private static McConstruct instance = null;
	public static final boolean debugging = false;
	private Thread hook = null;
	
	public static McConstruct getInstance() {
		return instance;
	}
	
	private MMBlockListener blockListener = new MMBlockListener();
	
	public McConstruct(PluginLoader pluginLoader, Server server,
			PluginDescriptionFile desc, File folder, File plugin,
			ClassLoader cLoader) {
		super(pluginLoader, server, desc, folder, plugin, cLoader);

		if ( !( folder.exists() ) )
			folder.mkdir();
		
		instance = this;
		
		hook = new Thread( 
			new Runnable() {
				public void run() {
					System.out.println( "SHUTDOWN: Saving all constructs!" );
					ConstructManager.getInstance().saveAllConstructs();
				}
			}
		);
	}
	
	public void onDisable() {
		Runtime.getRuntime().removeShutdownHook( hook );
		
		ConstructManager.getInstance().saveAllConstructs();
	}

	public void onEnable() {
		System.out.println( "McMachina Version " + getDescription().getVersion() );
		
		System.out.println( "Registering Shutdown Hook" );
		Runtime.getRuntime().addShutdownHook( hook );

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
		ConstructLoader.getInstance().reloadAllClasses();
		BlueprintManager.getInstance().reloadBlueprints();
		ConstructManager.getInstance().reloadAllConstructs();
		
		
	}
}
