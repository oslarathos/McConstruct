package org.langricr.mcconstruct.listeners;


import org.bukkit.Material;
import org.bukkit.block.BlockDamageLevel;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRightClickEvent;
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

public class MMBlockListener extends BlockListener {
	public void onBlockDamage( BlockDamageEvent bde ) {
		if ( bde.getDamageLevel().equals( BlockDamageLevel.BROKEN ) && bde.getBlock().getType().equals( Material.GLOWSTONE ) ) {
			Construct construct = ConstructManager.getInstance().getConstruct( new WorldCoordinate( bde.getBlock() ) );
			
			if ( construct != null ) {
				ConstructManager.getInstance().destroyConstruct( construct );
			
				return;
			}
		}
		
		CBlockDamageEvent cbde = new CBlockDamageEvent( bde );
		
		EventListener.getInstance().callEvent( cbde );
		
		if ( bde.isCancelled() )
			bde.setCancelled( true );
		 
	}
	
	public void onBlockPlace( BlockPlaceEvent bpe ) {
		CBlockPlaceEvent cbpe = new CBlockPlaceEvent( bpe );
		
		EventListener.getInstance().callEvent( cbpe );
		
		bpe.setCancelled( cbpe.isCancelled() );
	}
	
	public void onBlockRightClick( BlockRightClickEvent brce ) {
		if ( brce.getBlock().getType() == Material.GLOWSTONE && brce.getItemInHand().getType() == Material.GLOWSTONE_DUST ) {
			WorldCoordinate coord = new WorldCoordinate( brce.getBlock() );
			
			if ( ConstructManager.getInstance().getConstruct( coord ) != null )
				return;
			
			Blueprint blueprint = BlueprintManager.getInstance().scanCoordinate( coord );
			
			if ( blueprint == null )
				return;
			
			Class< ? > clazz = ConstructLoader.getInstance().getClass( blueprint.getClassname() );
			
			if ( clazz == null ) {
				System.out.println( "The blueprint for '" + blueprint.getClassname() +"' has no matching construct class loaded." );
				
				return;
			}
			
			ConstructManager.getInstance().createConstruct( clazz, coord );
		} else {
			CBlockRightClickEvent cbrce = new CBlockRightClickEvent( brce );
			
			EventListener.getInstance().callEvent( cbrce );
		}
	}
}
