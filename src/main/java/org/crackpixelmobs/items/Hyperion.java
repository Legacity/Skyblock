package org.crackpixelmobs.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.crackpixelmobs.Rarity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Hyperion implements Listener {

    private final JavaPlugin plugin;

    public Hyperion(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public static ItemStack createHyperion() {
        ItemStack hyperionSword = new ItemStack(Material.IRON_SWORD);
        ItemMeta hyperionMeta = hyperionSword.getItemMeta();
        hyperionMeta.setDisplayName(ChatColor.GOLD + "Hyperion");

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Damage:" + ChatColor.RED + " +360"); // Update base damage
        lore.add(ChatColor.GRAY + "Strength:" + ChatColor.RED + " +150");
        lore.add(ChatColor.GRAY + "Intelligence: " + ChatColor.GREEN + "+350");
        lore.add(ChatColor.GRAY + "Ferocity: " + ChatColor.GREEN + "+30");
        lore.add("");
        lore.add(ChatColor.GRAY + "Deals +" + ChatColor.GREEN + "50%" + ChatColor.GRAY + " damage to");
        lore.add(ChatColor.GRAY + "Withers. Grant" + ChatColor.RED + " +1 Damage");
        lore.add(ChatColor.GRAY + "+2" + ChatColor.AQUA + " Intelligence per Catacombs level");
        lore.add("");
        lore.add(ChatColor.GRAY + "Your Catacombs level:" + ChatColor.RED + " Not Implemented");
        lore.add(ChatColor.DARK_GRAY + "Cooldown " + ChatColor.GREEN + "10s");
        lore.add("");
        lore.add(ChatColor.DARK_GRAY + "This item can be reforged");
        Rarity rarity = Rarity.LEGENDARY;
        lore.add(rarity.getRarityColor() + "" +
                "" + ChatColor.BOLD + "" + rarity.getName());

        hyperionMeta.setLore(lore);
        hyperionSword.setItemMeta(hyperionMeta);
        return hyperionSword;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        if (heldItem != null && heldItem.isSimilar(createHyperion()) &&
                event.getAction().toString().contains("RIGHT")) {

            int teleportDistance = 5; // Adjust the teleport distance as needed
            double baseDamage = 200489; // Adjust the base damage as needed

            // Calculate the teleport destination
            player.getWorld().createExplosion(player.getLocation(), 0.0F, false, false); // Create an explosion to find safe teleport location
            player.teleport(player.getLocation().add(player.getLocation().getDirection().normalize().multiply(teleportDistance)));

            // Play explosion effect and sound
            player.getWorld().createExplosion(player.getLocation(), 4.0F, false, false); // No fire, no block damage
            player.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, player.getLocation(), 50);
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.0F, 1.0F);

            // Deal damage to mobs hit by the explosion
            List<Entity> nearbyEntities = player.getNearbyEntities(5, 5, 5);
            Random random = new Random();
            for (Entity entity : nearbyEntities) {
                if (entity instanceof Zombie) {
                    double damageMultiplier = random.nextDouble() * (2.0 - 1.0) + 1.0;
                    int damage = (int) (baseDamage * damageMultiplier);
                    ((Zombie) entity).damage(damage);
                    player.sendMessage(ChatColor.GRAY + "You dealt " + ChatColor.RED + damage + ChatColor.GRAY + " damage");
                }
            }
        }
    }
}
