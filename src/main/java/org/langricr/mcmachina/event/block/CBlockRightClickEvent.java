package org.langricr.mcmachina.event.block;

import org.bukkit.event.block.BlockRightClickEvent;

public class CBlockRightClickEvent extends CBlockEvent {
	public CBlockRightClickEvent( BlockRightClickEvent event) {
		super(Type.BLOCK_RIGHTCLICKED, event);
	}
	
	public BlockRightClickEvent getEvent() {
		return ( BlockRightClickEvent ) super.getEvent();
	}
}
