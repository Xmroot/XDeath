package me.rick.xms.configs;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Messages {
    public static List<String> INCOMPATIBLE;
    public static List<String> ENABLED;
    public static List<String> DISABLED;
    public static String COMMAND_BLOCKED;
    public static String SPECTATE_BLOCKED;
    public static String HOTBAR_TELEPORT_BLOCKED;
    public static String ACTIONBAR_DEATH;
    public static String ACTIONBAR_HC;
    public static List<String> ACTIONBAR_KILL;
    public static List<String> KILLED_TITLES;
    public static List<String> KILLED_BY_PLAYER_TITLES;
    public static List<String> KILLED_SUBTITLES;
    public static List<String> KILLED_BY_PLAYER_SUBTITLES;
    public static String SINGULAR;
    public static String PLURAL;
    public static List<String> HELP;
    public static String RELOAD;
    public static String NO_PERM;
    public static String SPAWN_SET;
    public static String AB_ERROR;
    public static String TITLE_ERROR;
    public static String SOUND_ERROR;
    public static String SPAWN_ERROR;


    public static void loadMessages() {
        FileConfiguration messages = ConfigHandler.getConfig("messages_" + Config.LANGUAGE);

        INCOMPATIBLE = messages.getStringList("plugin.incompatible");
        ENABLED = messages.getStringList("plugin.enabled");
        DISABLED = messages.getStringList("plugin.disabled");

        COMMAND_BLOCKED = messages.getString("misc.commands-blocked");
        SPECTATE_BLOCKED = messages.getString("misc.spectate-blocked");
        HOTBAR_TELEPORT_BLOCKED = messages.getString("misc.hotbar-tp-blocked");

        ACTIONBAR_DEATH = messages.getString("actionbar.death");
        ACTIONBAR_HC = messages.getString("actionbar.hardcore");
        ACTIONBAR_KILL = messages.getStringList("actionbar.kill");

        KILLED_TITLES = messages.getStringList("titles.killed");
        KILLED_BY_PLAYER_TITLES = messages.getStringList("titles.killed-by-player");
        KILLED_SUBTITLES = messages.getStringList("subtitles.killed");
        KILLED_BY_PLAYER_SUBTITLES = messages.getStringList("subtitles.killed-by-player");

        SINGULAR = messages.getString("time.singular");
        PLURAL = messages.getString("time.plural");

        HELP = messages.getStringList("commands.help");
        RELOAD = messages.getString("commands.reload");
        NO_PERM = messages.getString("commands.no-perm");
        SPAWN_SET = messages.getString("commands.spawn-set");

        AB_ERROR = messages.getString("errors.actionbar");
        TITLE_ERROR = messages.getString("errors.title");
        SOUND_ERROR = messages.getString("errors.sound");
        SPAWN_ERROR = messages.getString("errors.spawn");
    }
}
