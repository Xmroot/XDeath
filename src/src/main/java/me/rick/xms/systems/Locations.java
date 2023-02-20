package me.rick.xms.systems;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

public class Locations {

    public static void teleportSafeLocation(Player p, Location location) {
        int y = location.getWorld().getHighestBlockYAt(location);
        for (int i = y; i > 0; i--) {
            Location loc = new Location(location.getWorld(), location.getX(), i, location.getZ());
            Material type = loc.getBlock().getType();
            if (type != Material.AIR && type != Material.LAVA && type != Material.WATER) {
                p.teleport(loc.add(0,1.1,0), PlayerTeleportEvent.TeleportCause.PLUGIN);
                break;
            }
        }
    }
}
