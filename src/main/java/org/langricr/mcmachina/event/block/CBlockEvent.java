package org.langricr.mcmachina.event.block;

import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockEvent;
import org.langricr.mcmachina.event.Event;

public class CBlockEvent extends Event implements Cancellable {
	private final Block block;
	private boolean state;
	private final BlockEvent event;
	
	public CBlockEvent( Type type, Block block, BlockEvent event ) {
		super( type );
		
		this.block = block;
		this.event = event;
	}
	
	public final Block getBlock() {
		return block;
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
