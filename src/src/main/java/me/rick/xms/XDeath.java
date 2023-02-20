package me.rick.xms;

import me.rick.xms.api.Version;
import me.rick.xms.configs.ConfigHandler;
import me.rick.xms.configs.Messages;
import me.rick.xms.events.Listeners;
import me.rick.xms.commands.MainCommand;
import me.rick.xms.commands.MainTabComplete;
import me.rick.xms.configs.Config;
import me.rick.xms.systems.Tasks;
import org.bukkit.Bukkit;
import org.bstats.bukkit.Metrics;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class XDeath extends JavaPlugin {

    public static XDeath plugin;
    public static Version version;
    public static boolean PLACEHOLDERAPI = false;
    PluginDescriptionFile pdf = this.getDescription();

    public static void logger(String text) {
        Bukkit.getConsoleSender().sendMessage("[XDeath] " + text);
    }

    public static boolean veryNewVersion() {
        if (version == Version.v1_20)
            return true;
        if (version == Version.v1_19)
            return true;
        if (version == Version.v1_18)
            return true;
        if (version == Version.v1_17)
            return true;
        return false;
    }

    public static boolean newVersion() {
        if (version == Version.v1_16)
            return true;
        if (version == Version.v1_15)
            return true;
        if (version == Version.v1_14)
            return true;
        if (version == Version.v1_13)
            return true;
        if (version == Version.v1_12)
            return true;
        return false;
    }

    public static boolean oldVersion() {
        if (version == Version.v1_11)
            return true;
        if (version == Version.v1_10)
            return true;
        if (version == Version.v1_9)
            return true;
        if (version == Version.v1_8)
            return true;
        return false;
    }

    public static void createAndLoadConfigs() {
        ConfigHandler.createConfig("config");
        ConfigHandler.createConfig("locations");
        Config.loadConfigs();
        try {
            ConfigHandler.createConfig("messages_" + Config.LANGUAGE);
        } catch (RuntimeException e) {
            logger("The plugin will shutdown, because " + Config.LANGUAGE + " does not exist on the configurations.");
            logger("O plugin será desligado, porque " + Config.LANGUAGE + " não existe nas configurações.");
            plugin.getPluginLoader().disablePlugin(plugin);
            return;
        }
        Messages.loadMessages();
    }

    @Override
    public void onEnable() {
        plugin = this;
        version = Version.getServerVersion();
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            PLACEHOLDERAPI = true;
        }
        if (version == Version.UNKNOWN) {
            for (String incompatible : Messages.INCOMPATIBLE) {
                logger(ChatColor.translateAlternateColorCodes('&', incompatible.replace("%server_version%", "(" + Version.getServerVersion() + ")")));
            }
            getPluginLoader().disablePlugin(plugin);
            return;
        }
        createAndLoadConfigs();
        Listeners.setup();
        getCommand("xms").setExecutor(new MainCommand());
        getCommand("xms").setTabCompleter(new MainTabComplete());
        int pluginId = 17720;
        Metrics metrics = new Metrics(this, pluginId);
        for (String enabled : Messages.ENABLED) {
            logger(ChatColor.translateAlternateColorCodes('&', enabled.replace("%plugin_version%", "(v" + pdf.getVersion() + ")")));
        }
        logger("§fMinecraft " + version.toString().replace("_", ".").replace("v", ""));
    }

    @Override
    public void onDisable() {
        for (String disabled : Messages.DISABLED) {
            logger(ChatColor.translateAlternateColorCodes('&', disabled.replace("%plugin_version%", "(v" + pdf.getVersion() + ")")));
        }
        for (Player ps : Bukkit.getOnlinePlayers()) {
            if (Config.DEAD_PLAYERS.contains(ps.getName())) {
                Tasks.performRespawn(ps);
            }
        }
    }
}