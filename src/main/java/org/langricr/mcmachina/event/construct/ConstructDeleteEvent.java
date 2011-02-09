package org.langricr.mcmachina.event.construct;

import org.langricr.mcmachina.construct.Construct;

public class ConstructDeleteEvent extends ConstructModifyEvent {
	public ConstructDeleteEvent( Construct construct) {
		super( Type.CONSTRUCT_DELETE, construct );
	}
}
