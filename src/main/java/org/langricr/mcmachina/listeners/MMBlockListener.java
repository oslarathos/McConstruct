package org.langricr.mcmachina.listeners;


import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.langricr.mcmachina.event.EventListener;
import org.langricr.mcmachina.event.block.CBlockDamageEvent;
import org.langricr.mcmachina.event.block.CBlockPlaceEvent;

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
}
