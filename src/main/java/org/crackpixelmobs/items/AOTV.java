package org.crackpixelmobs.items;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.plugin.java.*;
import org.bukkit.util.Vector;
import org.crackpixelmobs.Rarity;

import java.util.ArrayList;
import java.util.List;

public class AOTV implements Listener {

    private final JavaPlugin plugin;

    public AOTV(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public ItemStack createAspectOfTheVoid() {
        ItemStack aspectOfTheVoid = new ItemStack(Material.DIAMOND_SHOVEL);
        ItemMeta itemMeta = aspectOfTheVoid.getItemMeta();
        itemMeta.setDisplayName(ChatColor.DARK_PURPLE + "Aspect of The Void");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Damage: " + ChatColor.RED + "+100");
        lore.add(ChatColor.GRAY + "Strength: " + ChatColor.RED + "+100");
        lore.add(" ");
        lore.add(ChatColor.GOLD + "Ability: Instant Transmission" + ChatColor.YELLOW + ChatColor.BOLD + " RIGHT CLICK");
        lore.add(ChatColor.GRAY + "Teleport " + ChatColor.GREEN + "8 blocks " + ChatColor.GRAY + "ahead of you and gain "
                + ChatColor.GREEN + "+50" + ChatColor.WHITE + "⭐ Speed for");
        lore.add(ChatColor.GREEN + "3 seconds");
        lore.add("");
        lore.add(ChatColor.DARK_GRAY + "This item can be reforged");
        lore.add("");
        Rarity rarity = Rarity.EPIC;
        lore.add(rarity.getRarityColor() + "" + ChatColor.BOLD + "" + rarity.getName());

        itemMeta.setLore(lore); // Set the lore here
        aspectOfTheVoid.setItemMeta(itemMeta);
        return aspectOfTheVoid;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // Check for right-click actions
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (item != null && item.isSimilar(createAspectOfTheVoid())) {
                Location playerLocation = player.getLocation();
                Vector playerDirection = playerLocation.getDirection().normalize();
                Location teleportLocation = playerLocation.add(playerDirection.multiply(8)); // Adjust the teleport distance


                player.teleport(teleportLocation);
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);

                // Prevent the default right-click action (e.g., placing blocks)
                event.setCancelled(true);
            }
        }
    }
}
