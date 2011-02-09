package org.langricr.mcmachina.event.construct;

import org.langricr.mcmachina.construct.Construct;

public class ConstructCreateEvent extends ConstructModifyEvent {
	public ConstructCreateEvent( Construct construct ) {
		super( Type.CONSTRUCT_CREATE, construct );
	}
}
