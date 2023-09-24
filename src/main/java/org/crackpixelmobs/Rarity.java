package org.crackpixelmobs;

import org.bukkit.ChatColor;

public enum Rarity {
    COMMON(ChatColor.BOLD + "COMMON", ChatColor.WHITE),
    UNCOMMON(ChatColor.BOLD + "UNCOMMON", ChatColor.GREEN),
    RARE(ChatColor.BOLD + "RARE", ChatColor.BLUE),
    EPIC(ChatColor.BOLD + "EPIC", ChatColor.DARK_PURPLE),
    MYTHICAL(ChatColor.BOLD + "MYTHICAL", ChatColor.LIGHT_PURPLE),
    LEGENDARY(ChatColor.BOLD + "LEGENDARY", ChatColor.GOLD);


    private final String name;
    private final ChatColor color;

    Rarity(String name, ChatColor color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public ChatColor getRarityColor() {
        return color;
    }

    public String getLore() {
        return color + "" + ChatColor.BOLD + name + "\n";
    }
}
