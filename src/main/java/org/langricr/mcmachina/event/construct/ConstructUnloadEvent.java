package org.langricr.mcmachina.event.construct;

import org.langricr.mcmachina.construct.Construct;

public class ConstructUnloadEvent extends ConstructEvent {
	public ConstructUnloadEvent( Construct construct ) {
		super( Type.CONSTRUCT_UNLOAD, construct );
	}
}
