package org.langricr.mcconstruct.construct;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.langricr.mcconstruct.McConstruct;
import org.langricr.util.FileClassLoader;

public class ConstructLoader {
	private static ConstructLoader _instance = new ConstructLoader();
	
	public static ConstructLoader getInstance() {
		return _instance;
	}
	
	private final File folder = new File( McConstruct.getInstance().getDataFolder(), "Classes" );
	private Map< String, Class< ? > > classes = new HashMap< String, Class< ? > >();
	private FileClassLoader fcl = null;
	
	private ConstructLoader() {
		if ( !( folder.exists() ) )
			folder.mkdir();
	}
	
	public final File getFolder() {
		return folder;
	}
	
	public synchronized void reloadAllClasses() {
		System.out.println( "Reloading construct classes." );
		
		classes.clear();
		
		try {
			fcl = new FileClassLoader( new URL[] { folder.toURI().toURL() }, Construct.class.getClassLoader() );
		} catch ( Exception e ) {
			e.printStackTrace();
			
			return;
		}
		
		for ( File file : folder.listFiles() ) {
			try {
				reloadClass( file );
			} catch ( Exception e ) {
				System.out.println( " BAD... " + file.getName() );
			}
		}
		
		System.out.println( "Loaded " + classes.size() + " classes." );
	}
	
	public synchronized void reloadClass( File file ) throws Exception {		
		if ( file.isDirectory() || !( file.getName().endsWith( ".class" ) ) ) {
			System.out.println( "SKIP... " + file.getName() );
			return;
		}
		
		Class< ? > clazz = fcl.createClass( file );
		
		System.out.println( "  OK... " + clazz.getName() );
		
		if ( Construct.class.isAssignableFrom( clazz ) )
			classes.put( clazz.getName(), clazz );
	}
	
	public synchronized Class< ? > getClass( String classname ) {
		return classes.get( classname );
	}
}
