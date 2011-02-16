package org.langricr.mcconstruct.construct;

import java.util.UUID;

import org.langricr.mcconstruct.event.block.CBlockDamageEvent;
import org.langricr.mcconstruct.event.block.CBlockPlaceEvent;
import org.langricr.mcconstruct.event.block.CBlockRightClickEvent;
import org.langricr.mcconstruct.event.construct.ConstructCreateEvent;
import org.langricr.mcconstruct.event.construct.ConstructDeleteEvent;
import org.langricr.mcconstruct.event.construct.ConstructDestroyEvent;
import org.langricr.mcconstruct.event.construct.ConstructLoadEvent;
import org.langricr.mcconstruct.event.construct.ConstructSaveEvent;
import org.langricr.util.WorldCoordinate;
import org.langricr.util.PolarCoordinate.Rotation;

public class Construct {
	private final WorldCoordinate core;
	private UUID uuid = UUID.randomUUID();
	private Rotation rotation;
	
	public Construct( WorldCoordinate core, Rotation rotation ) {
		this.core = core;
		this.rotation = rotation;
	}
	
	/**
	 * Returns the WorldCoordinate of the construct's core.
	 * @return The WorldCoordinate of the core.
	 */
	public final WorldCoordinate getCore() {
		return core;
	}
	
	public final Rotation getRotation() {
		return rotation;
	}
	
	public final void setRotation( Rotation rotation ) {
		this.rotation = rotation;
	}
	
	/**
	 * Returns the UUID of the construct.
	 * @return The UUID of the construct.
	 */
	public final UUID getUUID() {
		return uuid;
	}
	
	/**
	 * Sets the UUID of the construct.
	 * @param uuid The UUID to be set.
	 */
	public final void setUUID( UUID uuid ) {
		this.uuid = uuid;
	}
	
	/**
	 * Fired when a construct is created.
	 * @param cce The event that fired.
	 */
	public void onConstructCreated( ConstructCreateEvent cce ) {}
	
	/**
	 * Fired when a construct is deleted.
	 * @param cde The event that fired.
	 */
	public void onConstructDeleted( ConstructDeleteEvent cde ) {}
	
	/**
	 * Fired when a construct is destroyed, calls a ConstructDeleteEvent if it is not cancelled.
	 * @param cde
	 */
	public void onConstructDestroyed( ConstructDestroyEvent cde ) {}
	
	/**
	 * Fired when a construct attempts to save.
	 * @param cse The event that fired.
	 */
	public void onConstructSave( ConstructSaveEvent cse ) {}
	
	/**
	 * Fired when a construct attempts to load.
	 * @param cle The event that fired.
	 */
	public void onConstructLoad( ConstructLoadEvent cle ) {}
	
	/**
	 * Fired when a block is damaged.
	 * @param bde The event that fired.
	 */
	public void onBlockDamaged( CBlockDamageEvent bde ) {}
	
	/**
	 * Fired when a block is placed.
	 * @param bpe The event that fired.
	 */
	public void onBlockPlaced( CBlockPlaceEvent bpe ) {}
	
	/**
	 * Fired when a block is right-clicked.
	 * @param cbrce The event that fired.
	 */
	public void onBlockRightClicked( CBlockRightClickEvent cbrce ) {}
	
	public String toString() {
		return "(" + getClass().getName() + "|" + getCore().toString() + ")";
	}
}
