package org.langricr.mcmachina.construct.blueprint;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.langricr.mcmachina.Utils;
import org.langricr.util.Coordinate;

public class Blueprint {
	private final File file;
	private List< BlueprintPoint > points = new ArrayList< BlueprintPoint >();
	private String classname = null;
	
	private Blueprint( File file, String className ) {
		this.file = file;
		this.classname = className;
	}
	
	public final File getFile() {
		return file;
	}
	
	public String getClassname() {
		return classname;
	}
	
	public void addPoint( BlueprintPoint point ) {
		if ( !( points.contains( point ) ) )
			points.add( point );
	}
	
	public BlueprintPoint getPoint( Coordinate core, Block block ) {
		for ( BlueprintPoint point : points ) {
			if ( point.equals( core.getOffset( new Coordinate( block ) ) ) )
				return point;
		}
		
		return null;
	}
	
	public List< BlueprintPoint > getPoints() {
		return points;
	}
	
	public boolean isValid( Coordinate coord ) {
		for ( BlueprintPoint point : points ) {
			Block block = Utils.getBlockAt( coord.offset( point ) );
			
			if ( point.getMaterial() != null && !( point.getMaterial().equals( block.getType() ) ) ) {
				System.out.println( "FAILED MATCH: " + block.getType().name() + " to " + point.toString() );
				
				return false;
			}
		}
		
		return true;
	}
	
	public static Blueprint loadBlueprint( File file ) {
		try {
			
			FileReader fr = new FileReader( file );
			BufferedReader br = new BufferedReader( fr );
			
			String line = br.readLine();
			
			String classname = line.substring( line.indexOf( "=" ) + 1 );
			
			Blueprint blueprint = new Blueprint( file, classname );
			
			while ( ( line = br.readLine() ) != null ) {
				if ( !( line.startsWith( ":point(" ) ) && !( line.endsWith( ");" ) ) )
					continue;
				
				String[] contents = line.substring( line.indexOf( "(" ) + 1, line.indexOf( ")" ) ).split( "," );
				
				blueprint.addPoint(
					new BlueprintPoint(
						Integer.parseInt( contents[ 0 ].trim() ),
						Integer.parseInt( contents[ 1 ].trim() ),
						Integer.parseInt( contents[ 2 ].trim() ),
						contents[ 3 ].trim().equalsIgnoreCase( "*" ) ? null : Material.getMaterial( contents[ 3 ].trim() )
					)
				);
			}
			
			return blueprint;
		} catch ( Exception e ) {
			e.printStackTrace();
			
			return null;
		}
	}
}
