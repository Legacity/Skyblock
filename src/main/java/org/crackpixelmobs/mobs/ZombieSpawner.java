package org.crackpixelmobs.mobs;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ZombieSpawner implements Listener {

    private final JavaPlugin plugin;

    public ZombieSpawner(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void spawnZombie(Player player) {
        Location spawnLocation = player.getLocation();
        Zombie zombie = (Zombie) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.ZOMBIE);

        updateZombieCustomName(zombie);

    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Zombie) {
            Zombie zombie = (Zombie) event.getEntity();
            updateZombieCustomName(zombie);
        }
    }

    private void updateZombieCustomName(Zombie zombie) {
        double totalHealth = 20.0; // Maximum health
        double remainingHealth = zombie.getHealth();

        int totalHealthInt = (int) totalHealth;
        int remainingHealthInt = (int) remainingHealth;

        // Set custom attributes and display name for the zombie
        zombie.setCustomName(ChatColor.GRAY + "[5]" + ChatColor.RED + " Zombie " + ChatColor.GREEN + totalHealthInt + "/" + remainingHealthInt);
        zombie.setCustomNameVisible(true);
    }
}
