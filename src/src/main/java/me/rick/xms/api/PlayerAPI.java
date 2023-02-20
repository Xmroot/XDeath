package me.rick.xms.api;

import me.rick.xms.XDeath;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerAPI {

    public static boolean isStackEmpty(ItemStack stack) {
        return stack == null || stack.getType() == Material.AIR || stack.getAmount() == 0;
    }

    public static void dropInventory(Player player) {
        if (player.getWorld().getGameRuleValue("keepInventory").equals("false")) {
            player.closeInventory();
            player.updateInventory();
            List<ItemStack> filtered_inventory = Arrays.stream(player.getInventory().getContents())
                    .filter(stack -> !PlayerAPI.isStackEmpty(stack)).collect(Collectors.toList());
            if (XDeath.version == Version.v1_8) {
                List<ItemStack> armor = Arrays.stream(player.getInventory().getArmorContents())
                        .filter(stack -> !PlayerAPI.isStackEmpty(stack))
                        .collect(Collectors.toList());
                filtered_inventory.addAll(armor);
            }
            for (ItemStack items : filtered_inventory) {
                player.getWorld().dropItemNaturally(player.getLocation(), items);
            }
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
        }
    }
}
