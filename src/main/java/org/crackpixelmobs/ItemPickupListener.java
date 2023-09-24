package org.crackpixelmobs;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;

import java.util.Collections;
import java.util.List;

public class ItemPickupListener implements Listener {
    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        ItemStack item = event.getItem().getItemStack();
        Material itemType = item.getType();

        if (!LoresGenerator.isCustomItem(itemType)) { // Exclude custom items
            Rarity rarity = LoresGenerator.getRarityForVanillaItem(itemType);
            List<String> itemLore = item.getItemMeta().getLore();

            // Check if the item lore contains custom rarity lore
            boolean hasCustomRarityLore = false;
            if (itemLore != null) {
                for (String lore : itemLore) {
                    if (lore.contains(ChatColor.BOLD.toString())) {
                        hasCustomRarityLore = true;
                        break;
                    }
                }
            }

            if (!hasCustomRarityLore) {
                applyRarityLore(item, rarity);
            }
        }
    }

    private void applyRarityLore(ItemStack item, Rarity rarity) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setLore(Collections.singletonList(rarity.getRarityColor() + "" + ChatColor.BOLD + rarity.getName()));
            item.setItemMeta(meta);
        }
    }
}
