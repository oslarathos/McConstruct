package org.langricr.mcconstruct.event.block;

import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockEvent;
import org.langricr.mcconstruct.event.Event;

public class CBlockEvent extends Event implements Cancellable {
	private boolean state;
	private final BlockEvent event;
	
	public CBlockEvent( Type type, BlockEvent event ) {
		super( type );
		
		this.event = event;
	}
	
	public final Block getBlock() {
		return event.getBlock();
	}
	
	public BlockEvent getEvent() {
		return event;
	}

	public boolean isCancelled() {
		return state;
	}

	public void setCancelled( boolean state ) {
		this.state = state;
	}
}
