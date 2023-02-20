package me.rick.xms.commands;

import me.rick.xms.configs.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainTabComplete implements TabCompleter {

    private static final String[] FIRST_COMPLETIONS = { "reload", "setspawn" };

    private static final String[] SECOND_COMPLETIONS = { "normal", "vip" };

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (sender.hasPermission(Config.ADMIN)) {
            if (args.length == 1) {
                final List<String> completions = new ArrayList<>();

                StringUtil.copyPartialMatches(args[0], Arrays.asList(FIRST_COMPLETIONS), completions);
                Collections.sort(completions);
                return completions;
            }
            if (args.length == 2 && args[0].equalsIgnoreCase("setspawn")) {
                final List<String> completions = new ArrayList<>();

                StringUtil.copyPartialMatches(args[1], Arrays.asList(SECOND_COMPLETIONS), completions);
                Collections.sort(completions);
                return completions;
            }
        }
        return new ArrayList<>();
    }
}
