package me.rick.xms.configs;

import me.rick.xms.XDeath;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigHandler {

    public static void createConfig(String file) {
        if (!new File(XDeath.plugin.getDataFolder(), file + ".yml").exists()) {
            XDeath.plugin.saveResource(file + ".yml", false);
        }
    }

    public static FileConfiguration getConfig(String file) {
        File new_file = new File(XDeath.plugin.getDataFolder() + File.separator + file + ".yml");
        return YamlConfiguration.loadConfiguration(new_file);
    }

    public static FileConfiguration getSavedConfiguration(File file) {
        return YamlConfiguration.loadConfiguration(file);
    }

    public static File getFile(String file) {
        return new File(XDeath.plugin.getDataFolder() + File.separator + file + ".yml");
    }
}

