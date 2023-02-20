package me.rick.xms.api;

import org.bukkit.Bukkit;

public enum Version {

    v1_20(20),
    v1_19(19),
    v1_18(18),
    v1_17(17),
    v1_16(16),
    v1_15(15),
    v1_14(14),
    v1_13(13),
    v1_12(12),
    v1_11(11),
    v1_10(10),
    v1_9(9),
    v1_8(8),
    UNKNOWN(0);

    public static Version getServerVersion() {
        Version version = getServerVersion(getMinecraftVersion());
        if (version == UNKNOWN) {
            version = getServerVersion(Bukkit.getVersion());
        }
        return version;
    }

    public static String getMinecraftVersion() {
        try {
            String info = Bukkit.getVersion();
            return info.split("MC: ")[1].split("\\)")[0];
        } catch (Throwable e) {
            return "Unknown";
        }
    }

    public static Version getServerVersion(String ver) {
        if (ver.contains("1.20"))
            return v1_19;
        else if (ver.contains("1.19"))
            return v1_19;
        else if (ver.contains("1.18"))
            return v1_18;
        else if (ver.contains("1.17"))
            return v1_17;
        else if (ver.contains("1.16"))
            return v1_16;
        else if (ver.contains("1.15"))
            return v1_15;
        else if (ver.contains("1.14"))
            return v1_14;
        else if (ver.contains("1.13"))
            return v1_13;
        else if (ver.contains("1.12"))
            return v1_12;
        else if (ver.contains("1.11"))
            return v1_11;
        else if (ver.contains("1.10"))
            return v1_10;
        else if (ver.contains("1.9"))
            return v1_9;
        else if (ver.contains("1.8"))
            return v1_8;
        else
            return UNKNOWN;
    }

    public final int value;

    Version(int value) {
        this.value = value;
    }
}