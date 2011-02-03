package org.langricr.mcmachina.event.construct;

import java.io.File;

import org.langricr.mcmachina.construct.Construct;

public class ConstructFileEvent extends ConstructEvent {
	private final File _file;
	
	public ConstructFileEvent( Type type, Construct construct, File file ) {
		super( type, construct );
		
		_file = file;
	}
	
	public final File getFile() {
		return _file;
	}
}
