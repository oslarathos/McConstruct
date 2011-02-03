package org.langricr.mcmachina.listeners;


import org.bukkit.Material;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRightClickEvent;
import org.langricr.mcmachina.construct.ConstructManager;
import org.langricr.mcmachina.construct.blueprint.Blueprint;
import org.langricr.mcmachina.construct.blueprint.BlueprintManager;
import org.langricr.mcmachina.event.EventListener;
import org.langricr.mcmachina.event.block.CBlockDamageEvent;
import org.langricr.mcmachina.event.block.CBlockPlaceEvent;
import org.langricr.util.Coordinate;

public class MMBlockListener extends BlockListener {
	public void onBlockDamage( BlockDamageEvent bde ) {
		CBlockDamageEvent cbde = new CBlockDamageEvent( bde.getBlock(), bde.getDamageLevel(), bde );
		
		EventListener.getInstance().callEvent( cbde );
		
		bde.setCancelled( cbde.isCancelled() );
	}
	
	public void onBlockPlace( BlockPlaceEvent bpe ) {
		CBlockPlaceEvent cbpe = new CBlockPlaceEvent( bpe.getBlock(), bpe );
		
		EventListener.getInstance().callEvent( cbpe );
		
		bpe.setCancelled( cbpe.isCancelled() );
	}
	
	public void onBlockRightClick( BlockRightClickEvent brce ) {
		if ( brce.getBlockAgainst().getType().equals( Material.GLOWSTONE ) && brce.getItemInHand().getType().equals( Material.GLOWSTONE_DUST ) ) {
			Coordinate target = new Coordinate( brce.getBlockAgainst() );
			
			if ( ConstructManager.getInstance().getConstruct( target ) != null )
				return;
			
			Blueprint blueprint = BlueprintManager.getInstance().scanCoordinate( new Coordinate( brce.getBlockAgainst() ) );
			
			if ( blueprint == null )
				return;
		}
	}
}