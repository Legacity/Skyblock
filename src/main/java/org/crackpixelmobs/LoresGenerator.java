package org.crackpixelmobs;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LoresGenerator {
    // Set that includes namespaces of custom items
    private static final Set<String> customItemNamespaces = new HashSet<>();
    static {
        customItemNamespaces.add("org.crackpixelmobs");
        customItemNamespaces.add("org.crackpixelmobs.items");
    }

    // Map that associates vanilla items with the default rarity
    private static final Map<Material, Rarity> vanillaItemRarities = new HashMap<>();

    static {
        for (Material material : Material.values()) {
            if (material.isItem() && !isCustomItem(material)) { // Filter out non-item materials and custom-coded items
                vanillaItemRarities.put(material, Rarity.COMMON);
            }
        }
    }

    public static Rarity getRarityForVanillaItem(Material material) {
        return vanillaItemRarities.getOrDefault(material, Rarity.COMMON);
    }

    public static boolean isCustomItem(Material material) {
        String namespace = material.getKey().getNamespace();
        return customItemNamespaces.contains(namespace);
    }
}
