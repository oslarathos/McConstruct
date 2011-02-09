package org.langricr.mcmachina.event.construct;

import org.bukkit.event.Cancellable;
import org.langricr.mcmachina.construct.Construct;

public class ConstructModifyEvent extends ConstructEvent implements Cancellable {

	public ConstructModifyEvent(Type type, Construct construct) {
		super(type, construct);
		// TODO Auto-generated constructor stub
	}

	private boolean cancelled = false;
	
	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancel) {
		cancelled = cancel;
	}

}
