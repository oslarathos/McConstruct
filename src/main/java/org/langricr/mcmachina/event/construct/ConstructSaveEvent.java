package org.langricr.mcmachina.event.construct;

import java.io.File;

import org.langricr.mcmachina.construct.Construct;

public class ConstructSaveEvent extends ConstructFileEvent {	
	public ConstructSaveEvent( File file, Construct construct ) {
		super( Type.CONSTRUCT_SAVE, construct, file );
	}
}
