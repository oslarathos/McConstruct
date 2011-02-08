package org.langricr.util;

import org.bukkit.block.Block;

public class WorldCoordinate extends Coordinate {
	private static final long serialVersionUID = 1L;
	
	private String _world;
	
	public WorldCoordinate( String world, int x, int y, int z) {
		super( x, y, z );
		
		_world = world;
	}

	public WorldCoordinate( Block block ) {
		this( block.getWorld().getName(), block.getX(), block.getY(), block.getZ() );
	}
	
	public String getWorld() {
		return _world;
	}
	
	public void setWorld( String world ) {
		_world = world;
	}
	
	public WorldCoordinate offset( int x, int y, int z ) {
		return offset( getWorld(), getX() + x, getY() + y, getZ() + z );
	}
	
	public WorldCoordinate offset( String world, int x, int y, int z ) {
		return new WorldCoordinate( world, getX() + x, getY() + y, getZ() + z );
	}
	
	public WorldCoordinate offset( WorldCoordinate coord ) {
		return offset( coord.getX(), coord.getY(), coord.getZ() );
	}
	
	public WorldCoordinate offset( Coordinate coord ) {
		return offset( coord.getX(), coord.getY(), coord.getZ() );
	}
	
	public boolean equals( Object o ) {
		if ( o == null ) return false;
		if ( !( o instanceof WorldCoordinate ) ) return false;

		return o.hashCode() == hashCode();
	}
	
	public int hashCode() {
		int code = 0;
		
		code += super.hashCode();
		code += getWorld().hashCode();
		
		return code;
	}
}
