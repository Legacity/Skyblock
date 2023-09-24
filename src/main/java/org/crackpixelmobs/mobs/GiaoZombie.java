package org.crackpixelmobs.mobs;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.plugin.java.JavaPlugin;

public class GiaoZombie implements Listener {
    private final JavaPlugin plugin;

    public GiaoZombie(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void spawnGiaoZombie(Player player) {
        Location spawnLocation = player.getLocation();
        Zombie giaoZombie = (Zombie) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.ZOMBIE);

        // Customize the GiaoZombie's appearance and behavior
        giaoZombie.setCustomName(ChatColor.RED + "[BOSS] iShowMeat");
        giaoZombie.setCustomNameVisible(true);
        giaoZombie.setMaxHealth(200.0);
        giaoZombie.setHealth(200.0);
        giaoZombie.setBaby(false); // Make sure it's not a baby zombie
        giaoZombie.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.0); // Stop movement
        giaoZombie.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(0.0); // Remove attack damage

        // Apply a scaling effect to make the GiaoZombie appear larger
        giaoZombie.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 3, false, false));
    }


    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Zombie) {
            Zombie zombie = (Zombie) event.getEntity();

            if (event.getDamager() instanceof Player) {
                Player player = (Player) event.getDamager();

                // Check if it's the GiaoZombie and the first hit
                if (zombie.getCustomName().equals(ChatColor.RED + "[BOSS] iShowMeat") && zombie.getHealth() == 200.0) {
                    player.sendMessage(ChatColor.RED + "[BOSS] iShowMeat: NOOO MAMA");
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, 1.0f, 1.0f);
                }
                // Check if it's the GiaoZombie and the second hit
                else if (zombie.getCustomName().equals(ChatColor.RED + "[BOSS] iShowMeat") && zombie.getHealth() <= 100.0) {
                    player.sendMessage(ChatColor.RED + "[BOSS] iShowMeat: stop you dumbo");
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, 1.0f, 1.0f);
                }
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getCustomName() != null && event.getEntity().getCustomName().equals(ChatColor.RED + "[BOSS] iShowMeat")) {
            Location loc = event.getEntity().getLocation();

            // Spawn rainbow particles around the player for 10 seconds
            for (Player player : loc.getWorld().getPlayers()) {
                if (player.getLocation().distance(loc) < 20) { // Adjust the radius as needed
                    player.spawnParticle(Particle.REDSTONE, loc, 1000, 1, 1, 1, 1, new Particle.DustOptions(Color.RED, 1));
                }
            }

            // Create a big explosion
            loc.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), 5.0F, true, true);
        }
    }
}
