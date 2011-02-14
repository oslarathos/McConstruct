package org.langricr.mcconstruct.construct;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.langricr.mcconstruct.McConstruct;
import org.langricr.mcconstruct.construct.blueprint.Blueprint;
import org.langricr.mcconstruct.construct.blueprint.BlueprintManager;
import org.langricr.mcconstruct.event.EventListener;
import org.langricr.mcconstruct.event.construct.ConstructCreateEvent;
import org.langricr.mcconstruct.event.construct.ConstructDeleteEvent;
import org.langricr.mcconstruct.event.construct.ConstructLoadEvent;
import org.langricr.mcconstruct.event.construct.ConstructSaveEvent;
import org.langricr.mcconstruct.event.construct.ConstructUnloadEvent;
import org.langricr.util.WorldCoordinate;

public class ConstructManager {
	private static ConstructManager _instance = new ConstructManager();
	
	/**
	 * Used to retrieve the construct manager.
	 * @return instance the construct manager
	 */
	public static ConstructManager getInstance() {
		return _instance;
	}
	
	private final File folder = new File( McConstruct.getInstance().getDataFolder(), "Save" );
	private final File data = new File( folder, "Data" );
	private Map< WorldCoordinate, Construct > constructs = new HashMap< WorldCoordinate, Construct >();
	
	private ConstructManager() {
		if ( !( folder.exists() ) )
			folder.mkdir();
		
		if ( !( data.exists() ) )
			data.mkdir();
	}
	
	/**
	 *	Returns the folder that contains all critical construct save data.
	 * 	@return The folder containing all construct save files.
	 */
	public final File getFolder() {
		return folder;
	}
	
	/**
	 * Unloads all currently loaded constructs.
	 * <br />
	 * <i>Called by the plugin when the plugin is being disabled.</i>
	 */
	public synchronized void unloadAllConstructs() {
		// Calling all constructs in an arraylist to avoid concurrent modifications when removing the constructs.
		for ( Construct c : new ArrayList< Construct >( constructs.values() ) ) {
			unloadConstruct( c );
		}
	}
	
	/**
	 * Unloads the construct, essentially a save and remove method, calls a ConstructSaveEvent if not cancelled.
	 * @param construct The construct to be unloaded
	 */
	public synchronized void unloadConstruct( Construct construct ) {
		ConstructUnloadEvent cue = new ConstructUnloadEvent( construct );
		
		EventListener.getInstance().callEvent( cue );
		
		if ( cue.isCancelled() )
			return;
		
		saveConstruct( construct );
		
		constructs.remove( construct.getCore() );
	}
	
	/**
	 *	Attempts to load all saved constructs.
	 * 	<br />
	 * 	<i>Called by the plugin when the plugin is being enabled.</i>
	 */
	public synchronized void reloadAllConstructs() {
		System.out.println( "Reloading all constructs." );
		
		unloadAllConstructs();
		
		loadAllConstructs();
		
		System.out.println( "Reloaded " + constructs.size() + " constructs" );
	}
	
	public synchronized void loadAllConstructs() {
		for ( File file : folder.listFiles() ) {
			if ( file.isDirectory() )
				continue;
			
			loadConstruct( file );
		}
	}
	
	/**
	 * Attempts to load the construct from the file, if cancelled the 
	 * @param file The file from which to load the construct.
	 */
	public synchronized void loadConstruct( File file ) {
		try {
			// We'll create the reader objects
			FileReader fr = new FileReader( file );
			BufferedReader br = new BufferedReader( fr );
			
			// And then create the variables to hold the information
			String line = null;
			int x = 0, y = 0, z = 0;
			String world = null, uuid = null;
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
				
				if ( line.startsWith( "UUID=" ) )
					uuid = line.substring( line.indexOf( "=" ) + 1 ).trim();
				
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
			if ( constructs.containsKey( coord ) ) {
				System.out.println( "CNA1... " + file.getName() );
				
				file.delete();
				
				return;
			}
			
			// We'll scan the coordinate to make sure the construct can still fit.
			Blueprint blueprint = BlueprintManager.getInstance().getBlueprint( clazz.getName() );
			
			// We can no longer fit the construct into that location.
			if ( blueprint == null || !( blueprint.isValid( coord ) ) ) {
				System.out.println( "CNA2... " + file.getName() );
			
				file.delete();
				
				return;
			}
		
			// And create the construct
			Construct c = ( Construct ) clazz.getConstructor( WorldCoordinate.class ).newInstance( coord );
			
			// Set the UUID
			c.setUUID( UUID.fromString( uuid ) );
			
			// We'll then create an event to load the construct, this allows the construct to do some additional loading.
			ConstructLoadEvent cle = new ConstructLoadEvent( c, new File( data, file.getName() + ".ini" ) );
			
			// And call it
			EventListener.getInstance().callEvent( cle );
			
			// We'll unregister the construct if the event has been cancelled.
			if (cle.isCancelled() ) {
				EventListener.getInstance().unregisterConstruct( c );
				
				return;
			}
			
			constructs.put( coord, c );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	/**
	 *  Attempts to save all currently loaded constructs.
	 */
	public synchronized void saveAllConstructs() {
		for ( Construct construct : constructs.values() ) {
			saveConstruct( construct );
		}
	}
	
	/**
	 * 	Attempts to save a construct to the disk.
	 * 	<br />
	 *	Creates the construct file to hold the information pertaining to a construct directly.
	 *	Calls a ConstructSaveEvent with an optional related file to store additional information pertaining to a construct's data.
	 *	<br />
	 *  Cancelling will not alter either of the two files.
	 * 	@param construct The construct to be saved.
	 */
	public synchronized void saveConstruct( Construct construct ) {
		try {
			// Defining the files based on the constructs UUID.
			File saveFile = getConstructFile( construct );
			File dataFile = getConstructDateFile( construct );
			
			ConstructSaveEvent cde = new ConstructSaveEvent( construct, dataFile );
			
			// And call all listeners
			EventListener.getInstance().callEvent( cde );
			
			// This allows the construct to cancel itself from saving
			if ( cde.isCancelled() ) {
				
				return;
			}
			
			// Recreating the save file
			if ( saveFile.delete() || !( saveFile.exists() ) )
				saveFile.createNewFile();
			

			// We'll create a print writer
			PrintWriter pw = new PrintWriter( saveFile );
			
			// We'll write out the coordinates and class
			pw.println( "X=" + construct.getCore().getX() );
			pw.println( "Y=" + construct.getCore().getY() );
			pw.println( "Z=" + construct.getCore().getZ() );
			pw.println( "World=" + construct.getCore().getWorldName() );
			pw.println( "UUID=" + construct.getUUID().toString() );
			pw.println( "Class=" + construct.getClass().getName() );
			
			// Then flush the stream and close it.
			pw.flush();
			pw.close();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Attempts to create a construct using the specified class at the specified coordinate.
	 * <br />
	 * <i>Called when attempting to create a construct, cancelling will call a ConstructDeleteEvent afterwards.</i>
	 * @param coord
	 * @param clazz
	 */
	public synchronized void createConstruct( Class<?> clazz, WorldCoordinate coord ) {
		try {
			// We'll make sure the class is actually a construct
			if ( !( Construct.class.isAssignableFrom( clazz ) ) )
				throw new Exception( "Construct is not assignable from " + clazz.getName() );
			
			// We'll create the construct
			Construct construct = ( Construct ) clazz.getConstructor( WorldCoordinate.class ).newInstance( coord );
			
			// and add it to the list.
			constructs.put( construct.getCore(), construct );
			
			// Create the construct create event.
			ConstructCreateEvent cce = new ConstructCreateEvent( construct );
			
			// Call the event
			EventListener.getInstance().callEvent( cce );
			
			// Delete the construct if cancelled.
			if ( cce.isCancelled() ) {
				deleteConstruct( construct );
			} else {
				System.out.println( "Construct created: " + construct.toString() );
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Attempts to delete the construct from the game.
	 * <br />
	 * If it is not can cancelled, it will delete the save file, remove the construct from the list and unhook all of it's listeners.
	 * @param construct The construct to be deleted.
	 */
	public synchronized void deleteConstruct( Construct construct ) {
		if ( construct == null )
			return;
		
		ConstructDeleteEvent cde = new ConstructDeleteEvent( construct );
		
		EventListener.getInstance().callEvent( cde );
		
		if ( cde.isCancelled() )
			return;
		
		getConstructFile( construct ).delete();
		getConstructDateFile( construct ).delete();
		
		constructs.remove( construct.getCore() );
		
		EventListener.getInstance().unregisterConstruct( construct );
	}
	
	/**
	 * Attempts to return the construct located at that coordinate.
	 * @param coord The coordinate to check
	 * @return Construct The construct at that coordinate if any.
	 */
	public synchronized Construct getConstruct( WorldCoordinate coord ) {
		return constructs.get( coord );
	}
	
	/**
	 * Returns the file for the construct containing coordinates and location in the server, do not modify.
	 * @param construct The construct to retrieve the data file for
	 * @return The save file of the construct
	 */
	public synchronized File getConstructFile( Construct construct ) {
		if ( construct == null )
			return null;
		
		return new File( folder, construct.getUUID().toString() + ".txt" );
	}
	
	public synchronized File getConstructDateFile( Construct construct ) {
		if ( construct == null )
			return null;
		
		return new File( data, getConstructFile( construct ).getName() + ".ini" );
	}
}
