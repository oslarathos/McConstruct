package org.langricr.mcmachina.event;

public abstract class Event {
	private final Type _type;
	
	public Event( Type type ) {
		_type = type;
	}
	
	public final Type getType() {
		return _type;
	}
	
	public enum Type {
		CONSTRUCT_CREATE,
		CONSTRUCT_DELETE,
		CONSTRUCT_DESTROY,
		CONSTRUCT_SAVE,
		CONSTRUCT_LOAD,
		BLOCK_DAMAGED,
		BLOCK_PLACED,
		BLOCK_RIGHTCLICKED;
	}
}
