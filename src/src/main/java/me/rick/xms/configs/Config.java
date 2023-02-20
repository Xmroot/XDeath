package me.rick.xms.configs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Config {

    public static List<String> DEAD_PLAYERS = new ArrayList<>();
    public static boolean USE_PACKET_EVENT_HANDLER;
    public static String LANGUAGE;
    public static boolean WORLD_BORDER_EFFECT;
    public static boolean USE_DEFAULT_WORLD_SPAWN;
    public static boolean USE_SAFE_TELEPORT;
    public static boolean USE_KILL_CAM;
    public static Location SPAWN;
    public static Location VIP_SPAWN;
    public static List<String> SOUND_DEATH;
    public static List<String> SOUND_COUNTDOWN;
    public static List<String> SOUND_RESPAWN;
    public static List<String> SOUND_KILL;
    public static Float SOUND_DEATH_VOLUME;
    public static Float SOUND_COUNTDOWN_VOLUME;
    public static Float SOUND_RESPAWN_VOLUME;
    public static Float SOUND_KILL_VOLUME;
    public static Float SOUND_DEATH_PITCH;
    public static Float SOUND_COUNTDOWN_PITCH;
    public static Float SOUND_RESPAWN_PITCH;
    public static Float SOUND_KILL_PITCH;
    public static int TIME;
    public static String INSTANT_RESPAWN;
    public static String VIP;
    public static String KEEP_XP;
    public static String ADMIN;
    public static String ANIMATION;
    public static boolean QUEUE_TELEPORT;
    public static boolean SPECTATE_ENTITY;
    public static boolean HOTBAR_TELEPORT_SPECTATOR;
    public static boolean MOVE_SPECTATOR;
    public static boolean ALLOW_COMMANDS_WHILE_DEAD;

    public static void loadConfigs() {
        FileConfiguration config = ConfigHandler.getConfig("config");
        FileConfiguration locations = ConfigHandler.getConfig("locations");

        LANGUAGE = config.getString("misc.language");
        USE_PACKET_EVENT_HANDLER = config.getBoolean("misc.use-packet-event-handler");
        WORLD_BORDER_EFFECT = config.getBoolean("misc.world-border-effect");
        USE_DEFAULT_WORLD_SPAWN = config.getBoolean("misc.use-default-world-spawn");
        USE_SAFE_TELEPORT = config.getBoolean("misc.use-safe-teleport");
        USE_KILL_CAM = config.getBoolean("misc.use-kill-camera");

        SPAWN = new Location(Bukkit.getWorld(locations.getString("normal.world")), locations.getDouble("normal.X"), locations.getDouble("normal.Y"), locations.getDouble("normal.Z"), (float) locations.getDouble("normal.yaw"), (float) locations.getDouble("normal.pitch"));
        VIP_SPAWN = new Location(Bukkit.getWorld(locations.getString("vip.world")), locations.getDouble("vip.X"), locations.getDouble("vip.Y"), locations.getDouble("vip.Z"), (float) locations.getDouble("vip.yaw"), (float) locations.getDouble("vip.pitch"));

        SOUND_DEATH = config.getStringList("sound.type.death");
        SOUND_COUNTDOWN = config.getStringList("sound.type.countdown");
        SOUND_RESPAWN = config.getStringList("sound.type.respawn");
        SOUND_KILL = config.getStringList("sound.type.kill");

        SOUND_DEATH_VOLUME = (float) config.getDouble("sound.volume.death");
        SOUND_COUNTDOWN_VOLUME = (float) config.getDouble("sound.volume.countdown");
        SOUND_RESPAWN_VOLUME = (float) config.getDouble("sound.volume.respawn");
        SOUND_KILL_VOLUME = (float) config.getDouble("sound.volume.kill");

        SOUND_DEATH_PITCH = (float) config.getDouble("sound.pitch.death");
        SOUND_COUNTDOWN_PITCH = (float) config.getDouble("sound.pitch.countdown");
        SOUND_RESPAWN_PITCH = (float) config.getDouble("sound.pitch.respawn");
        SOUND_KILL_PITCH = (float) config.getDouble("sound.pitch.kill");

        TIME = config.getInt("time.duration");

        INSTANT_RESPAWN = config.getString("permissions.instant-respawn");
        VIP = config.getString("permissions.vip-spawn");
        KEEP_XP = config.getString("permissions.keep-xp");
        ADMIN = config.getString("permissions.admin");

        ANIMATION = config.getString("animation.type");

        QUEUE_TELEPORT = config.getBoolean("spectator-settings.queue-teleport");
        SPECTATE_ENTITY = config.getBoolean("spectator-settings.allow-spectate");
        HOTBAR_TELEPORT_SPECTATOR = config.getBoolean("spectator-settings.allow-teleport-with-keys");
        MOVE_SPECTATOR = config.getBoolean("spectator-settings.allow-move");
        ALLOW_COMMANDS_WHILE_DEAD = config.getBoolean("spectator-settings.allow-commands");
    }
}
