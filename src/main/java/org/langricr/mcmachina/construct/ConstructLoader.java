package org.langricr.mcmachina.construct;

import java.io.File;
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
	
	private ConstructLoader() {
		if ( !( folder.exists() ) )
			folder.mkdir();
	}
	
	public Class< ? > getClass( String classname ) {
		return classes.get( classname );
	}
}
