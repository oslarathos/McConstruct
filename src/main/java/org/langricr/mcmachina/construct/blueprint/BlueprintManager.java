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
			System.out.print( "Loading " + file.getName() + "... " );
			
			Blueprint blueprint = Blueprint.loadBlueprint( file );
			
			if ( blueprint != null ) {
				System.out.println( "OK" );
				blueprints.add( blueprint );
			} else {
				System.out.println( "BAD" );
			}
		}
	}
	
	public synchronized Blueprint scanCoordinate( Coordinate coord ) {
		for ( Blueprint blueprint : blueprints ) {
			if ( blueprint.isValid( coord ) )
				return blueprint;
		}
		
		return null;
	}
}