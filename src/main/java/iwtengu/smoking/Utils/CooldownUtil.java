package iwtengu.smoking.Utils;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownUtil {

    private static final Map<UUID, Long> cooldowns = new HashMap<>();

    private static JavaPlugin plugin;

    public static void init(JavaPlugin pl) {
        plugin = pl;
    }

    private static UUID getSystemId(Player player, String keyName) {

        NamespacedKey key = new NamespacedKey(plugin, keyName);

        String value = player.getPersistentDataContainer()
                .get(key, PersistentDataType.STRING);

        if (value == null) {
            value = UUID.randomUUID().toString();

            player.getPersistentDataContainer().set(
                    key,
                    PersistentDataType.STRING,
                    value
            );
        }

        return UUID.fromString(value);
    }

    public static boolean isOnCooldown(Player player, String keyName, long time) {

        UUID id = getSystemId(player, keyName);

        long now = System.currentTimeMillis();

        if (!cooldowns.containsKey(id)) return false;

        return (now - cooldowns.get(id)) < time;
    }

    public static void setCooldown(Player player, String keyName) {

        UUID id = getSystemId(player, keyName);

        cooldowns.put(id, System.currentTimeMillis());
    }
}