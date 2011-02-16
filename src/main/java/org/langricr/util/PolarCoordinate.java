package org.langricr.util;

import org.bukkit.block.Block;

public class PolarCoordinate extends Coordinate {
	private static final long serialVersionUID = 1L;

	public PolarCoordinate(Block block) {
		super(block);
	}

	public PolarCoordinate(Coordinate coord) {
		super(coord);
	}

	public PolarCoordinate(int x, int y, int z) {
		super(x, y, z);
	}

	public PolarCoordinate rotate( Rotation rot ) {
		int x = getX(), y = getY(), z = getZ();
		
		switch ( rot ) {
			case None:
				return this;
			case Left:
				return new PolarCoordinate( z, y, -x );
			case Right:
				return new PolarCoordinate( -z, y, x );
			case Inverse:
				return new PolarCoordinate( -x, y, -z );
			default:
				return null;
		}
	}
	
	public enum Rotation {
		None,
		Left,
		Right,
		Inverse
	}
	
	public String toString() {
		return super.toString();
	}
	
	public boolean equals( Object o ) {
		if ( o == null ) return false;
		if ( !( PolarCoordinate.class.isAssignableFrom( o.getClass() ) ) ) return false;
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
