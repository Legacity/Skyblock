//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.crackpixelmobs;

import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.crackpixelmobs.Stats.Attribute;
import org.crackpixelmobs.items.AOTV;
import org.crackpixelmobs.items.AspectOfTheVodka;
import org.crackpixelmobs.items.Boomerang;
import org.crackpixelmobs.items.Hyperion;
import org.crackpixelmobs.items.Terminator;
import org.crackpixelmobs.mobs.GiaoZombie;
import org.crackpixelmobs.mobs.SkeletonSpawner;
import org.crackpixelmobs.mobs.ZealotSpawner;
import org.crackpixelmobs.mobs.ZombieSpawner;

public class CommandCenter extends JavaPlugin implements CommandExecutor, Listener {
    private ZombieSpawner zombieSpawner;
    private ZealotSpawner zealotSpawner;
    private Stats playerStats;
    private ItemsGui itemsGui;
    private Boomerang boomerang;
    private CustomScoreboard customScoreboard;
    private SkeletonSpawner skeletonSpawner;
    private DamageIndicator damageIndicator;
    private GiaoZombie giaZombieSpawner;
    private Mention mention;

    public CommandCenter() {
    }

    public void onEnable() {
        this.playerStats = new Stats();
        this.boomerang = new Boomerang(this);
        this.customScoreboard = new CustomScoreboard(this);
        this.damageIndicator = new DamageIndicator(this);
        this.giaZombieSpawner = new GiaoZombie(this);
        mention = new Mention(this);
        this.getServer().getPluginManager().registerEvents(this.giaZombieSpawner, this);
        Hyperion hyperion = new Hyperion(this);
        Terminator terminatorInstance = new Terminator(this);
        Boomerang boomerangInstance = new Boomerang(this);
        AspectOfTheVodka aspectOfTheVodka = new AspectOfTheVodka(this);
        AOTV aotv = new AOTV(this);
        this.itemsGui = new ItemsGui(this, this.playerStats, aotv, hyperion, terminatorInstance, boomerangInstance, aspectOfTheVodka);
        this.zombieSpawner = new ZombieSpawner(this);
        this.getServer().getPluginManager().registerEvents(this.zombieSpawner, this);
        this.zealotSpawner = new ZealotSpawner(this);
        this.getServer().getPluginManager().registerEvents(this.zealotSpawner, this);

        this.getServer().getPluginManager().registerEvents(new ItemPickupListener(), this);
        this.skeletonSpawner = new SkeletonSpawner(this);
        this.getServer().getPluginManager().registerEvents(this.skeletonSpawner, this);
        this.getCommand("cpm").setExecutor(this);
        this.getCommand("cpa").setExecutor(this);
        this.getCommand("itemsgui").setExecutor(this);
        this.getCommand("slayers").setExecutor(this);
        this.getCommand("worldtest").setExecutor(this);
        this.getCommand("statstest").setExecutor(this);
        Iterator var6 = Bukkit.getWorlds().iterator();

        while(var6.hasNext()) {
            World world = (World)var6.next();
            world.setGameRule(GameRule.FALL_DAMAGE, false);
        }

        this.getLogger().info("░█▀▀░█▀█░█▄█░█▀▀░█▀█░▀█▀░█░█░█▀▀░█░░░░░█▀▀░█▀█░█▀▄░█▀");
        this.getLogger().info("░█░█░█▀█░█░█░█▀▀░█▀▀░░█░░▄▀▄░█▀▀░█░░░░░█░░░█░█░█▀▄░█▀");
        this.getLogger().info("░▀▀▀░▀░▀░▀░▀░▀▀▀░▀░░░▀▀▀░▀░▀░▀▀▀░▀▀▀░░░▀▀▀░▀▀▀░▀░▀░▀▀");
    }

    public void onDisable() {
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("cpm")) {
            Player player;
            if (command.getName().equalsIgnoreCase("cpa") && args.length == 2 && sender instanceof Player) {
                player = (Player)sender;
                String attributeName = args[0];

                double attributeValue;
                try {
                    attributeValue = Double.parseDouble(args[1]);
                } catch (NumberFormatException var10) {
                    sender.sendMessage("Invalid attribute value.");
                    return true;
                }

                Stats.Attribute attribute = Attribute.valueOf(attributeName.toUpperCase());
                this.playerStats.setAttribute(player, attribute, attributeValue);
                sender.sendMessage("Attribute " + attributeName + " set to " + attributeValue);
                return true;
            } else if (command.getName().equalsIgnoreCase("itemsgui")) {
                if (sender instanceof Player) {
                    this.itemsGui.openItemsGui((Player)sender);
                }

                return true;
            } else if (command.getName().equalsIgnoreCase("statstest") && sender instanceof Player) {
                player = (Player)sender;
                player.sendMessage("RAW STAT OUTPUT");
                player.sendMessage(ChatColor.RED + "Health: " + this.playerStats.getAttributeValue(Attribute.HEALTH));
                return true;
            } else if (command.getName().equalsIgnoreCase("worldtest") && sender instanceof Player) {
                WorldCreator wc = new WorldCreator("neu");
                wc.environment(Environment.NORMAL);
                wc.type(WorldType.NORMAL);
                World w = Bukkit.createWorld(wc);
                sender.sendMessage("World 'neu' created successfully.");
                return true;
            } else {
                return false;
            }
        } else {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("zombie") && sender instanceof Player) {
                    this.zombieSpawner.spawnZombie((Player)sender);
                } else if (args[0].equalsIgnoreCase("skeleton") && sender instanceof Player) {
                    this.skeletonSpawner.spawnSkeleton((Player)sender);
                } else if (args[0].equalsIgnoreCase("special_zealot") && sender instanceof Player) {
                    this.zealotSpawner.spawnSpecialZealot((Player)sender);
                } else if (args[0].equalsIgnoreCase("giao") && sender instanceof Player) {
                    this.giaZombieSpawner.spawnGiaoZombie((Player)sender);
                } else {
                    sender.sendMessage("Usage: /cpm <mob name>");
                }
            } else {
                sender.sendMessage("Usage: /cpm <mob name>");
            }

            return true;
        }
    }

    private void addSkyblockMenuNetherStar(Player player) {
        ItemStack netherStar = new ItemStack(Material.NETHER_STAR);
        ItemMeta itemMeta = netherStar.getItemMeta();
        itemMeta.setDisplayName(ChatColor.YELLOW + "Skyblock Menu");
        netherStar.setItemMeta(itemMeta);
        player.getInventory().setItem(8, netherStar);
        player.updateInventory();
    }

    private void removeSkyblockMenuNetherStar(Player player) {
        player.getInventory().setItem(8, (ItemStack)null);
        player.updateInventory();
    }
}
