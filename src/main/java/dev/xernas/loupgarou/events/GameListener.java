package dev.xernas.loupgarou.events;

import dev.xernas.loupgarou.GameManager;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class GameListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (!GameManager.inGame) {
            return;
        }
        if (!GameManager.getPlayers().contains(e.getPlayer())) {
            return;
        }
        Location from = e.getFrom();
        Location to = e.getTo();
        if (from.getBlockX() != to.getBlockX() || from.getBlockZ() != to.getBlockZ()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (!GameManager.inGame) {
            return;
        }
        if (!GameManager.getPlayers().contains(e.getPlayer())) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (!GameManager.inGame) {
            return;
        }
        if (!GameManager.getPlayers().contains(e.getPlayer())) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (!GameManager.inGame) {
            return;
        }
        if (!GameManager.getPlayers().contains(e.getPlayer())) {
            return;
        }
        e.setCancelled(true);
    }

}
