package org.langricr.mcmachina.event;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.langricr.mcmachina.construct.Construct;
import org.langricr.mcmachina.event.Event.Type;
import org.langricr.mcmachina.event.block.CBlockDamageEvent;
import org.langricr.mcmachina.event.block.CBlockPlaceEvent;
import org.langricr.mcmachina.event.block.CBlockRightClickEvent;
import org.langricr.mcmachina.event.construct.ConstructCreateEvent;
import org.langricr.mcmachina.event.construct.ConstructDeleteEvent;
import org.langricr.mcmachina.event.construct.ConstructDestroyEvent;
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
		for ( Type type : Type.values() ) {
			_listeners.put( type, new ArrayList< Construct >() );
		}
	}
	
	public synchronized void registerConstructEvent( Construct construct, Type type ) {
		if ( !( _listeners.get( type ).contains( construct ) ) )
			_listeners.get( type ).add( construct );
	}
	
	public synchronized void unregisterConstructEvent( Construct construct, Type type ) {
		if ( _listeners.get( type ).contains( construct ) )
			_listeners.get( type ).remove( construct );
	}
	
	public synchronized void unregisterConstruct( Construct construct ) {
		for ( Type type : Type.values() ) {
			unregisterConstructEvent( construct, type );
		}
	}
	
	public synchronized Event callEvent( Event event ) {
		for ( Construct construct : _listeners.get( event.getType() ) ) {
			switch ( event.getType() ) {
				case CONSTRUCT_CREATE:
					construct.onCreate( ( ConstructCreateEvent ) event );
					break;
				case CONSTRUCT_DELETE:
					construct.onDelete( ( ConstructDeleteEvent ) event );
					break;
				case CONSTRUCT_DESTROY:
					construct.onDestroy( ( ConstructDestroyEvent ) event );
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
				case BLOCK_RIGHTCLICKED:
					construct.onBlockRightClicked( ( CBlockRightClickEvent ) event );
					break;
			}
		}
		
		return event;
	}
}
