package org.langricr.mcconstruct.event.block;

import org.bukkit.event.block.BlockRedstoneEvent;

public class CBlockRedstoneEvent extends CBlockEvent {
	public CBlockRedstoneEvent( BlockRedstoneEvent bre ) {
		super( Type.BLOCK_REDSTONE, bre );
	}

	public BlockRedstoneEvent getEvent() {
		return ( BlockRedstoneEvent ) super.getEvent();
	}
}
