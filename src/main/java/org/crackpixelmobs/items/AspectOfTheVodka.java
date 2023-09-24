package org.crackpixelmobs.items;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;
import org.crackpixelmobs.Rarity;

import java.util.ArrayList;
import java.util.List;

public class AspectOfTheVodka implements Listener {
    private final JavaPlugin plugin;

    public AspectOfTheVodka(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public ItemStack createAspectOfTheVodka() {
        ItemStack aspectOfTheVodka = new ItemStack(Material.IRON_HORSE_ARMOR);
        ItemMeta itemMeta = aspectOfTheVodka.getItemMeta();
        itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Aspect of the Vodka");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Damage: " + ChatColor.RED + "+270");
        lore.add(ChatColor.GRAY + "Strength: " + ChatColor.RED + "+130");
        lore.add(" ");
        lore.add(ChatColor.GOLD + "BANG BANG BASH!:" + ChatColor.YELLOW + ChatColor.BOLD + " VODKA RUN!");
        lore.add(ChatColor.GRAY + "Throws cocaine and if it touches the ground");
        lore.add(ChatColor.GRAY + "it would automatically kill you hehe");
        lore.add(" ");
        Rarity rarity = Rarity.MYTHICAL;
        lore.add(rarity.getRarityColor() + "" + ChatColor.BOLD + rarity.getName());
        itemMeta.setLore(lore);
        aspectOfTheVodka.setItemMeta(itemMeta);
        return aspectOfTheVodka;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item != null && item.isSimilar(createAspectOfTheVodka())) {
            Vector direction = player.getLocation().getDirection().normalize();

            ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
            armorStand.setVisible(false);
            armorStand.setGravity(true);
            armorStand.setCustomName(ChatColor.RED + "Cocaine");
            armorStand.setCustomNameVisible(true);

            ItemStack helmet = new ItemStack(Material.STONE);
            armorStand.setHelmet(helmet);

            EulerAngle headPose = new EulerAngle(-Math.PI / 4, 0, 0);
            armorStand.setHeadPose(headPose);

            Vector velocity = direction.multiply(1.5);

            new BukkitRunnable() {
                int ticks = 0;
                Location startLocation = armorStand.getLocation();

                @Override
                public void run() {
                    if (ticks >= 160) { // Adjusted to explode after traveling 8 blocks (160 ticks)
                        explodeArmorStand(armorStand);
                        this.cancel();
                        return;
                    }

                    Location armorStandLocation = armorStand.getLocation();
                    double distance = armorStandLocation.distance(startLocation);

                    if (distance >= 8.0) { // Changed the distance condition to 8 blocks
                        explodeArmorStand(armorStand);
                        playJoyfulSound(armorStandLocation);
                        this.cancel();
                        return;
                    }

                    armorStand.setVelocity(velocity);

                    for (Entity entity : armorStand.getNearbyEntities(2.0, 2.0, 2.0)) {
                        if (entity instanceof LivingEntity && !(entity instanceof ArmorStand) && !(entity instanceof Player)) {
                            explodeArmorStand(armorStand);
                            playJoyfulSound(armorStandLocation);
                            this.cancel();
                            return;
                        }
                    }

                    ticks++;
                }
            }.runTaskTimer(plugin, 0L, 1L);
        }
    }

    private void explodeArmorStand(ArmorStand armorStand) {
        armorStand.getWorld().createExplosion(armorStand.getLocation(), 5.0f, false, false);
        armorStand.remove();
    }

    private void playJoyfulSound(Location location) {
        location.getWorld().playSound(location, Sound.ENTITY_PLAYER_LEVELUP, 10.0f, 1.0f);
    }
}
