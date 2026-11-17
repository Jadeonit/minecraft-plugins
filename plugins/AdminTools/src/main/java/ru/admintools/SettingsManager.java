package ru.admintools;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class SettingsManager {

    private final Map<UUID, PlayerSettings> settings = new HashMap<>();

    private final PluginConfig config;

    public SettingsManager(PluginConfig config) {
        this.config = config;
    }

    public PlayerSettings get(Player player) {
        return settings.computeIfAbsent(player.getUniqueId(),
                id -> new PlayerSettings(config.defaultProjectile(), config.defaultCount(), null));
    }

    public void reset(Player player) {
        settings.remove(player.getUniqueId());
    }

    public void forget(UUID uuid) {
        settings.remove(uuid);
    }
}
