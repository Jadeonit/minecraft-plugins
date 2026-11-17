package ru.admintools;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public final class SpawnCommand implements TabExecutor {

    private static final int MODE_UNKNOWN = 0;
    private static final int MODE_ITEM = 1;
    private static final int MODE_NOW = 2;

    private final AdminToolsPlugin plugin;
    private final PluginConfig config;
    private final SettingsManager settings;
    private final SpawnService service;
    private final SpawnItems items;

    public SpawnCommand(AdminToolsPlugin plugin, PluginConfig config,
                        SettingsManager settings, SpawnService service, SpawnItems items) {
        this.plugin = plugin;
        this.config = config;
        this.settings = settings;
        this.service = service;
        this.items = items;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload") && canReload(sender)) {
            plugin.reloadAll();
            Msg.ok(sender, "Готово.");
            return true;
        }

        if (!(sender instanceof Player player)) {
            usage(sender, label);
            return true;
        }

        if (args.length != 5 || !args[0].equalsIgnoreCase("spawn")) {
            usage(player, label);
            return true;
        }

        EntityType type = PluginConfig.parseType(args[1]);
        if (type == null || !EntityTypes.isAllowed(type, config)) {
            Msg.error(player, "Недоступно.");
            return true;
        }

        Integer count = parseCount(args[2]);
        if (count == null) {
            Msg.error(player, "Недоступно.");
            return true;
        }
        if (count == PlayerSettings.UNLIMITED && !player.hasPermission("admintools.unlimited")) {
            Msg.error(player, "Недоступно.");
            return true;
        }

        Player target = resolveTarget(player, args[3]);
        if (target == null) {
            Msg.error(player, "Игрок не в сети.");
            return true;
        }

        int mode = parseMode(args[4]);
        if (mode == MODE_UNKNOWN) {
            usage(player, label);
            return true;
        }

        PlayerSettings current = settings.get(player);
        current.setProjectile(type);
        current.setCount(count);
        current.setTarget(target.getUniqueId());

        if (mode == MODE_ITEM) {
            if (!player.hasPermission("admintools.spawn")) {
                Msg.error(player, "Недоступно.");
                return true;
            }
            ItemStack item = items.createItem(type, count, target.getUniqueId(), target.getName());
            var leftovers = player.getInventory().addItem(item);
            for (ItemStack left : leftovers.values()) {
                player.getWorld().dropItemNaturally(player.getLocation(), left);
            }
            Msg.ok(player, "Готово.");
            return true;
        }

        service.execute(player, target, type, count);
        return true;
    }

    private static boolean canReload(CommandSender sender) {
        return sender instanceof Player player
                ? player.hasPermission("admintools.reload")
                : sender.hasPermission("admintools.reload");
    }

    private static Player resolveTarget(Player player, String raw) {
        String value = raw.toLowerCase(Locale.ROOT);
        if (value.equals("me") || value.equals("self") || value.equals("я") || value.equals("себя")) {
            return player;
        }
        Player exact = Bukkit.getPlayerExact(raw);
        return exact != null ? exact : Bukkit.getPlayer(raw);
    }

    private static int parseMode(String raw) {
        return switch (raw.toLowerCase(Locale.ROOT)) {
            case "item", "предмет", "палочка" -> MODE_ITEM;
            case "now", "run", "сразу", "сейчас" -> MODE_NOW;
            default -> MODE_UNKNOWN;
        };
    }

    private static Integer parseCount(String raw) {
        String value = raw.trim().toLowerCase(Locale.ROOT);
        if (value.equals("unlimited") || value.equals("inf") || value.equals("infinity")
                || value.equals("all") || value.equals("∞") || value.equals("*") || value.equals("-1")) {
            return PlayerSettings.UNLIMITED;
        }
        try {
            int parsed = Integer.parseInt(value);
            return parsed > 0 ? parsed : null;
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private static void usage(CommandSender sender, String label) {
        Msg.info(sender, "Использование: /" + label + " spawn <сущность> <кол-во> <игрок> <now|item>");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}
