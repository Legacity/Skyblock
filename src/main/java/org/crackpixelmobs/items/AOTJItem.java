package org.crackpixelmobs.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.crackpixelmobs.Rarity;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class AOTJItem implements Listener {

    private final JavaPlugin plugin;

    public AOTJItem(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public ItemStack createAspectOfTheJerry() {
        ItemStack aotjSword = new ItemStack(Material.WOODEN_SWORD);
        ItemMeta aotjMeta = aotjSword.getItemMeta();
        aotjMeta.setDisplayName(ChatColor.WHITE + "Aspect of The Jerry");

        // Lore for the item
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Damage: " + ChatColor.RED + "+1");
        lore.add("");
        lore.add(ChatColor.GOLD + "Ability: Parley" + ChatColor.YELLOW + "" + ChatColor.BOLD + " RIGHT CLICK");
        lore.add(ChatColor.GRAY + "Channel your Inner Jerry");
        lore.add("");
        lore.add(ChatColor.DARK_GRAY + "Cooldown " + ChatColor.GREEN + "5s");
        lore.add(ChatColor.DARK_GRAY + "This item can be reforged");
        lore.add("");
        // Adding the rarity to the lore
        Rarity rarity = Rarity.COMMON;
        lore.add(rarity.getRarityColor() + "" + ChatColor.BOLD + rarity.getName());

        aotjMeta.setLore(lore);
        aotjSword.setItemMeta(aotjMeta);
        return aotjSword;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack heldItem = player.getInventory().getItemInMainHand();

        if (heldItem != null && heldItem.isSimilar(createAspectOfTheJerry())) {
            if (event.getAction().toString().contains("RIGHT")) {
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_CELEBRATE, 1.0f, 1.0f);
                player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation().add(0, 1, 0), 50);
            }
        }
    }
}
