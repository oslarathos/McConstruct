package org.langricr.mcconstruct.listeners;

import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;
import org.langricr.mcconstruct.McConstruct;

import com.nijiko.permissions.PermissionHandler;

public class MMPlayerListener extends PlayerListener {
	public void onPlayerCommand( PlayerChatEvent event ) {
		PermissionHandler perms = McConstruct.getInstance().getPermissions();
		
		if ( perms != null && !( perms.has( event.getPlayer(), "mcconstruct.command" ) ) )
			return;
		
		String[] split = event.getMessage().split( " " );
		
		if ( split[ 0 ].equalsIgnoreCase( "/construct" ) ) {
			if ( split.length == 1 || split[ 1 ].equalsIgnoreCase( "help" ) ) {
				event.getPlayer().sendMessage( "McConstruct Help File" );
				event.getPlayer().sendMessage( "/construct reload - Reload the plugin's assets." );
				
				return;
			}
			
			if ( split[ 1 ].equalsIgnoreCase( "reload" ) ) {
				McConstruct.getInstance().reload();
				
				return;
			}
			
			event.getPlayer().sendMessage( "McConsturct: Command not recognized." );
		}
    }
}
