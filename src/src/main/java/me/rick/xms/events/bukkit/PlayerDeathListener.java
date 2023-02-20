package me.rick.xms.events.bukkit;

import me.rick.xms.configs.Config;
import me.rick.xms.events.Listeners;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener extends Listeners {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();

        if (p.hasPermission(Config.KEEP_XP)) {
            e.setDroppedExp(0);
            e.setKeepLevel(true);
        }
    }
}
