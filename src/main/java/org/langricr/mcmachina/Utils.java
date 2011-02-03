package org.langricr.mcmachina;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.langricr.util.Coordinate;

public class Utils {
	public static Block getBlockAt( Coordinate coord ) {
		return McMachina.getInstance().getServer().getWorlds()[ 0 ].getBlockAt( coord.getX(), coord.getY(), coord.getZ() );
	}
	
	public static Location toLocation( Block block ) {
		return new Location( block.getWorld(), block.getX(), block.getY(), block.getZ() );
	}
	
	public static Location toLocation( Coordinate coord ) {
		return toLocation( getBlockAt( coord ) );
	}
}
