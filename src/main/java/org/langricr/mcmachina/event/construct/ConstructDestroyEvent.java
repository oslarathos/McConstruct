package org.langricr.mcmachina.event.construct;

import org.langricr.mcmachina.construct.Construct;

public class ConstructDestroyEvent extends ConstructModifyEvent {
	public ConstructDestroyEvent( Construct construct ) {
		super( Type.CONSTRUCT_DESTROY, construct );
	}
}
