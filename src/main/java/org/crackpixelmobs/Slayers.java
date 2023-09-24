package org.crackpixelmobs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Slayers {

    public void openSlayersMenu(Player player) {
        // Create a 3x9 inventory for the menu
        Inventory menu = Bukkit.createInventory(player, 27, ChatColor.BOLD + "Slayers Menu");

        // Create item stacks for the menu
        ItemStack blackGlassPane = createItem(Material.BLACK_STAINED_GLASS_PANE, " ");
        ItemStack zombieFlesh = createItem(Material.ROTTEN_FLESH, ChatColor.GREEN + "Revenant Horror");
        ItemStack bedrock = createItem(Material.BEDROCK, " ");

        // Set the items in the menu
        for (int i = 0; i < menu.getSize(); i++) {
            if (isBorderSlot(i) || i == 3) {
                menu.setItem(i, blackGlassPane);
            } else if (i == 4) {
                // Set the second slot with a custom-named item
                // For example, you can use a custom ItemStack from your ItemsGui class
                ItemStack customItem = zombieFlesh; // Replace this with your actual custom item
                menu.setItem(i, customItem);
            } else if (isBorderSlot(i) || i >= 6 && i <= 8) {
                menu.setItem(i, bedrock);
            }
            // Other cases for slots 5 and custom items
        }

        player.openInventory(menu); // Open the menu for the player
    }

    private boolean isBorderSlot(int slot) {
        int row = slot / 9;
        int col = slot % 9;
        return row == 0 || row == 2 || col == 0 || col == 8;
    }

    private ItemStack createItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }
}
