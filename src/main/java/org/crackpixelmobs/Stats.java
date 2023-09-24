package org.crackpixelmobs;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stats {

    private final Map<Attribute, Double> attributes = new HashMap<>();

    public enum Attribute {
        HEALTH("❤ Health"),
        DEFENSE("❈ Defense"),
        STRENGTH("❁ Strength"),
        SPEED("✦ Speed"),
        CRIT_CHANCE("☣ Crit Chance"),
        CRIT_DAMAGE("☠ Crit Damage"),
        INTELLIGENCE("✎ Intelligence");

        private final String symbol;

        Attribute(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }
    }

    public void addAttributeToArmorPiece(ItemStack armorPiece, Attribute attribute, int value) {
        ItemMeta armorMeta = armorPiece.getItemMeta();
        List<String> lore = armorMeta.getLore();

        if (lore == null) {
            lore = new ArrayList<>();
        }

        lore.add(ChatColor.GRAY + attribute.getSymbol() + ChatColor.GREEN + " +" + value);
        armorMeta.setLore(lore);
        armorPiece.setItemMeta(armorMeta);
    }

    public void setAttribute(Player player, Attribute attribute, double value) {
        attributes.put(attribute, value);
        // Implement your logic to apply the attribute to the player
    }

    public double getAttributeValue(Attribute attribute) {
        return attributes.getOrDefault(attribute, 0.0);
    }

    public double calculatePlayerDamage(Player player, double baseDamage) {
        double strength = getAttributeValue(Attribute.STRENGTH);
        double critDamage = getAttributeValue(Attribute.CRIT_DAMAGE);

        // Implement your logic to calculate damage based on strength, crit damage, and other factors
        // This is a simplified example, adjust it according to your game mechanics
        double calculatedDamage = baseDamage + (strength * 0.5) + (critDamage * 0.8);

        return calculatedDamage;
    }
}

