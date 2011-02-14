package org.langricr.mcconstruct.listeners;


import org.bukkit.Material;
import org.bukkit.block.BlockDamageLevel;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRightClickEvent;
import org.langricr.mcconstruct.McConstruct;
import org.langricr.mcconstruct.construct.Construct;
import org.langricr.mcconstruct.construct.ConstructLoader;
import org.langricr.mcconstruct.construct.ConstructManager;
import org.langricr.mcconstruct.construct.blueprint.Blueprint;
import org.langricr.mcconstruct.construct.blueprint.BlueprintManager;
import org.langricr.mcconstruct.event.EventListener;
import org.langricr.mcconstruct.event.block.CBlockDamageEvent;
import org.langricr.mcconstruct.event.block.CBlockPlaceEvent;
import org.langricr.mcconstruct.event.block.CBlockRightClickEvent;
import org.langricr.mcconstruct.event.construct.ConstructDestroyEvent;
import org.langricr.util.WorldCoordinate;

import com.nijiko.permissions.PermissionHandler;

public class MMBlockListener extends BlockListener {
	public void onBlockDamage( BlockDamageEvent bde ) {
		CBlockDamageEvent cbde = new CBlockDamageEvent( bde );
		
		EventListener.getInstance().callEvent( cbde );
		
		if ( cbde.isCancelled() ) {
			bde.setCancelled( true );
		} else {
			if ( bde.getDamageLevel().equals( BlockDamageLevel.BROKEN ) && bde.getBlock().getType().equals( Material.GLOWSTONE ) ) {
				PermissionHandler p = McConstruct.getInstance().getPermissions();
				
				if ( p != null && !( p.has( bde.getPlayer(), "mcconstruct.alter.destroy" ) ) ) {
					bde.setCancelled( true );
					
					return;
				}
				
				Construct construct = ConstructManager.getInstance().getConstruct( new WorldCoordinate( bde.getBlock() ) );
				
				ConstructDestroyEvent cde = new ConstructDestroyEvent( construct );
				
				EventListener.getInstance().callEvent( cde );
				
				if ( cde.isCancelled() ) {
					bde.setCancelled( true );
					
					return;
				}
				
				ConstructManager.getInstance().deleteConstruct( construct );
			}
		}
	}
	
	public void onBlockPlace( BlockPlaceEvent bpe ) {
		CBlockPlaceEvent cbpe = new CBlockPlaceEvent( bpe );
		
		EventListener.getInstance().callEvent( cbpe );
		
		bpe.setCancelled( cbpe.isCancelled() );
	}
	
	public void onBlockRightClick( BlockRightClickEvent brce ) {
		PermissionHandler perms = McConstruct.getInstance().getPermissions();
		
		if ( perms != null && !( perms.has( brce.getPlayer(), "mcconstruct.alter.create" ) ) ) {
			CBlockRightClickEvent cbrce = new CBlockRightClickEvent( brce );
			
			EventListener.getInstance().callEvent( cbrce );
			
			return;
		} else if ( brce.getBlock().getType() == Material.GLOWSTONE && brce.getItemInHand().getType() == Material.GLOWSTONE_DUST ) {
			// Getting the coordinate
			WorldCoordinate coord = new WorldCoordinate( brce.getBlock() );
			
			// Checking if the coordinate exists.
			if ( ConstructManager.getInstance().getConstruct( coord ) != null ) {
				System.out.println( "Construct Creation: Coordinate is already registered." );
				
				return;
			}
			
			// Getting the blueprint
			Blueprint blueprint = BlueprintManager.getInstance().scanCoordinate( coord );
			
			// Checking if the blueprint exists.
			if ( blueprint == null ) {
				System.out.println( "Construct Creation: No blueprint found.");
				
				return;
			}
			
			// Getting the class of the blueprint
			Class< ? > clazz = ConstructLoader.getInstance().getClass( blueprint.getClassname() );
			
			// Checking if the class exists.
			if ( clazz == null ) {
				System.out.println( "The blueprint for '" + blueprint.getClassname() +"' has no matching construct class loaded." );
				
				return;
			}
			
			// Creating the construct
			ConstructManager.getInstance().createConstruct( clazz, coord );
		} else {
			// Creating an event
			CBlockRightClickEvent cbrce = new CBlockRightClickEvent( brce );
			
			// Calling an event
			EventListener.getInstance().callEvent( cbrce );
		}
	}
}
