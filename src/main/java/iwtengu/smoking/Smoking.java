package iwtengu.smoking;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class Smoking extends JavaPlugin {

    @Override
    public void onEnable() {

        CigarettePackItem cigarettePackItem = new CigarettePackItem(this);

        getServer().getPluginManager().registerEvents(
                new CigarettePackListener(cigarettePackItem),
                this
        );

        getCommand("givepack").setExecutor(new GivePackCommand(this));
    }
    @Override
    public void onDisable() {

    }
}
