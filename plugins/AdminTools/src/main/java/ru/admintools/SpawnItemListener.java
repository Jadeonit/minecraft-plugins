package ru.admintools;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public final class SpawnItemListener implements Listener {

    private final SpawnItems items;
    private final SpawnService service;
    private final SettingsManager settings;

    public SpawnItemListener(SpawnItems items, SpawnService service, SettingsManager settings) {
        this.items = items;
        this.service = service;
        this.settings = settings;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        ItemStack item = event.getItem();
        if (!items.isSpawnItem(item)) {
            return;
        }

        EquipmentSlot hand = event.getHand();
        if (hand != null) {
            Player player = event.getPlayer();
            ItemStack handItem = hand == EquipmentSlot.OFF_HAND
                    ? player.getInventory().getItemInOffHand()
                    : player.getInventory().getItemInMainHand();
            if (!items.isSpawnItem(handItem)) {
                return;
            }
            item = handItem;
        }

        event.setCancelled(true);

        Player player = event.getPlayer();
        PlayerSettings current = settings.get(player);

        EntityType type = items.readType(item);
        Integer count = items.readCount(item);
        UUID targetId = items.readTarget(item);

        if (type == null) {
            Msg.error(player, "Недоступно.");
            return;
        }
        if (count == null) {
            count = current.getCount();
        }
        if (targetId == null) {
            targetId = current.getTarget();
        }

        Player target = targetId == null ? null : Bukkit.getPlayer(targetId);
        if (target == null) {
            Msg.error(player, "Игрок не в сети.");
            return;
        }

        service.execute(player, target, type, count);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onFish(PlayerFishEvent event) {
        Player player = event.getPlayer();
        if (items.isSpawnItem(player.getInventory().getItemInMainHand())
                || items.isSpawnItem(player.getInventory().getItemInOffHand())) {
            event.setCancelled(true);
        }
    }
}
