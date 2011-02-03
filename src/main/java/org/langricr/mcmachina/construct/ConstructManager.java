package org.langricr.mcmachina.construct;

import java.io.File;

import org.langricr.mcmachina.McMachina;

public class ConstructManager {
	private static ConstructManager _instance = new ConstructManager();
	
	public static ConstructManager getInstance() {
		return _instance;
	}
	
	private File folder = new File( McMachina.getInstance().getDataFolder(), "Saves" );
	
	private ConstructManager() {
		if ( !( folder.exists() ) )
			folder.mkdir();
	}
	
	public File getFolder() {
		return folder;
	}
	
	public void loadAllConstructs() {
		
	}
	
	public void loadConstruct( File file ) {
		
	}
}
