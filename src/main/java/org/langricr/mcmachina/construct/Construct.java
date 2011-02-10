package org.langricr.mcmachina.construct;

import java.util.UUID;

import org.langricr.mcmachina.event.block.CBlockDamageEvent;
import org.langricr.mcmachina.event.block.CBlockPlaceEvent;
import org.langricr.mcmachina.event.block.CBlockRightClickEvent;
import org.langricr.mcmachina.event.construct.ConstructCreateEvent;
import org.langricr.mcmachina.event.construct.ConstructDeleteEvent;
import org.langricr.mcmachina.event.construct.ConstructDestroyEvent;
import org.langricr.mcmachina.event.construct.ConstructLoadEvent;
import org.langricr.mcmachina.event.construct.ConstructSaveEvent;
import org.langricr.util.WorldCoordinate;

public class Construct {
	private final WorldCoordinate core;
	private UUID uuid = UUID.randomUUID();
	
	public Construct( WorldCoordinate core ) {
		this.core = core;
	}
	
	public final WorldCoordinate getCore() {
		return core;
	}
	
	public final UUID getUUID() {
		return uuid;
	}
	
	public final void setUUID( UUID uuid ) {
		this.uuid = uuid;
	}
	
	public void onConstructCreated( ConstructCreateEvent cce ) {}
	
	public void onConstructDeleted( ConstructDeleteEvent cde ) {}
	
	public void onDestroy( ConstructDestroyEvent cde ) {}
	
	public void onSave( ConstructSaveEvent cse ) {}
	
	public void onLoad( ConstructLoadEvent cle ) {}
	
	public void onBlockDamaged( CBlockDamageEvent bde ) {}
	
	public void onBlockPlaced( CBlockPlaceEvent bpe ) {}
	
	public void onBlockRightClicked( CBlockRightClickEvent cbrce ) {}
}
