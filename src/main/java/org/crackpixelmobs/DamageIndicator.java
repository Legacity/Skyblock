package org.crackpixelmobs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DamageIndicator implements Listener {

    private final JavaPlugin plugin;

    public DamageIndicator(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity victim = event.getEntity();

        if (damager instanceof Player && (victim instanceof Player || victim.getType().isAlive())) {
            Player playerDamager = (Player) damager;

            double damage = event.getFinalDamage();
            boolean isCrit = isCritical();

            String damageString = ChatColor.GRAY + formatDamage(damage);
            if (isCrit) {
                damageString = ChatColor.MAGIC + formatDamage(damage + 4);
            }

            createDamageIndicator(victim.getLocation().add(0, 1, 0), damageString);
        }
    }

    private boolean isCritical() {
        return Math.random() < 0.5;
    }

    private String formatDamage(double damage) {
        int roundedDamage = (int) Math.round(damage);
        return String.valueOf(roundedDamage);
    }

    private void createDamageIndicator(Location location, String damageString) {
        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        armorStand.setInvisible(true);
        armorStand.setSmall(true);
        armorStand.setCustomName(damageString);
        armorStand.setCustomNameVisible(true);

        Bukkit.getScheduler().runTaskLater(plugin, armorStand::remove, 23);
    }
}
