package org.langricr.mcmachina.construct.blueprint;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.langricr.mcmachina.McMachina;
import org.langricr.util.Coordinate;

public class BlueprintManager {
	private static BlueprintManager _instance = new BlueprintManager();
	
	public static BlueprintManager getInstance() {
		return _instance;
	}
	
	private File folder = new File( McMachina.getInstance().getDataFolder(), "Blueprints" );
	private List< Blueprint > blueprints = new ArrayList< Blueprint >();
	
	private BlueprintManager() {
		if ( !( folder.exists() ) )
			folder.mkdir();
	}
	
	public File getFolder() {
		return folder;
	}
	
	public synchronized void reloadBlueprints() {
		System.out.println( "Reloading blueprints." );
		
		blueprints.clear();
		
		for ( File file : folder.listFiles() ) {
			System.out.print( "\t" );
			
			Blueprint blueprint = Blueprint.loadBlueprint( file );
			
			if ( blueprint != null ) {
				System.out.print( "  OK" );
				
				blueprints.add( blueprint );
			} else {
				System.out.print( " BAD" );
			}
			
			System.out.println( "... " + file.getName() );
		}
		
		System.out.println( "Loaded " + blueprints.size() + " blueprints." );
	}
	
	public synchronized Blueprint scanCoordinate( Coordinate coord ) {
		for ( Blueprint blueprint : blueprints ) {
			System.out.println( "Checking: " + blueprint.getClassname() );
			
			if ( blueprint.isValid( coord ) )
				return blueprint;
		}
		
		return null;
	}
	
	public synchronized Blueprint getBlueprint( String classname ) {
		for ( Blueprint blueprint : blueprints ) {
			if ( blueprint.getClassname().equalsIgnoreCase( classname ) )
				return blueprint;
		}
		
		return null;
	}
}