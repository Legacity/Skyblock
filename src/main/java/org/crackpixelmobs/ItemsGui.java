package org.crackpixelmobs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.crackpixelmobs.items.AOTE;
import org.crackpixelmobs.items.AOTJItem;
import org.crackpixelmobs.items.AOTV;
import org.crackpixelmobs.items.AspectOfTheVodka;
import org.crackpixelmobs.items.Boomerang;
import org.crackpixelmobs.items.Hyperion;
import org.crackpixelmobs.items.Terminator;

public class ItemsGui implements Listener {
    private final JavaPlugin plugin;
    private final AOTJItem aotjItem;
    private final Stats stats;
    private final AOTV aotv;
    private final Hyperion hyperion;
    private final Terminator terminator;
    private final AspectOfTheVodka aspectOfTheVodka;
    private final Boomerang boomerang;

    public ItemsGui(JavaPlugin plugin, Stats stats, AOTV aotv, Hyperion hyperion, Terminator terminator, Boomerang boomerang, AspectOfTheVodka aspectOfTheVodka) {
        this.plugin = plugin;
        this.aotjItem = new AOTJItem(plugin);
        this.stats = stats;
        this.aotv = aotv;
        this.hyperion = hyperion;
        this.terminator = terminator;
        this.boomerang = boomerang;
        this.aspectOfTheVodka = aspectOfTheVodka;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void openItemsGui(Player player) {
        Inventory gui = Bukkit.createInventory((InventoryHolder)null, 18, ChatColor.BOLD + "Crackpixel Items");

        ItemStack aotjSword = this.aotjItem.createAspectOfTheJerry();
        gui.addItem(new ItemStack[]{aotjSword});

        AOTE aoteItem = new AOTE(this.plugin, this.stats);
        ItemStack aoteSword = aoteItem.createAspectOfTheEnd();
        gui.addItem(new ItemStack[]{aoteSword});

        Hyperion var10000 = this.hyperion;
        ItemStack hyperionSword = Hyperion.createHyperion();
        gui.addItem(new ItemStack[]{hyperionSword});

        ItemStack aspectOfTheVoid = this.aotv.createAspectOfTheVoid();
        gui.addItem(new ItemStack[]{aspectOfTheVoid});

        ItemStack terminatorBow = this.terminator.createTerminatorBow();
        gui.addItem(new ItemStack[]{terminatorBow});

        ItemStack boomerangItem = this.boomerang.createBoomerang();
        gui.addItem(new ItemStack[]{boomerangItem});

        ItemStack vodkaItem = this.aspectOfTheVodka.createAspectOfTheVodka();
        gui.addItem(new ItemStack[]{vodkaItem});

        player.openInventory(gui);
    }

    public void someMethodInItemsGui() {
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player)event.getWhoClicked();
            Inventory clickedInventory = event.getClickedInventory();
            ItemStack clickedItem = event.getCurrentItem();

            if (clickedInventory != null && clickedItem != null) {
                if (clickedInventory.getType() == InventoryType.CHEST) {
                    String inventoryTitle = event.getView().getTitle();
                    if (inventoryTitle.equals(ChatColor.BOLD + "Crackpixel Items")) {
                        event.setCancelled(true);
                        this.giveAbilityToPlayer(player, clickedItem);
                        player.getInventory().addItem(new ItemStack[]{clickedItem});
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getInventory().getType() == InventoryType.CHEST) {
            String inventoryTitle = event.getView().getTitle();
            if (inventoryTitle.equals(ChatColor.BOLD + "Crackpixel Items")) {
                event.setCancelled(true);
            }
        }
    }

    private void giveAbilityToPlayer(Player player, ItemStack item) {
        // Implement your ability logic here
    }
}
