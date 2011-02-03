package org.langricr.mcmachina.construct;

import org.langricr.mcmachina.event.block.CBlockDamageEvent;
import org.langricr.mcmachina.event.block.CBlockPlaceEvent;
import org.langricr.mcmachina.event.construct.ConstructCreateEvent;
import org.langricr.mcmachina.event.construct.ConstructDeleteEvent;
import org.langricr.mcmachina.event.construct.ConstructDestroyEvent;
import org.langricr.mcmachina.event.construct.ConstructLoadEvent;
import org.langricr.mcmachina.event.construct.ConstructSaveEvent;
import org.langricr.util.Coordinate;

public class Construct {
	private final Coordinate core;
	
	public Construct( Coordinate core ) {
		this.core = core;
	}
	
	public final Coordinate getCore() {
		return core;
	}
	
	public void onCreate( ConstructCreateEvent cce ) {}
	
	public void onDelete( ConstructDeleteEvent cde ) {}
	
	public void onDestroy( ConstructDestroyEvent cde ) {}
	
	public void onSave( ConstructSaveEvent cse ) {}
	
	public void onLoad( ConstructLoadEvent cle ) {}
	
	public void onBlockDamaged( CBlockDamageEvent bde ) {}
	
	public void onBlockPlaced( CBlockPlaceEvent bpe ) {}
}
