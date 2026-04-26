package iwtengu.smoking.system;

import iwtengu.smoking.Items.CigarettePack;
import iwtengu.smoking.Items.Keys;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemSystem {

    public static void init(JavaPlugin plugin) {

        Keys.init(plugin);
//bjkj
        CigarettePack.register(plugin);
    }
}