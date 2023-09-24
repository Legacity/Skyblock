package org.crackpixelmobs.mobs;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.crackpixelmobs.Rarity;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;

public class ZealotSpawner implements Listener {

    private final JavaPlugin plugin;

    public ZealotSpawner(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void spawnSpecialZealot(Player player) {
        Location spawnLocation = player.getLocation();
        Enderman specialZealot = (Enderman) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.ENDERMAN);

        // Set custom metadata tag to identify the special zealot
        specialZealot.setMetadata("SpecialZealot", new FixedMetadataValue(plugin, true));

        // Set custom attributes and display name for the special zealot
        specialZealot.setCustomName(ChatColor.RED + "Special Zealot");
        specialZealot.setCustomNameVisible(true);

        // You can further customize the special zealot's attributes, behavior, etc.
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Enderman) {
            Enderman zealot = (Enderman) event.getEntity();

            if (zealot.hasMetadata("SpecialZealot")) {
                updateZealotCustomName(zealot);
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof Enderman) {
            Enderman zealot = (Enderman) event.getEntity();
            Player killer = zealot.getKiller();
            if (killer != null) {
                ItemStack summoningEye = createSummoningEye();
                killer.getInventory().addItem(summoningEye);
                killer.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "RARE DROP!" + ChatColor.DARK_PURPLE + " Summoning Eye!");
            }
        }
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage(ChatColor.RED + "SPECIAL ZEALOT");
    }

    private void updateZealotCustomName(Enderman zealot) {
        double totalHealth = 12000.0; // Maximum health
        double remainingHealth = zealot.getHealth();

        int totalHealthInt = (int) totalHealth;
        int remainingHealthInt = (int) remainingHealth;

        // Set custom attributes and display name for the zealot
        zealot.setCustomName(ChatColor.GRAY + "[55] " + ChatColor.RED + "Special Zealot " + ChatColor.GREEN + remainingHealthInt + " / " + totalHealthInt);
        zealot.setCustomNameVisible(true);
    }

    private ItemStack createSummoningEye() {
        ItemStack summoningEye = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) summoningEye.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_PURPLE+"Summoning Eye");
        Rarity rarity = Rarity.EPIC;
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(rarity.getRarityColor() + "" + ChatColor.BOLD + rarity.getName()); // Rarity name in bold
        lore.add("");
        meta.setLore(lore);


        meta.setOwningPlayer(plugin.getServer().getOfflinePlayer("bronnon"));

        summoningEye.setItemMeta(meta);
        return summoningEye;
    }
}
