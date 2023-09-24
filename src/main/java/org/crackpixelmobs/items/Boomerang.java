package org.crackpixelmobs.items;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.crackpixelmobs.Rarity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Boomerang implements Listener {

    private final JavaPlugin plugin;
    private final HashMap<UUID, ArmorStand> boomerangs = new HashMap<>();

    public Boomerang(JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public ItemStack createBoomerang() {
        ItemStack boomerang = new ItemStack(Material.BONE);
        ItemMeta meta = boomerang.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Bonemerang");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Damage: " + ChatColor.RED + "+270");
        lore.add(ChatColor.GRAY + "Strength: " + ChatColor.RED + "+130");
        lore.add(" ");
        lore.add(ChatColor.GOLD + "Ability Swing:" + ChatColor.YELLOW + ChatColor.BOLD + " RIGHT CLICK");
        lore.add(ChatColor.GRAY + "Throw a bone a short distance.");
        lore.add(ChatColor.GRAY + "dealing the damage an arrow");
        lore.add(ChatColor.GRAY + "would");
        Rarity rarity = Rarity.LEGENDARY;
        lore.add(rarity.getRarityColor() + "" + ChatColor.BOLD + rarity.getName());

        // Add any other attributes, lore, and metadata needed for your server
        meta.setLore(lore);
        boomerang.setItemMeta(meta);
        return boomerang;
    }

    @EventHandler
    public void onPlayerRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (event.getAction() == Action.RIGHT_CLICK_AIR && item != null && item.isSimilar(createBoomerang())) {
            spawnBoomerang(player);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (event.getEntityType() == EntityType.ARMOR_STAND && event.getEntity().getCustomName() != null &&
                event.getEntity().getCustomName().equals(ChatColor.GOLD + "Boomerang")) {
            ArmorStand armorStand = (ArmorStand) event.getEntity();
            boomerangs.put(armorStand.getUniqueId(), armorStand);
            moveBoomerang(armorStand, null);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof ArmorStand) {
            ArmorStand armorStand = (ArmorStand) event.getDamager();
            if (boomerangs.containsKey(armorStand.getUniqueId())) {
                if (event.getEntity() instanceof Player) {
                    Player player = (Player) event.getEntity();
                    returnBoomerang(armorStand, player);
                }
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            ItemStack itemInHand = player.getInventory().getItemInMainHand();
            if (itemInHand != null && itemInHand.isSimilar(createBoomerang())) {
                itemInHand.setAmount(itemInHand.getAmount() - 1);
            }
        }
    }

    private void spawnBoomerang(Player player) {
        Location location = player.getEyeLocation();
        ArmorStand armorStand = location.getWorld().spawn(location, ArmorStand.class);
        armorStand.setVisible(false);
        armorStand.setGravity(false);
        armorStand.setCustomName(ChatColor.GOLD + "Boomerang");
        armorStand.setCustomNameVisible(true);

        moveBoomerang(armorStand, player);
    }

    private void moveBoomerang(ArmorStand armorStand, Player player) {
        new BukkitRunnable() {
            double t = 0;
            Location startLocation = armorStand.getLocation().clone();
            boolean hitEntity = false;

            @Override
            public void run() {
                if (t >= 360) {
                    t = 0;
                }

                double x = Math.sin(Math.toRadians(t)) * 2;
                double z = Math.cos(Math.toRadians(t)) * 2;

                Location newLocation = startLocation.clone().add(x, 0, z);
                armorStand.teleport(newLocation);

                t += 10;

                if (!armorStand.isValid()) {
                    cancel();
                }

                // Check for collisions with entities
                for (Entity entity : armorStand.getNearbyEntities(0.5, 0.5, 0.5)) {
                    if (entity instanceof LivingEntity && entity != player) {
                        hitEntity = true;
                        ((LivingEntity) entity).damage(5.0);
                    }
                }

                // Check if it hit an entity or traveled a certain distance
                if (hitEntity || armorStand.getLocation().distance(startLocation) >= 20.0) {
                    returnBoomerang(armorStand, player);
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }

    private void returnBoomerang(ArmorStand armorStand, Player player) {
        armorStand.remove();
        player.getInventory().addItem(createBoomerang());
        boomerangs.remove(armorStand.getUniqueId());
    }
}
