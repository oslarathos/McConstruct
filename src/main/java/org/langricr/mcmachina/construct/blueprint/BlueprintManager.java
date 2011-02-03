package org.langricr.mcmachina.construct.blueprint;

import java.io.File;

import org.langricr.mcmachina.McMachina;

public class BlueprintManager {
	private static BlueprintManager _instance = new BlueprintManager();
	
	public static BlueprintManager getInstance() {
		return _instance;
	}
	
	private File folder = new File( McMachina.getInstance().getDataFolder(), "Blueprints" );
	
	private BlueprintManager() {
		if ( !( folder.exists() ) )
			folder.mkdir();
	}
	
	public File getFolder() {
		return folder;
	}
}