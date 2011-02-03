package org.langricr.mcmachina.construct;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

import org.langricr.mcmachina.McMachina;

public class ConstructLoader {
	private static ConstructLoader _instance = new ConstructLoader();
	
	public static ConstructLoader getInstance() {
		return _instance;
	}
	
	private final File folder = new File( McMachina.getInstance().getDataFolder(), "Classes" );
	private Map< String, Class< ? > > classes = new HashMap< String, Class< ? > >();
	private URLClassLoader urlcl = null;
	
	private ConstructLoader() {
		if ( !( folder.exists() ) )
			folder.mkdir();
	}
	
	public synchronized void reloadClasses() {
		System.out.println( "Reloading construct classes." );
		
		classes.clear();
		
		try {
			urlcl = new URLClassLoader( new URL[] { folder.toURI().toURL() } );
		} catch ( Exception e ) {
			e.printStackTrace();
			
			return;
		}
		
		for ( File file : folder.listFiles() ) {
			try {
				System.out.print( "LOADING " + file.getName().toUpperCase() + "... " );
				
				reloadClass( file );
				
				System.out.println( "OK" );
			} catch ( Exception e ) {
				System.out.println( e.getMessage() );
				
				continue;
			}
		}
	}
	
	public synchronized void reloadClass( File file ) throws Exception {
		if ( file.getName().contains( "$" ) )
			throw new Exception( "NESTED" );
		
		if ( file.isDirectory() || !( file.getName().endsWith( ".class" ) ) )
			throw new Exception( "SKIP" );
		
		Class< ? > clazz = urlcl.loadClass( file.getName().substring( 0, file.getName().indexOf( ".class" ) ) );
		
		if ( clazz == null || !( Construct.class.isAssignableFrom( clazz ) ) )
			throw new Exception( "NOT ASSIGNABLE" );
		
		classes.put( clazz.getName(), clazz );
	}
	
	public synchronized Class< ? > getClass( String classname ) {
		return classes.get( classname );
	}
}
