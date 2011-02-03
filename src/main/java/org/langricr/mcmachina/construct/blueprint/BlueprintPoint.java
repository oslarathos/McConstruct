package org.langricr.mcmachina.construct.blueprint;

import org.bukkit.Material;
import org.langricr.util.Coordinate;

public class BlueprintPoint extends Coordinate {
	private static final long serialVersionUID = 1L;

	private Material _material = null;
	
	public BlueprintPoint(int x, int y, int z, Material material ) {
		super(x, y, z);
		
		_material = material;
	}
	
	public Material getMaterial() {
		return _material;
	}
}
