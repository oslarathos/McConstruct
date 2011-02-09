package org.langricr.mcmachina.construct;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.langricr.mcmachina.McMachina;
import org.langricr.mcmachina.construct.blueprint.Blueprint;
import org.langricr.mcmachina.construct.blueprint.BlueprintManager;
import org.langricr.mcmachina.event.EventListener;
import org.langricr.mcmachina.event.construct.ConstructCreateEvent;
import org.langricr.mcmachina.event.construct.ConstructDeleteEvent;
import org.langricr.mcmachina.event.construct.ConstructDestroyEvent;
import org.langricr.mcmachina.event.construct.ConstructLoadEvent;
import org.langricr.mcmachina.event.construct.ConstructSaveEvent;
import org.langricr.util.WorldCoordinate;

public class ConstructManager {
	private static ConstructManager _instance = new ConstructManager();
	
	public static ConstructManager getInstance() {
		return _instance;
	}
	
	private final File folder = new File( McMachina.getInstance().getDataFolder(), "Save" );
	private final File data = new File( folder, "Data" );
	private Map< WorldCoordinate, Construct > constructs = new HashMap< WorldCoordinate, Construct >();
	
	private ConstructManager() {
		if ( !( folder.exists() ) )
			folder.mkdir();
		
		if ( !( data.exists() ) )
			data.mkdir();
	}
	
	public final File getFolder() {
		return folder;
	}
	
	public synchronized void loadAllConstructs() {
		for ( File file : folder.listFiles() ) {
			if ( !( file.isDirectory() ) )
				loadConstruct( file );
		}
	}
	
	public synchronized void loadConstruct( File file ) {
		try {
			// We'll create the reader objects
			FileReader fr = new FileReader( file );
			BufferedReader br = new BufferedReader( fr );
			
			// And then create the variables to hold the information
			String line = null;
			int x = 0, y = 0, z = 0;
			String world = null;
			Class< ? > clazz = null;
			
			// We'll then go through the file
			while ( ( line = br.readLine() ) != null ) {
				// Get the X coordinate
				if ( line.startsWith( "X=" ) )
					x = Integer.parseInt( line.substring( line.indexOf( "=" ) + 1 ).trim() );
				
				// Get the Y coordinate
				if ( line.startsWith( "Y=" ) )
					y = Integer.parseInt( line.substring( line.indexOf( "=" ) + 1 ).trim() );
				
				// Get the Z coordinate
				if ( line.startsWith( "Z=" ) )
					z = Integer.parseInt( line.substring( line.indexOf( "=" ) + 1 ).trim() );
				
				// Get the world
				if ( line.startsWith( "World=" ) )
					world = line.substring( line.indexOf( "=" ) + 1 ).trim();
				
				// And finally the class
				if ( line.startsWith( "Class=" ) )
					clazz = ConstructLoader.getInstance().getClass( line.substring( line.indexOf( "=" ) + 1 ).trim() );
			}
			
			// We'll close the readers.
			br.close();
			fr.close();
			
			// We'll get the coordinate
			WorldCoordinate coord = new WorldCoordinate( world, x, y, z );
			
			// Make sure another construct hasn't taken it's place
			if ( constructs.containsKey( coord ) )
				return;
			
			// We'll scan the coordinate to make sure the construct can still fit.
			Blueprint blueprint = BlueprintManager.getInstance().getBlueprint( clazz.getName() );
			
			// We can no longer fit the construct into that location.
			if ( blueprint == null || blueprint.isValid( coord ) )
				return;
			
			// And create the construct
			Construct c = ( Construct ) clazz.getConstructor( WorldCoordinate.class ).newInstance( coord );
			
			// We'll then create an event to load the construct, this allows the construct to do some additional loading.
			ConstructLoadEvent cle = new ConstructLoadEvent( c, new File( data, file.getName() + ".ini" ) );
			
			// And call it
			EventListener.getInstance().callEvent( cle );
			
			// We'll add the construct only if the loading event is not canceled.
			if ( !( cle.isCancelled() ) )
				constructs.put( coord, c );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	public synchronized void saveAllConstructs( boolean skipEvents) {
		for ( Construct construct : constructs.values() ) {
			saveConstruct( construct, skipEvents );
		}
	}
	
	public synchronized void saveConstruct( Construct construct, boolean skipEvent ) {
		try {
			// Defining the file based on the constructs UUID.
			File file = new File( folder, construct.getUUID() + ".txt" );
			
			// Recreating the file
			if ( file.delete() || !( file.exists() ) )
				file.createNewFile();
			
			// We'll process the event first.
			if (  !( skipEvent ) ) {
				ConstructSaveEvent cde = new ConstructSaveEvent( construct, new File( data, file.getName() + ".ini" ) );
				
				// And call all listeners
				EventListener.getInstance().callEvent( cde );
				
				// This allows the construct to cancel itself from saving
				if ( cde.isCancelled() ) {
					file.delete();
					
					return;
				}
			}

			// We'll create a print writer
			PrintWriter pw = new PrintWriter( file );
			
			// We'll write out the coordinates and class
			pw.println( "X=" + construct.getCore().getX() );
			pw.println( "Y=" + construct.getCore().getY() );
			pw.println( "Z=" + construct.getCore().getZ() );
			pw.println( "World=" + construct.getCore().getWorldName() );
			pw.println( "Class=" + construct.getClass().getName() );
			
			// Then flush the stream and close it.
			pw.flush();
			pw.close();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	public synchronized void createConstruct( WorldCoordinate coord, Class<?> clazz ) {
		try {
			// We'll make sure the class is actually a construct
			if ( !( Construct.class.isAssignableFrom( clazz ) ) )
				throw new Exception( "Construct is not assignable from " + clazz.getName() );
			
			// We'll create the construct
			Construct construct = ( Construct ) clazz.getConstructor( WorldCoordinate.class ).newInstance( coord );
			
			// Create the construct create event.
			ConstructCreateEvent cce = new ConstructCreateEvent( construct );
			
			// Call the event
			EventListener.getInstance().callEvent( cce );
			
			// Delete it if cancelled or add it if not.
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
			new File( folder, construct.getUUID() + ".txt" ).delete();
			
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
