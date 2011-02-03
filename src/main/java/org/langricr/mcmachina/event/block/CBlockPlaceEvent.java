package org.langricr.mcmachina.event.block;

import org.bukkit.event.block.BlockPlaceEvent;

public class CBlockPlaceEvent extends CBlockEvent {
	public CBlockPlaceEvent( BlockPlaceEvent event ) {
		super( Type.BLOCK_PLACED, event );
	}
	
	public BlockPlaceEvent getEvent() {
		return ( BlockPlaceEvent ) super.getEvent();
	}
}
