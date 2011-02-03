package org.langricr.mcmachina;

import org.bukkit.block.Block;
import org.bukkit.material.Lever;
import org.langricr.util.Coordinate;

public class Utils {
	public static Block getBlockAt( Coordinate coord ) {
		return McMachina.getInstance().getServer().getWorlds()[ 0 ].getBlockAt( coord.getX(), coord.getY(), coord.getZ() );
	}
	
	public static boolean isLeverPowered( Lever lever ) {
		return lever.getData() / 8 == 1;
	}
	
	public static void toggleLever( Lever lever ) {
		lever.setData( ( byte ) ( lever.getData() ^ 8 ) );
	}
}
