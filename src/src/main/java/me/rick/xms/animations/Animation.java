package me.rick.xms.animations;

import me.rick.xms.configs.Config;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Animation {

    private static void bloodAnimation(Player p) {
        p.getWorld().playEffect(p.getEyeLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
        p.getWorld().playEffect(p.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
    }

    private static void lightningAnimation(Player p) {
        p.getWorld().strikeLightningEffect(p.getLocation());
    }

    public static void sendAnimation(Player p) {
        if (Config.ANIMATION.equals("BLOOD")) {
            bloodAnimation(p);
        }
        if (Config.ANIMATION.equals("LIGHTNING")) {
            lightningAnimation(p);
        }
    }
}