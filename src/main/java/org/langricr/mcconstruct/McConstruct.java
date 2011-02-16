package org.langricr.mcconstruct;

import java.io.File;

import org.bukkit.Server;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.langricr.mcconstruct.construct.ConstructLoader;
import org.langricr.mcconstruct.construct.ConstructManager;
import org.langricr.mcconstruct.construct.blueprint.BlueprintManager;
import org.langricr.mcconstruct.listeners.MMBlockListener;
import org.langricr.mcconstruct.listeners.MMPlayerListener;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class McConstruct extends JavaPlugin {
	private static McConstruct instance = null;
	
	/**
	 * Retrieves the currently running instance of McConstruct.
	 * @return The current enabled instance of McConstruct
	 */
	public static McConstruct getInstance() {
		return instance;
	}
	
	private MMBlockListener blockListener = new MMBlockListener();
	private MMPlayerListener playerListener = new MMPlayerListener();
	private Thread shutdownHook = null;
	private PermissionHandler permissionHandler = null;
	
	public McConstruct(PluginLoader pluginLoader, Server server,
			PluginDescriptionFile desc, File folder, File plugin,
			ClassLoader cLoader) {
		super(pluginLoader, server, desc, folder, plugin, cLoader);

		if ( !( folder.exists() ) )
			folder.mkdir();
		
		instance = this;
		
		shutdownHook = new Thread( 
			new Runnable() {
				public void run() {
					System.out.println( "SHUTDOWN: Unloading all constructs!" );
					ConstructManager.getInstance().unloadAllConstructs();
				}
			}
		);
	}
	
	public void onDisable() {
		Runtime.getRuntime().removeShutdownHook( shutdownHook );
		
		ConstructManager.getInstance().saveAllConstructs();
	}

	public void onEnable() {
		System.out.println( "McMachina Version " + getDescription().getVersion() );
		
		System.out.println( "Registering Shutdown Hook" );
		Runtime.getRuntime().addShutdownHook( shutdownHook );

		PluginManager pm = getServer().getPluginManager();
		
		pm.registerEvent( Type.BLOCK_PLACED, blockListener, Priority.High, this );
		pm.registerEvent( Type.BLOCK_DAMAGED, blockListener, Priority.High, this );
		pm.registerEvent( Type.BLOCK_RIGHTCLICKED, blockListener, Priority.High, this );
		
		pm.registerEvent( Type.PLAYER_COMMAND, playerListener, Priority.Low, this );
		
		System.out.println( "\nDirectories" );
		System.out.println( getDataFolder().getPath() );
		System.out.println( ConstructLoader.getInstance().getFolder().getPath() );
		System.out.println( BlueprintManager.getInstance().getFolder().getPath() );
		System.out.println( ConstructManager.getInstance().getFolder().getPath() );
		System.out.println( "\nStartup" );
		
		setupPermissions();
		
		reload();
	}
	
	/**
	 * Attempts to reload all plugin assets: Classes, Blueprints and saved Constructs.
	 */
	public void reload() {
		ConstructLoader.getInstance().reloadAllClasses();
		BlueprintManager.getInstance().reloadBlueprints();
		ConstructManager.getInstance().reloadAllConstructs();
	}
	
	/**
	 * Attempts to enable support for the Permissions plugin.
	 */
	public void setupPermissions() {
		Plugin permissionsPlugin = getServer().getPluginManager().getPlugin( "Permissions" );
		
		if ( permissionsPlugin == null ) {
			System.out.println( "Permissions not enabled, free reign." );
		} else {
			permissionHandler = ( ( Permissions ) permissionsPlugin ).getHandler();
		}
	}
	
	/**
	 * Attempts to retrieve the handler of the Permissions plugin, returns null if not enabled.
	 * @return The PermissionHandler of the Permissions plugin.
	 */
	public PermissionHandler getPermissions() {
		return permissionHandler;
	}
}
