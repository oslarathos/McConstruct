package org.langricr.mcconstruct.event.construct;

import java.io.File;

import org.langricr.mcconstruct.construct.Construct;

public class ConstructFileEvent extends ConstructEvent {
	private final File _file;
	private boolean cancelled = false;
	
	public ConstructFileEvent( Type type, Construct construct, File file ) {
		super( type, construct );
		
		_file = file;
	}
	
	public final File getFile() {
		return _file;
	}
	
	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled( boolean state ) {
		cancelled = state;
	}
}
