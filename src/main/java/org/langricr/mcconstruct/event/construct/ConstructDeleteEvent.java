package org.langricr.mcconstruct.event.construct;

import org.langricr.mcconstruct.construct.Construct;

public class ConstructDeleteEvent extends ConstructModifyEvent {
	public ConstructDeleteEvent( Construct construct) {
		super( Type.CONSTRUCT_DELETE, construct );
	}
}
