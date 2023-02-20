package me.rick.xms.systems;

import me.clip.placeholderapi.PlaceholderAPI;
import me.rick.xms.XDeath;
import me.rick.xms.configs.Messages;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

public class Randomizer {

    public static String randomSound(List<String> sound) {
        Random r = new Random();
        int random_item = r.nextInt(sound.size());
        return sound.get(random_item);
    }

    public static String randomTitleOnDeathByPlayer(Player killer) {
        Random r = new Random();
        int random_item = r.nextInt(Messages.KILLED_BY_PLAYER_TITLES.size());
        String message = ChatColor.translateAlternateColorCodes('&', Messages.KILLED_BY_PLAYER_TITLES.get(random_item));
        if (XDeath.PLACEHOLDERAPI) {
            message = PlaceholderAPI.setPlaceholders(killer, message);
        }
        return message;
    }

    public static String randomSubTitleOnDeathByPlayer(Player killer) {
        Random r = new Random();
        int random_item = r.nextInt(Messages.KILLED_BY_PLAYER_SUBTITLES.size());
        String message = ChatColor.translateAlternateColorCodes('&', Messages.KILLED_BY_PLAYER_SUBTITLES.get(random_item));
        if (XDeath.PLACEHOLDERAPI) {
            message = PlaceholderAPI.setPlaceholders(killer, message);
        }
        return message;
    }

    public static String randomKillActionBar(Player victim) {
        Random r = new Random();
        int random_item = r.nextInt(Messages.ACTIONBAR_KILL.size());
        String message = ChatColor.translateAlternateColorCodes('&', Messages.ACTIONBAR_KILL.get(random_item));
        if (XDeath.PLACEHOLDERAPI) {
            message = PlaceholderAPI.setPlaceholders(victim, message);
        }
        return message;
    }

    public static String randomTitle(Player victim) {
        Random r = new Random();
        int random_item = r.nextInt(Messages.KILLED_TITLES.size());
        String message = ChatColor.translateAlternateColorCodes('&', Messages.KILLED_TITLES.get(random_item));
        if (XDeath.PLACEHOLDERAPI) {
            message = PlaceholderAPI.setPlaceholders(victim, message);
        }
        return message;
    }

    public static String randomSubTitle(Player victim) {
        Random r = new Random();
        int random_item = r.nextInt(Messages.KILLED_SUBTITLES.size());
        String message = ChatColor.translateAlternateColorCodes('&', Messages.KILLED_SUBTITLES.get(random_item));
        if (XDeath.PLACEHOLDERAPI) {
            message = PlaceholderAPI.setPlaceholders(victim, message);
        }
        return message;
    }
}
