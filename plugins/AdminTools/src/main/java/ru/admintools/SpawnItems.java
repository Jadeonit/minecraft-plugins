package ru.admintools;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class SpawnItems {

    private final PluginConfig config;

    private final NamespacedKey typeKey;
    private final NamespacedKey countKey;
    private final NamespacedKey targetKey;

    public SpawnItems(AdminToolsPlugin plugin, PluginConfig config) {
        this.config = config;
        this.typeKey = new NamespacedKey(plugin, "item_type");
        this.countKey = new NamespacedKey(plugin, "item_count");
        this.targetKey = new NamespacedKey(plugin, "item_target");
    }

    public ItemStack createItem(EntityType type, int count, UUID targetId, String targetName) {
        ItemStack item = new ItemStack(Material.FISHING_ROD);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return item;
        }

        if (!config.itemName().isBlank()) {
            meta.displayName(Component.text(config.itemName(), NamedTextColor.WHITE)
                    .decoration(TextDecoration.ITALIC, false));
        }

        if (config.itemLore()) {
            List<Component> lore = new ArrayList<>();
            lore.add(Component.text("Тип: ", NamedTextColor.DARK_GRAY)
                    .append(Component.text(type.name().toLowerCase(), NamedTextColor.WHITE))
                    .decoration(TextDecoration.ITALIC, false));
            lore.add(Component.text("Количество: ", NamedTextColor.DARK_GRAY)
                    .append(Component.text(count == PlayerSettings.UNLIMITED ? "безлимит" : String.valueOf(count),
                            NamedTextColor.WHITE))
                    .decoration(TextDecoration.ITALIC, false));
            lore.add(Component.text("Игрок: ", NamedTextColor.DARK_GRAY)
                    .append(Component.text(targetName == null ? "?" : targetName, NamedTextColor.WHITE))
                    .decoration(TextDecoration.ITALIC, false));
            meta.lore(lore);
        }

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(typeKey, PersistentDataType.STRING, type.name());
        pdc.set(countKey, PersistentDataType.INTEGER, count);
        if (targetId != null) {
            pdc.set(targetKey, PersistentDataType.STRING, targetId.toString());
        }

        item.setItemMeta(meta);
        return item;
    }

    public boolean isSpawnItem(ItemStack item) {
        if (item == null || item.getType() != Material.FISHING_ROD) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return false;
        }
        return meta.getPersistentDataContainer().has(typeKey, PersistentDataType.STRING);
    }

    public EntityType readType(ItemStack item) {
        ItemMeta meta = item == null ? null : item.getItemMeta();
        if (meta == null) {
            return null;
        }
        String raw = meta.getPersistentDataContainer().get(typeKey, PersistentDataType.STRING);
        return PluginConfig.parseType(raw);
    }

    public Integer readCount(ItemStack item) {
        ItemMeta meta = item == null ? null : item.getItemMeta();
        if (meta == null) {
            return null;
        }
        return meta.getPersistentDataContainer().get(countKey, PersistentDataType.INTEGER);
    }

    public UUID readTarget(ItemStack item) {
        ItemMeta meta = item == null ? null : item.getItemMeta();
        if (meta == null) {
            return null;
        }
        String raw = meta.getPersistentDataContainer().get(targetKey, PersistentDataType.STRING);
        if (raw == null) {
            return null;
        }
        try {
            return UUID.fromString(raw);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
