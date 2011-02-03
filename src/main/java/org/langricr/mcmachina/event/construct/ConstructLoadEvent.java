package org.langricr.mcmachina.event.construct;

import java.io.File;

import org.langricr.mcmachina.construct.Construct;

public class ConstructLoadEvent extends ConstructFileEvent {
	public ConstructLoadEvent( Construct construct, File file ) {
		super( Type.CONSTRUCT_LOAD, construct, file );
	}
}
