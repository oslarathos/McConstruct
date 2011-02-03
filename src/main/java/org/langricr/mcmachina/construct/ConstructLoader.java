package org.langricr.mcmachina.construct;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.langricr.mcmachina.McMachina;
import org.langricr.util.FileClassLoader;

public class ConstructLoader {
	private static ConstructLoader _instance = new ConstructLoader();
	
	public static ConstructLoader getInstance() {
		return _instance;
	}
	
	private final File folder = new File( McMachina.getInstance().getDataFolder(), "Classes" );
	private Map< String, Class< ? > > classes = new HashMap< String, Class< ? > >();
	private FileClassLoader urlcl = null;
	
	private ConstructLoader() {
		if ( !( folder.exists() ) )
			folder.mkdir();
	}
	
	public final File getFolder() {
		return folder;
	}
	
	public synchronized void reloadClasses() {
		System.out.println( "Reloading construct classes." );
		
		classes.clear();
		
		try {
			urlcl = new FileClassLoader( new URL[] { folder.toURI().toURL() }, Construct.class.getClassLoader() );
		} catch ( Exception e ) {
			e.printStackTrace();
			
			return;
		}
		
		for ( File file : folder.listFiles() ) {
			System.out.print( "\t" );
			
			try {
				reloadClass( file );
			} catch ( Exception e ) {
				System.out.print( e.getMessage() );
			}
			
			System.out.println( "... " + file.getName() );
		}
		
		System.out.println( "Loaded " + classes.size() + " classes." );
	}
	
	public synchronized void reloadClass( File file ) throws Exception {		
		if ( file.isDirectory() || !( file.getName().endsWith( ".class" ) ) ) {
			System.out.print( "SKIP" );
		
			return;
		}
		
		Class< ? > clazz = urlcl.createClass( file );
		
		if ( clazz == null ) {
			System.out.print( "NULL" );
		
			return;
		}
		
		System.out.print( "  OK" );
		
		if ( Construct.class.isAssignableFrom( clazz ) )
			classes.put( clazz.getName(), clazz );
	}
	
	public synchronized Class< ? > getClass( String classname ) {
		return classes.get( classname );
	}
}
