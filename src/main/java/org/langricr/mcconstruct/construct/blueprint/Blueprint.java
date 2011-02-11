package org.langricr.mcconstruct.construct.blueprint;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.langricr.mcconstruct.Utils;
import org.langricr.util.Coordinate;
import org.langricr.util.WorldCoordinate;

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
	
	public boolean isValid( WorldCoordinate core ) {
		// Cycling through all points
		for ( BlueprintPoint point : points ) {
			// Getting the block.
			Block block = Utils.getBlockAt( core.offset( point ) );
			
			if ( point.getMaterial() != null && !( point.getMaterial().equals( block.getType() ) ) )
				return false;
		}
		
		return true;
	}
	
	public static Blueprint loadBlueprint( File file ) {
		try {
			// Opening the file readers
			FileReader fr = new FileReader( file );
			BufferedReader br = new BufferedReader( fr );
			
			// Getting the first line
			String line = br.readLine();
			
			// Determining the class
			String classname = line.substring( line.indexOf( "=" ) + 1 );
			
			// Creating a new blueprint
			Blueprint blueprint = new Blueprint( file, classname );
			
			// Reading the blueprint
			while ( ( line = br.readLine() ) != null ) {
				// Checking if the line is a point
				if ( !( line.startsWith( ":point(" ) ) && !( line.endsWith( ");" ) ) )
					continue;
				
				// Splitting the string
				String[] contents = line.substring( line.indexOf( "(" ) + 1, line.indexOf( ")" ) ).split( "," );
				
				blueprint.addPoint(
					new BlueprintPoint(
						Integer.parseInt( contents[ 0 ].trim() ),
						Integer.parseInt( contents[ 1 ].trim() ),
						Integer.parseInt( contents[ 2 ].trim() ),
						contents[ 3 ].trim().equalsIgnoreCase( "*" ) ? null : Material.getMaterial( contents[ 3 ].trim() )
							// Checking if the material is wild, getting the material if not.
					)
				);
			}
			
			// Closing the readers.
			br.close();
			fr.close();
			
			return blueprint;
		} catch ( Exception e ) {
			e.printStackTrace();
			
			return null;
		}
	}
}
