package org.langricr.mcmachina.listeners;


import org.bukkit.Material;
import org.bukkit.block.BlockDamageLevel;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRightClickEvent;
import org.langricr.mcmachina.construct.Construct;
import org.langricr.mcmachina.construct.ConstructLoader;
import org.langricr.mcmachina.construct.ConstructManager;
import org.langricr.mcmachina.construct.blueprint.Blueprint;
import org.langricr.mcmachina.construct.blueprint.BlueprintManager;
import org.langricr.mcmachina.event.EventListener;
import org.langricr.mcmachina.event.block.CBlockDamageEvent;
import org.langricr.mcmachina.event.block.CBlockPlaceEvent;
import org.langricr.mcmachina.event.block.CBlockRightClickEvent;
import org.langricr.util.Coordinate;

public class MMBlockListener extends BlockListener {
	public void onBlockDamage( BlockDamageEvent bde ) {
		if ( bde.getDamageLevel() == BlockDamageLevel.BROKEN && bde.getBlock().getType() == Material.GLOWSTONE ) {
			Construct construct = ConstructManager.getInstance().getConstruct( new Coordinate( bde.getBlock() ) );
			
			if ( construct != null )
				ConstructManager.getInstance().destroyConstruct( construct );
		}
		
		CBlockDamageEvent cbde = new CBlockDamageEvent( bde );
		
		EventListener.getInstance().callEvent( cbde );
		
		bde.setCancelled( cbde.isCancelled() );
	}
	
	public void onBlockPlace( BlockPlaceEvent bpe ) {
		CBlockPlaceEvent cbpe = new CBlockPlaceEvent( bpe );
		
		EventListener.getInstance().callEvent( cbpe );
		
		bpe.setCancelled( cbpe.isCancelled() );
	}
	
	public void onBlockRightClick( BlockRightClickEvent brce ) {
		CBlockRightClickEvent cbrce = new CBlockRightClickEvent( brce );
		
		if ( brce.getBlockAgainst().getType().equals( Material.GLOWSTONE ) && brce.getItemInHand().getType().equals( Material.GLOWSTONE_DUST ) ) {
			Coordinate target = new Coordinate( brce.getBlockAgainst() );
			
			if ( ConstructManager.getInstance().getConstruct( target ) != null )
				return;
			
			Blueprint blueprint = BlueprintManager.getInstance().scanCoordinate( new Coordinate( brce.getBlockAgainst() ) );
			
			if ( blueprint == null )
				return;
			
			Class< ? > clazz = ConstructLoader.getInstance().getClass( blueprint.getClassname() );
			
			if ( clazz == null )
				return;
			
			ConstructManager.getInstance().createConstruct( target, clazz );
		} else {
			EventListener.getInstance().callEvent( cbrce );
		}
	}
}
