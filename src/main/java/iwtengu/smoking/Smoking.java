package iwtengu.smoking;

import iwtengu.smoking.system.ItemSystem;
import org.bukkit.plugin.java.JavaPlugin;

public final class Smoking extends JavaPlugin {

    @Override
    public void onEnable() {

        ItemSystem.init(this);
    }

    @Override
    public void onDisable() {

    }
}