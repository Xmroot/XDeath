package me.rick.xms.events.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.rick.xms.configs.Messages;
import me.rick.xms.XDeath;
import me.rick.xms.configs.Config;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class SpectatorPacketLimiter {

    public static List<String> SPECTATOR_MESSAGE_CD = new ArrayList<>();

    public static void cancelSpectate() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(XDeath.plugin, PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if (Config.DEAD_PLAYERS.contains(p.getName()) && !Config.SPECTATE_ENTITY) {
                    e.setCancelled(true);
                    if (!SPECTATOR_MESSAGE_CD.contains(p.getName())) {
                        SPECTATOR_MESSAGE_CD.add(p.getName());
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.SPECTATE_BLOCKED));
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                SPECTATOR_MESSAGE_CD.remove(p.getName());
                            }
                        }.runTaskLaterAsynchronously(XDeath.plugin, 20*3);
                    }
                }
            }
        });
    }
}
