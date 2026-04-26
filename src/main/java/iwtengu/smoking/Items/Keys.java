package iwtengu.smoking.Items;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class Keys {

    public static NamespacedKey CIGARETTES;
    public static NamespacedKey UNIQUE;

    public static void init(JavaPlugin plugin) {
//bj
        CIGARETTES = new NamespacedKey(plugin, "cigarettes_count");
        UNIQUE = new NamespacedKey(plugin, "unique_pack_id");
    }
}