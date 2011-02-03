package org.langricr.mcmachina.event;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.langricr.mcmachina.construct.Construct;
import org.langricr.mcmachina.event.Event.Type;
import org.langricr.mcmachina.event.block.CBlockDamageEvent;
import org.langricr.mcmachina.event.block.CBlockPlaceEvent;
import org.langricr.mcmachina.event.construct.ConstructCreateEvent;
import org.langricr.mcmachina.event.construct.ConstructDeleteEvent;
import org.langricr.mcmachina.event.construct.ConstructEvent;
import org.langricr.mcmachina.event.construct.ConstructLoadEvent;
import org.langricr.mcmachina.event.construct.ConstructSaveEvent;

public class EventListener {
	private static EventListener _instance = new EventListener();
	
	public static EventListener getInstance() {
		return _instance;
	}
	
	private Map< ConstructEvent.Type, List< Construct > > _listeners = new EnumMap< ConstructEvent.Type, List< Construct > >( ConstructEvent.Type.class );
	
	private EventListener() {
	}
	
	public void registerConstructEvent( Construct construct, Type type ) {
		if ( !( _listeners.get( type ).contains( construct ) ) )
			_listeners.get( type ).add( construct );
	}
	
	public void unregisterConstructEvent( Construct construct, Type type ) {
		if ( _listeners.get( type ).contains( construct ) )
			_listeners.get( type ).remove( construct );
	}
	
	public void unregisterConstruct( Construct construct ) {
		for ( Type type : Type.values() ) {
			unregisterConstructEvent( construct, type );
		}
	}
	
	public Event callEvent( Event event ) {
		for ( Construct construct : _listeners.get( event.getType() ) ) {
			switch ( event.getType() ) {
				case CONSTRUCT_CREATE:
					construct.onCreate( ( ConstructCreateEvent ) event );
					break;
				case CONSTRUCT_DELETE:
					construct.onDelete( ( ConstructDeleteEvent ) event );
					break;
				case CONSTRUCT_LOAD:
					construct.onLoad( ( ConstructLoadEvent ) event );
					break;
				case CONSTRUCT_SAVE:
					construct.onSave( ( ConstructSaveEvent ) event );
					break;
				case BLOCK_DAMAGED:
					construct.onBlockDamaged( ( CBlockDamageEvent ) event );
					break;
				case BLOCK_PLACED:
					construct.onBlockPlaced( ( CBlockPlaceEvent ) event );
					break;
			}
		}
		
		return event;
	}
}
