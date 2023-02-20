package me.rick.xms.events.bukkit;

import me.rick.xms.configs.Messages;
import me.rick.xms.XDeath;
import me.rick.xms.configs.Config;
import me.rick.xms.events.Listeners;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class PlayerCommandListener extends Listeners {

    public static List<String> COMMAND_MESSAGE_CD = new ArrayList<>();

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onCommandPreProcess(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();

        if (Config.DEAD_PLAYERS.contains(p.getName()) && !Config.ALLOW_COMMANDS_WHILE_DEAD) {
            e.setCancelled(true);
            if (!COMMAND_MESSAGE_CD.contains(p.getName())) {
                COMMAND_MESSAGE_CD.add(p.getName());
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.COMMAND_BLOCKED));
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        COMMAND_MESSAGE_CD.remove(p.getName());
                    }
                }.runTaskLaterAsynchronously(XDeath.plugin, 20*3);
            }
        }
    }
}
