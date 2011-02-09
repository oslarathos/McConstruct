package org.langricr.mcmachina;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.langricr.util.WorldCoordinate;

public class Utils {
	public static Block getBlockAt( WorldCoordinate coord ) {
		World world = getWorld( coord.getWorldName(), null );
		
		if ( world == null )
			return null;
		
		return world.getBlockAt( coord.getX(), coord.getY(), coord.getZ() );
	}
	
	public static World getWorld( String name, Environment style ) {
		for ( World world : McMachina.getInstance().getServer().getWorlds() ) {
			if ( world.getName().equals( name ) ) 
				return world;
		}
		
		if ( style != null )
			return McMachina.getInstance().getServer().createWorld( name, style );
		
		return null;
	}
	
	public static Location toLocation( Block block ) {
		return new Location( block.getWorld(), block.getX(), block.getY(), block.getZ() );
	}
	
	public static Location toLocation( WorldCoordinate coord ) {
		return toLocation( getBlockAt( coord ) );
	}
	
	public static Location randomLocation( Location loc, int range ) {
		Random rand = new Random();
		
		int x = ( loc.getBlockX() - ( range / 2 ) ) + rand.nextInt( range );
		int z = ( loc.getBlockZ() - ( range / 2 ) ) + rand.nextInt( range );
		
		return new Location( loc.getWorld(), x, loc.getWorld().getHighestBlockYAt( x, z ), z );
	}
	
	public static List< Player > getPlayersInRange( WorldCoordinate coord, double range ) {
		List< Player > players = new ArrayList< Player >();
		
		for ( Player player : McMachina.getInstance().getServer().getOnlinePlayers() ) {
			if ( coord.distanceTo( new WorldCoordinate( player.getLocation() ) ) <= range )
				players.add( player );
		}
		
		return players;
	}
}
