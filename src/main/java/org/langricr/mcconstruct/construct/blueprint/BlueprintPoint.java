package org.langricr.mcconstruct.construct.blueprint;

import org.bukkit.Material;
import org.langricr.util.PolarCoordinate;

public class BlueprintPoint extends PolarCoordinate {
	private static final long serialVersionUID = 1L;

	private Material _material = null;
	
	public BlueprintPoint( int x, int y, int z, Material material ) {
		super( x, y, z );
		
		_material = material;
	}
	
	public BlueprintPoint( PolarCoordinate point, Material material ) {
		this( point.getX(), point.getY(), point.getZ(), material );
	}
	
	public Material getMaterial() {
		return _material;
	}
	
	public String toString() {
		return "BlueprintPoint( " + getX() + "," + getY() + "," + getZ() + "," + _material.name() + ")";
	}
	
	public boolean equals( Object o ) {
		if ( o == null ) return false;
		if ( !( o instanceof BlueprintPoint ) ) return false;
		return o.hashCode() == hashCode();
	}
	
	public int hashCode() {
		int code = 0;
		
		code += super.hashCode();
		code += getMaterial().hashCode();
		
		return code;
	}
}
