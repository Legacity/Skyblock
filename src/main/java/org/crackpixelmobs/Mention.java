package org.crackpixelmobs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Mention implements Listener {

    private final JavaPlugin plugin;

    public Mention(JavaPlugin plugin) {
        this.plugin = plugin;
        // Register this class as an event listener in the plugin
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        String[] words = message.split(" ");

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (word.startsWith("@")) {
                // Remove the "@" symbol
                String playerName = word.substring(1);

                // Check if the mentioned player is online
                if (Bukkit.getPlayerExact(playerName) != null) {
                    // Colorize and underline the mentioned player's name
                    String formattedName = ChatColor.YELLOW + "" + ChatColor.UNDERLINE + playerName + ChatColor.RESET;
                    words[i] = formattedName;
                }
            }
        }

        // Reconstruct the message with formatted mentions
        StringBuilder formattedMessage = new StringBuilder();
        for (String word : words) {
            formattedMessage.append(word).append(" ");
        }

        event.setMessage(formattedMessage.toString().trim());
    }
}
