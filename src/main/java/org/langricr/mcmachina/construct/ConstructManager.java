package org.langricr.mcmachina.construct;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.langricr.mcmachina.McMachina;
import org.langricr.mcmachina.event.EventListener;
import org.langricr.mcmachina.event.construct.ConstructCreateEvent;
import org.langricr.mcmachina.event.construct.ConstructDeleteEvent;
import org.langricr.mcmachina.event.construct.ConstructDestroyEvent;
import org.langricr.util.Coordinate;
import org.langricr.util.WorldCoordinate;

public class ConstructManager {
	private static ConstructManager _instance = new ConstructManager();
	
	public static ConstructManager getInstance() {
		return _instance;
	}
	
	private final File folder = new File( McMachina.getInstance().getDataFolder(), "Saves" );
	private Map< WorldCoordinate, Construct > constructs = new HashMap< WorldCoordinate, Construct >();
	
	private ConstructManager() {
		if ( !( folder.exists() ) )
			folder.mkdir();
	}
	
	public final File getFolder() {
		return folder;
	}
	
	public synchronized void loadAllConstructs() {
		for ( File file : folder.listFiles() ) {
			loadConstruct( file );
		}
	}
	
	public synchronized void loadConstruct( File file ) {
		
	}
	
	public synchronized void createConstruct( Coordinate coord, Class<?> clazz ) {
		try {
			if ( !( Construct.class.isAssignableFrom( clazz ) ) )
				throw new Exception( "Construct is not assignable from " + clazz.getName() );
			
			Construct construct = ( Construct ) clazz.getConstructor( Coordinate.class ).newInstance( coord );
			
			ConstructCreateEvent cce = new ConstructCreateEvent( construct );
			
			EventListener.getInstance().callEvent( cce );
			
			if ( cce.isCancelled() ) {
				deleteConstruct( construct );
			} else {
				constructs.put( construct.getCore(), construct );
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	public synchronized void deleteConstruct( Construct construct ) {
		ConstructDeleteEvent cde = new ConstructDeleteEvent( construct );
		
		EventListener.getInstance().callEvent( cde );
		
		if ( !( cde.isCancelled() ) ) {
			constructs.remove( construct.getCore() );
			
			EventListener.getInstance().unregisterConstruct( construct );
		}
	}
	
	public synchronized void destroyConstruct( Construct construct ) {
		ConstructDestroyEvent cde = new ConstructDestroyEvent( construct );
		
		EventListener.getInstance().callEvent( cde );
		
		if ( !( cde.isCancelled() ) )
			deleteConstruct( construct );
	}
	
	public synchronized Construct getConstruct( WorldCoordinate coord ) {
		return constructs.get( coord );
	}
}
