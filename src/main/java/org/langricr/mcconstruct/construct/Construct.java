package org.langricr.mcconstruct.construct;

import java.util.UUID;

import org.langricr.mcconstruct.event.block.CBlockDamageEvent;
import org.langricr.mcconstruct.event.block.CBlockPlaceEvent;
import org.langricr.mcconstruct.event.block.CBlockRightClickEvent;
import org.langricr.mcconstruct.event.construct.ConstructCreateEvent;
import org.langricr.mcconstruct.event.construct.ConstructDeleteEvent;
import org.langricr.mcconstruct.event.construct.ConstructDestroyEvent;
import org.langricr.mcconstruct.event.construct.ConstructLoadEvent;
import org.langricr.mcconstruct.event.construct.ConstructSaveEvent;
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
	
	public void onConstructDestroyed( ConstructDestroyEvent cde ) {}
	
	public void onConstructSave( ConstructSaveEvent cse ) {}
	
	public void onConstructLoad( ConstructLoadEvent cle ) {}
	
	public void onBlockDamaged( CBlockDamageEvent bde ) {}
	
	public void onBlockPlaced( CBlockPlaceEvent bpe ) {}
	
	public void onBlockRightClicked( CBlockRightClickEvent cbrce ) {}
	
	public String toString() {
		return "(" + getClass().getName() + "|" + getCore().toString() + ")";
	}
}
