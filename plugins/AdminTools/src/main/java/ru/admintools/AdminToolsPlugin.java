package ru.admintools;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class AdminToolsPlugin extends JavaPlugin {

    private PluginConfig pluginConfig;
    private SettingsManager settings;
    private SpawnService service;
    private SpawnItems items;
    private BukkitTask cleanupTask;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Msg.init(getConfig().getString("messages.prefix", ""));

        this.pluginConfig = new PluginConfig();
        this.pluginConfig.reload(getConfig());

        this.settings = new SettingsManager(pluginConfig);
        this.service = new SpawnService(this, pluginConfig);
        this.items = new SpawnItems(this, pluginConfig);

        SpawnCommand command = new SpawnCommand(this, pluginConfig, settings, service, items);
        PluginCommand pluginCommand = getCommand("at");
        if (pluginCommand != null) {
            pluginCommand.setExecutor(command);
            pluginCommand.setTabCompleter(command);
        } else {
            getLogger().severe("Command 'at' is missing in plugin.yml!");
        }

        getServer().getPluginManager().registerEvents(new SpawnItemListener(items, service, settings), this);
        getServer().getPluginManager().registerEvents(new FallDamageListener(), this);

        this.cleanupTask = getServer().getScheduler().runTaskTimer(this, service::cleanupExpired, 100L, 100L);

        getLogger().info("AdminTools enabled.");
    }

    @Override
    public void onDisable() {
        if (cleanupTask != null) {
            cleanupTask.cancel();
            cleanupTask = null;
        }
        if (service != null) {
            service.removeAll();
        }
        getLogger().info("AdminTools disabled.");
    }

    public void reloadAll() {
        reloadConfig();
        pluginConfig.reload(getConfig());
        Msg.init(getConfig().getString("messages.prefix", ""));
    }

    public PluginConfig pluginConfig() {
        return pluginConfig;
    }
}
