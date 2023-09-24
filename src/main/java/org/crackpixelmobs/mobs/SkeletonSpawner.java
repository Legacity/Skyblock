package org.crackpixelmobs.mobs;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SkeletonSpawner implements Listener {

    private final JavaPlugin plugin;

    public SkeletonSpawner(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void spawnSkeleton(Player player) {
        Location spawnLocation = player.getLocation();
        Skeleton skeleton = (Skeleton) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.SKELETON);

        updateSkeletonCustomName(skeleton);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Skeleton) {
            Skeleton skeleton = (Skeleton) event.getEntity();
            updateSkeletonCustomName(skeleton);
        }
    }

    private void updateSkeletonCustomName(Skeleton skeleton) {
        double totalHealth = 20.0; // Maximum health
        double remainingHealth = skeleton.getHealth();

        int totalHealthInt = (int) totalHealth;
        int remainingHealthInt = (int) remainingHealth;

        // Set custom attributes and display name for the skeleton
        skeleton.setCustomName(ChatColor.GRAY + "[5]" + ChatColor.RED + " Skeleton " + ChatColor.GREEN + totalHealthInt + "/" + remainingHealthInt);
        skeleton.setCustomNameVisible(true);
    }
}
