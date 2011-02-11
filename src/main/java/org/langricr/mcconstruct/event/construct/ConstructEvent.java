package org.langricr.mcconstruct.event.construct;

import org.langricr.mcconstruct.construct.Construct;
import org.langricr.mcconstruct.event.Event;

public class ConstructEvent extends Event {
	private final Construct _construct;
	private boolean cancelled = false;
	
	public ConstructEvent( Type type, Construct construct ) {
		super( type );
		
		_construct = construct;
	}
	
	public final Construct getConstruct() {
		return _construct;
	}

	public boolean isCancelled() {
		return cancelled;
	}
	
	public void setCancelled( boolean state )  {
		cancelled = state;
	}
}
