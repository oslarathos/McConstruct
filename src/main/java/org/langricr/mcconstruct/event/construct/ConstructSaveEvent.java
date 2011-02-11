package org.langricr.mcconstruct.event.construct;

import java.io.File;

import org.langricr.mcconstruct.construct.Construct;

public class ConstructSaveEvent extends ConstructFileEvent {	
	public ConstructSaveEvent( Construct construct, File file ) {
		super( Type.CONSTRUCT_SAVE, construct, file );
	}
}
