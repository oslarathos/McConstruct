package org.langricr.mcconstruct.construct.blueprint;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.langricr.mcconstruct.McConstruct;
import org.langricr.util.WorldCoordinate;

public class BlueprintManager {
	private static BlueprintManager _instance = new BlueprintManager();
	
	public static BlueprintManager getInstance() {
		return _instance;
	}
	
	private File folder = new File( McConstruct.getInstance().getDataFolder(), "Blueprints" );
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
			Blueprint blueprint = Blueprint.loadBlueprint( file );
			
			if ( blueprint != null ) {
				System.out.print( "  OK" );
				
				blueprints.add( blueprint );
			} else {
				System.out.print( " BAD" );
			}
			
			System.out.println( ". " + blueprint.toString() );
		}
		
		System.out.println( "Loaded " + blueprints.size() + " blueprints." );
	}
	
	public synchronized Blueprint scanCoordinate( WorldCoordinate coord ) {
		for ( Blueprint blueprint : blueprints ) {
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