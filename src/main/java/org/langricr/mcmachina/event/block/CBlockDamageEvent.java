package org.langricr.mcmachina.event.block;

import org.bukkit.event.block.BlockDamageEvent;

public class CBlockDamageEvent extends CBlockEvent {
	
	public CBlockDamageEvent( BlockDamageEvent event ) {
		super( Type.BLOCK_DAMAGED, event );
	}
	
	public BlockDamageEvent getEvent() {
		return ( BlockDamageEvent ) super.getEvent();
	}
}
