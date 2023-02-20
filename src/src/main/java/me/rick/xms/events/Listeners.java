package me.rick.xms.events;

import me.rick.xms.events.bukkit.*;
import me.rick.xms.events.packets.DeathPacketListener;
import me.rick.xms.events.packets.SpectatorPacketLimiter;
import me.rick.xms.XDeath;
import me.rick.xms.events.bukkit.*;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class Listeners implements Listener {

    public static void setup() {
        XDeath pl = XDeath.plugin;
        PluginManager pm = Bukkit.getPluginManager();
        // Bukkit
        pm.registerEvents(new EntityDamageListener(), pl);
        pm.registerEvents(new EntityRegainHealthListener(), pl);
        pm.registerEvents(new PlayerCommandListener(), pl);
        pm.registerEvents(new PlayerDeathListener(), pl);
        pm.registerEvents(new PlayerConnectionListener(), pl);
        pm.registerEvents(new PlayerTeleportListener(), pl);
        // Packets
        DeathPacketListener.cancelDeathScreen();
        SpectatorPacketLimiter.cancelSpectate();
    }
}
