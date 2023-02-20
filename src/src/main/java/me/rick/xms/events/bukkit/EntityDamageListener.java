package me.rick.xms.events.bukkit;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.messages.Titles;
import me.rick.xms.configs.Messages;
import me.rick.xms.XDeath;
import me.rick.xms.animations.Animation;
import me.rick.xms.api.PlayerAPI;
import me.rick.xms.api.Version;
import me.rick.xms.configs.Config;
import me.rick.xms.events.Listeners;
import me.rick.xms.systems.Randomizer;
import me.rick.xms.systems.Tasks;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityDamageListener extends Listeners {

    private static boolean handChecker(Player player, EntityDamageEvent event) {
        if (!(XDeath.version == Version.v1_8)) {
            return ((player.getInventory().getItemInMainHand().getType() != XMaterial.TOTEM_OF_UNDYING.parseMaterial() && player.getInventory().getItemInOffHand().getType() != XMaterial.TOTEM_OF_UNDYING.parseMaterial()) || event.getCause() == EntityDamageEvent.DamageCause.SUICIDE || event.getCause() == EntityDamageEvent.DamageCause.VOID);
        }
        return true;
    }

    private static void sendEventsBukkit(Player victim) {
        Config.DEAD_PLAYERS.add(victim.getName());
        String random_death_sound = Randomizer.randomSound(Config.SOUND_DEATH);

        for (PotionEffect pe : victim.getActivePotionEffects()) {
            victim.removePotionEffect(pe.getType());
        }
        if (victim.getWorld().getGameRuleValue("keepInventory").equals("false")) {
            List<ItemStack> filtered_inventory = Arrays.stream(victim.getInventory().getContents())
                    .filter(stack -> !PlayerAPI.isStackEmpty(stack)).collect(Collectors.toList());
            if (XDeath.version == Version.v1_8) {
                List<ItemStack> armor = Arrays.stream(victim.getInventory().getArmorContents())
                        .filter(stack -> !PlayerAPI.isStackEmpty(stack))
                        .collect(Collectors.toList());
                filtered_inventory.addAll(armor);
            }
            PlayerDeathEvent death = new PlayerDeathEvent(victim, filtered_inventory, Math.min(100, victim.getLevel() * 7), null);
            Bukkit.getPluginManager().callEvent(death);
            if (!victim.hasPermission(Config.KEEP_XP)) {
                victim.setLevel(0);
                victim.setExp(0);
            }
            PlayerAPI.dropInventory(victim);
        }
        if (victim.getWorld().getGameRuleValue("keepInventory").equals("true")) {
            PlayerDeathEvent death = new PlayerDeathEvent(victim, new ArrayList<>(), 0, null);
            Bukkit.getPluginManager().callEvent(death);
        }
        if (!Config.MOVE_SPECTATOR) {
            victim.setWalkSpeed(0F);
            victim.setFlySpeed(0F);
        }
        victim.setHealth(0.1);
        victim.setGameMode(GameMode.SPECTATOR);
        victim.setStatistic(Statistic.TIME_SINCE_DEATH, 0);
        victim.incrementStatistic(Statistic.DEATHS, 1);
        try {
            victim.playSound(victim.getLocation(), Sound.valueOf(Randomizer.randomSound(Config.SOUND_DEATH)), Config.SOUND_DEATH_VOLUME, Config.SOUND_DEATH_PITCH);
        } catch (Exception e) {
            XDeath.logger(ChatColor.translateAlternateColorCodes('&', Messages.SOUND_ERROR).replace("%sound%", random_death_sound));
        }
        if (!Bukkit.getServer().isHardcore()) {
            Tasks.normalTimer(victim);
        }
        if (Bukkit.getServer().isHardcore()) {
            Tasks.hardcoreTimer(victim);
        }
        Animation.sendAnimation(victim);
    }

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        Entity victim = event.getEntity();
        int time = Config.TIME;
        if (time <= 0) {
            time = 1;
        }

        if (victim instanceof Player) {
            Player pv = (Player) victim;
            String random_kill_sound = Randomizer.randomSound(Config.SOUND_KILL);

            if (Config.DEAD_PLAYERS.contains(pv.getName())) {
                event.setCancelled(true);
                return;
            }

            if (pv.getHealth() <= event.getFinalDamage() && handChecker(pv, event)) {
                if (!Config.USE_PACKET_EVENT_HANDLER) {
                    event.setCancelled(true);
                    try {
                        pv.incrementStatistic(Statistic.DAMAGE_TAKEN, (int) event.getFinalDamage());
                    } catch (Exception e) {
                        pv.incrementStatistic(Statistic.DAMAGE_TAKEN, (int) pv.getHealth());
                    }
                    sendEventsBukkit(pv);
                }
                Titles.sendTitle(pv, 2, 20 * time, 2, Randomizer.randomTitle(pv), Randomizer.randomSubTitle(pv));
                if (event instanceof EntityDamageByBlockEvent) {
                    Block damager = ((EntityDamageByBlockEvent) event).getDamager();
                    if (!Config.USE_PACKET_EVENT_HANDLER) {
                        try {
                            EntityDamageByBlockEvent block_damage = new EntityDamageByBlockEvent(damager, victim, event.getCause(), event.getDamage());
                            Bukkit.getPluginManager().callEvent(block_damage);
                        } catch (Exception e) {
                            EntityDamageByBlockEvent block_damage = new EntityDamageByBlockEvent(damager, victim, event.getCause(), pv.getHealth());
                            Bukkit.getPluginManager().callEvent(block_damage);
                        }
                    }
                }
                if (event instanceof EntityDamageByEntityEvent) {
                    Entity damager = ((EntityDamageByEntityEvent) event).getDamager();
                    if (Config.USE_KILL_CAM && !Config.USE_PACKET_EVENT_HANDLER) {
                        pv.setSpectatorTarget(damager);
                    }
                    if (damager instanceof Player) {
                        Player pd = (Player) damager;
                        Titles.sendTitle(pv, 2, 20 * time, 2, Randomizer.randomTitleOnDeathByPlayer(pd), Randomizer.randomSubTitleOnDeathByPlayer(pd));
                        ActionBar.sendActionBar(pd, Randomizer.randomKillActionBar(pv));
                        try {
                            pd.playSound(pd.getLocation(), Sound.valueOf(random_kill_sound), Config.SOUND_KILL_VOLUME, Config.SOUND_KILL_PITCH);
                        } catch (Exception e) {
                            XDeath.logger(ChatColor.translateAlternateColorCodes('&', Messages.SOUND_ERROR).replace("%sound%", random_kill_sound));
                        }
                        if (!Config.USE_PACKET_EVENT_HANDLER) {
                            EntityDamageByEntityEvent entity_damage = new EntityDamageByEntityEvent(damager, victim, event.getCause(), event.getDamage());
                            Bukkit.getPluginManager().callEvent(entity_damage);
                            pd.incrementStatistic(Statistic.DAMAGE_DEALT, (int) event.getFinalDamage());
                            pd.incrementStatistic(Statistic.PLAYER_KILLS, 1);
                        }
                    }
                    if (damager instanceof Projectile) {
                        Projectile pj = (Projectile) damager;
                        if (pj.getShooter() instanceof Entity) {
                            if (Config.USE_KILL_CAM && !Config.USE_PACKET_EVENT_HANDLER) {
                                pv.setSpectatorTarget((Entity) pj.getShooter());
                            }
                        }
                        if (pj.getShooter() instanceof Player) {
                            Player pd = (Player) pj.getShooter();
                            Titles.sendTitle(pv, 2, 20 * time, 2, Randomizer.randomTitleOnDeathByPlayer(pd), Randomizer.randomSubTitleOnDeathByPlayer(pd));
                            ActionBar.sendActionBar(pd, Randomizer.randomKillActionBar(pv));
                            try {
                                pd.playSound(pd.getLocation(), Sound.valueOf(random_kill_sound), Config.SOUND_KILL_VOLUME, Config.SOUND_KILL_PITCH);
                            } catch (Exception e) {
                                XDeath.logger(ChatColor.translateAlternateColorCodes('&', Messages.SOUND_ERROR).replace("%sound%", random_kill_sound));
                            }
                            if (!Config.USE_PACKET_EVENT_HANDLER) {
                                EntityDamageByEntityEvent entity_damage = new EntityDamageByEntityEvent(pd, victim, event.getCause(), event.getDamage());
                                Bukkit.getPluginManager().callEvent(entity_damage);
                                pd.incrementStatistic(Statistic.DAMAGE_DEALT, (int) event.getFinalDamage());
                                pd.incrementStatistic(Statistic.PLAYER_KILLS, 1);
                            }
                        }
                        pj.remove();
                    }
                }
            }
        }
    }
}
