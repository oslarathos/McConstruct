package org.langricr.mcconstruct.event.construct;

import org.langricr.mcconstruct.construct.Construct;

public class ConstructCreateEvent extends ConstructModifyEvent {
	public ConstructCreateEvent( Construct construct ) {
		super( Type.CONSTRUCT_CREATE, construct );
	}
}
