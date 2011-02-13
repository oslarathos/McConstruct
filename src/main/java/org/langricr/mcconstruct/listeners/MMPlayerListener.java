package org.langricr.mcconstruct.listeners;

import org.bukkit.Material;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.inventory.ItemStack;
import org.langricr.mcconstruct.McConstruct;

public class MMPlayerListener extends PlayerListener {
	public void onPlayerCommand( PlayerChatEvent event ) {
		String[] split = event.getMessage().split( " " );
		
		if ( split[ 0 ].equalsIgnoreCase( "/construct" ) ) {
			if ( McConstruct.permissions != null && !( McConstruct.permissions.has( event.getPlayer(), "construct.command" ) ) )
				return;
			
			if ( split.length == 1 || split[ 1 ].equalsIgnoreCase( "help" ) ) {
				event.getPlayer().sendMessage( "McConstruct Help File" );
				event.getPlayer().sendMessage( "/construct reload - Reload the plugin's assets." );
				
				return;
			}
			
			if ( split[ 1 ].equalsIgnoreCase( "reload" ) ) {
				McConstruct.getInstance().reload();
				
				return;
			}
			
			if ( split[ 1 ].equalsIgnoreCase( "give" ) ) {
				if ( split.length != 4 )
					return;
				
				event.getPlayer().getInventory().addItem( new ItemStack( Material.valueOf( split[ 2 ] ), Integer.parseInt( split[ 3 ] ) ) );
				
				return;
			}
			
			event.getPlayer().sendMessage( "McConsturct: Command not recognized." );
		}
    }
}
