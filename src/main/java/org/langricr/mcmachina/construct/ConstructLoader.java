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
	
	public final File getFolder() {
		return folder;
	}
	
	public synchronized void reloadClasses() {
		System.out.println( "Reloading construct classes." );
		
		classes.clear();
		
		try {
			urlcl = new URLClassLoader( new URL[] { folder.toURI().toURL() }, Construct.class.getClassLoader() );
		} catch ( Exception e ) {
			e.printStackTrace();
			
			return;
		}
		
		for ( File file : folder.listFiles() ) {
			System.out.print( "\t" );
			
			try {
				reloadClass( file );
				
				System.out.print( "  OK" );
			} catch ( Exception e ) {
				System.out.print( e.getMessage() );
			}
			
			System.out.println( "... " + file.getName() );
		}
		
		System.out.println( "Loaded " + classes.size() + " classes." );
	}
	
	public synchronized void reloadClass( File file ) throws Exception {
		if ( file.getName().contains( "$" ) )
			throw new Exception( "NEST" );
		
		if ( file.isDirectory() || !( file.getName().endsWith( ".class" ) ) )
			throw new Exception( "SKIP" );
		
		Class< ? > clazz = urlcl.loadClass( file.getName().substring( 0, file.getName().indexOf( ".class" ) ) );
		
		if ( clazz == null || !( Construct.class.isAssignableFrom( clazz ) ) )
			throw new Exception( " BAD" );
		
		classes.put( clazz.getName(), clazz );
	}
	
	public synchronized Class< ? > getClass( String classname ) {
		return classes.get( classname );
	}
}
