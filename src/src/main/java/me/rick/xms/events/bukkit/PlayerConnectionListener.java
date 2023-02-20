package me.rick.xms.events.bukkit;

import me.rick.xms.configs.Config;
import me.rick.xms.events.Listeners;
import me.rick.xms.systems.Tasks;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionListener extends Listeners {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if ((p.getGameMode() == GameMode.SPECTATOR && !p.hasPermission(Config.ADMIN)) || Config.DEAD_PLAYERS.contains(p.getName())) {
            if (Bukkit.getServer().isHardcore()) {
                Config.DEAD_PLAYERS.add(p.getName());
                Tasks.hardcoreTimer(p);
            }
        }
        // Desbugando o respawn em outras dimensões.
        if (p.getBedSpawnLocation() != null) {
            if (p.getBedSpawnLocation().getWorld().getEnvironment() != World.Environment.NORMAL) {
                p.setBedSpawnLocation(null);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        // Para evitar bugs, o jogador renasce ao desconectar.
        if ((p.getGameMode() == GameMode.SPECTATOR && !p.hasPermission(Config.ADMIN)) || Config.DEAD_PLAYERS.contains(p.getName())) {
            if (!Bukkit.getServer().isHardcore()) {
                Tasks.performRespawn(p);
            }
        }
    }
}
