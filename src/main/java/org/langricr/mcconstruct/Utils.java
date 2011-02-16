package org.langricr.mcconstruct;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.langricr.util.WorldCoordinate;

public class Utils {
	/**
	 * Attempts to retrieve a block from the server based off the given coordinate.
	 * @param coord The coordinate
	 * @return The block matching that coordinate.
	 */
	public static Block getBlockAt( WorldCoordinate coord ) {
		World world = getWorld( coord.getWorldName() );
		
		if ( world == null )
			return null;
		
		return world.getBlockAt( coord.getX(), coord.getY(), coord.getZ() );
	}
	
	/**
	 * Attempts to retrieve a world with the specified name.
	 * @param name The name of the world to be retrieved.
	 * @return The world with the corresponding name, or null if there is no world or no name was given.
	 */
	public static World getWorld( String name ) {
		if ( name == null ) return null;
		
		for ( World world : McConstruct.getInstance().getServer().getWorlds() ) {
			if ( world.getName().equals( name ) ) 
				return world;
		}
		
		return null;
	}
	
	/**
	 * Attempts to create a world with the specified name.
	 * @param name The name of the world to be created
	 * @param style The type of world to be created.
	 * @return The new world, the old world if the name is already in use, or null if either parameter is null.
	 */
	public static World createWorld( String name, Environment style ) {
		if ( name == null || style == null )
			return null;
		
		World world = getWorld( name );
		
		if ( world != null )
			return world;
		
		return McConstruct.getInstance().getServer().createWorld( name, style );
	}
	
	/**
	 * Attempts to convert a block to a location, this is inaccurate because a location is precise and a block is not.
	 * @param block The block to be converted
	 * @return The Location form of that block.
	 */
	public static Location toLocation( Block block ) {
		return new Location( block.getWorld(), block.getX(), block.getY(), block.getZ() );
	}
	
	/**
	 * Attempts to convert a WorldCoordinate to a Location.
	 * @param coord The coordinate
	 * @return The Location form of the coordinate.
	 */
	public static Location toLocation( WorldCoordinate coord ) {
		return toLocation( getBlockAt( coord ) );
	}
	
	/**
	 * Used to create a random location around the given location within a specified range.
	 * @param loc The location to check
	 * @param range The range of the randomization
	 * @return The randomized location.
	 */
	public static Location randomLocation( Location loc, int range ) {
		Random rand = new Random();
		
		int x = ( loc.getBlockX() - ( range / 2 ) ) + rand.nextInt( range );
		int z = ( loc.getBlockZ() - ( range / 2 ) ) + rand.nextInt( range );
		
		return new Location( loc.getWorld(), x, loc.getY(), z );
	}
	
	/**
	 * Used to retrieve all players within the specified range of a coordinate.
	 * @param coord The coordinate to check
	 * @param range The range to get players around it in
	 * @return A list of players within range.
	 */
	public static List< Player > getPlayersInRange( WorldCoordinate coord, double range ) {
		List< Player > players = new ArrayList< Player >();
		
		for ( Player player : McConstruct.getInstance().getServer().getOnlinePlayers() ) {
			if ( coord.distanceTo( new WorldCoordinate( player.getLocation() ) ) <= range )
				players.add( player );
		}
		
		return players;
	}
	
	public static void clearSign( Sign sign ) {
		sign.setLine( 0, "" );
		sign.setLine( 1, "" );
		sign.setLine( 2, "" );
		sign.setLine( 3, "" );
	}
}
