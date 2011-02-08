package org.langricr.util;

import java.io.Serializable;

import org.bukkit.block.Block;

public class Coordinate implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	
	private int _x;
	private int _y;
	private int _z;
	
	public Coordinate( int x, int y, int z ) {
		_x = x;
		_y = y;
		_z = z;
	}
	
	public Coordinate( Block block ) {
		this( block.getX(), block.getY(), block.getZ() );
	}
	
	public Coordinate( Coordinate coord ) {
		_x = coord.getX();
		_y = coord.getY();
		_z = coord.getZ();
	}
	
	public Coordinate offset( int x, int y, int z ) {
		return new Coordinate( getX() + x, getY() + y, getZ() + z );
	}
	
	public Coordinate offset( Coordinate coord ) {
		return offset( coord.getX(), coord.getY(), coord.getZ() );
	}
	
	public Coordinate getOffset( Coordinate coord ) {
		return new Coordinate( getX() - coord.getX(), getY() - coord.getY(), getZ() - coord.getZ() );
	}
	
	
	public int getX() {
		return _x;
	}
	
	public void setX( int x ) {
		_x = x;
	}
	
	public int getY() {
		return _y;
	}
	
	public void setY( int y ) {
		_y = y;
	}
	
	public int getZ() {
		return _z;
	}
	
	public void setZ( int z ) {
		_z = z;
	}
	
	public double distanceTo( int x, int y, int z ) {
		return Math.sqrt( Math.pow( Math.abs( x - getX() ), 2.0D ) + Math.pow( Math.abs( y - getY() ), 2.0D ) + Math.pow( Math.abs( z - getZ() ), 2.0D ) );
	}
	
	public double distanceTo( Coordinate coord ) {
		return distanceTo( coord.getX(), coord.getY(), coord.getZ() );
	}
	
	public boolean equals( Object o ) {
		if ( o == null ) return false;
		if ( !( Coordinate.class.isAssignableFrom( o.getClass() ) ) ) return false;
		if ( hashCode() != o.hashCode() ) return false;
		
		return true;
	}
	
	public int hashCode() {
		int code = 7;
		code += 7 * getX();
		code += 8 * getY();
		code += 9 * getZ();
		return code;
	}
}
