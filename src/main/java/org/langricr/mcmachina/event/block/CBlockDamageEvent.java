package org.langricr.mcmachina.event.block;

import org.bukkit.block.Block;
import org.bukkit.block.BlockDamageLevel;
import org.bukkit.event.block.BlockDamageEvent;

public class CBlockDamageEvent extends CBlockEvent {
	private final BlockDamageLevel _bdl;
	
	public CBlockDamageEvent( Block block, BlockDamageLevel bdl, BlockDamageEvent event ) {
		super( Type.BLOCK_DAMAGED, block, event );
		
		_bdl = bdl;
	}
	
	public final BlockDamageLevel getLevel() {
		return _bdl;
	}
	
	public BlockDamageEvent getEvent() {
		return ( BlockDamageEvent ) super.getEvent();
	}
}
