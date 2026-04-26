package iwtengu.smoking.Utils;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class CooldownUtil {

    private static NamespacedKey KEY;

    public static void init(JavaPlugin plugin) {
        KEY = new NamespacedKey(plugin, "cooldown");
    }
    public static boolean isOnCooldown(Player player, String id, long cooldownMillis) {

        long now = System.currentTimeMillis();

        String key = id + "_time";

        Long last = player.getPersistentDataContainer()
                .get(new NamespacedKey(player.getServer().getPluginManager().getPlugin("Smoking"), key),
                        PersistentDataType.LONG);

        if (last == null) return false;

        return (now - last) < cooldownMillis;
    }

    public static void setCooldown(Player player, String id) {

        String key = id + "_time";

        player.getPersistentDataContainer().set(
                new NamespacedKey(player.getServer().getPluginManager().getPlugin("Smoking"), key),
                PersistentDataType.LONG,
                System.currentTimeMillis()
        );
    }
}