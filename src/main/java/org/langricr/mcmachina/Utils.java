package org.langricr.mcmachina;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.langricr.util.WorldCoordinate;

public class Utils {
	public static Block getBlockAt( WorldCoordinate coord ) {
		World world = getWorld( coord.getWorld() );
		
		if ( world == null )
			return null;
		
		return world.getBlockAt( coord.getX(), coord.getY(), coord.getZ() );
	}
	
	public static World getWorld( String name ) {
		for ( World world : McMachina.getInstance().getServer().getWorlds() ) {
			if ( world.getName().equals( name ) ) 
				return world;
		}
		
		return null;
	}
	
	public static Location toLocation( Block block ) {
		return new Location( block.getWorld(), block.getX(), block.getY(), block.getZ() );
	}
	
	public static Location toLocation( WorldCoordinate coord ) {
		return toLocation( getBlockAt( coord ) );
	}
}
