package org.langricr.mcmachina.construct.blueprint;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

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
						Integer.parseInt( contents[ 0 ] ),
						Integer.parseInt( contents[ 1 ] ),
						Integer.parseInt( contents[ 2 ] ),
						contents[ 3 ].equalsIgnoreCase( "*" ) ? null : Material.getMaterial( contents[ 3 ] )
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
