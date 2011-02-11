package org.langricr.mcconstruct.event.construct;

import org.langricr.mcconstruct.construct.Construct;

public class ConstructUnloadEvent extends ConstructEvent {
	public ConstructUnloadEvent( Construct construct ) {
		super( Type.CONSTRUCT_UNLOAD, construct );
	}
}
