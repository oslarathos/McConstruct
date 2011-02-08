package org.langricr.mcmachina.event.construct;

import java.io.File;

import org.langricr.mcmachina.construct.Construct;

public class ConstructSaveEvent extends ConstructFileEvent {	
	public ConstructSaveEvent( Construct construct, File file ) {
		super( Type.CONSTRUCT_SAVE, construct, file );
	}
}
