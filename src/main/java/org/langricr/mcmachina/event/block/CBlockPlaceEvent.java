package org.langricr.mcmachina.event.block;

import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent;

public class CBlockPlaceEvent extends CBlockEvent {
	public CBlockPlaceEvent( Block block, BlockPlaceEvent event ) {
		super( Type.BLOCK_PLACED, block, event );
	}
	
	public BlockPlaceEvent getEvent() {
		return ( BlockPlaceEvent ) super.getEvent();
	}
}
