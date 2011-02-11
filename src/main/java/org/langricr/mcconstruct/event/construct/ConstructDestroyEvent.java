package org.langricr.mcconstruct.event.construct;

import org.langricr.mcconstruct.construct.Construct;

public class ConstructDestroyEvent extends ConstructModifyEvent {
	public ConstructDestroyEvent( Construct construct ) {
		super( Type.CONSTRUCT_DESTROY, construct );
	}
}
