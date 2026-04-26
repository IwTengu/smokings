package iwtengu.smoking.Items;

import iwtengu.smoking.Listeners.CigaretteListener;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class Cigarette {

    public static int MAX = 3;

    public static void register(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(
                new CigaretteListener(),
                plugin
        );
    }

    public static ItemStack create() {
        ItemStack item = new ItemStack(Material.STICK);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.AQUA + "Сигарета");

        meta.getPersistentDataContainer().set(
                Keys.PUFFS,
                PersistentDataType.INTEGER,
                MAX
        );

        meta.getPersistentDataContainer().set(
                Keys.UNIQUE,
                PersistentDataType.STRING,
                UUID.randomUUID().toString()
        );
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack get() {
        return create().clone();
    }

    public static int getAmount(ItemStack item) {

        if (item == null || !item.hasItemMeta()) return 0;

        Integer val = item.getItemMeta()
                .getPersistentDataContainer()
                .get(Keys.PUFFS, PersistentDataType.INTEGER);

        return val == null ? 0 : val;
    }

    public static void setAmount(ItemStack item, int amount) {

        if (item == null || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();

        meta.getPersistentDataContainer().set(
                Keys.PUFFS,
                PersistentDataType.INTEGER,
                amount
        );

        item.setItemMeta(meta);
    }

    public static boolean isCigarette(ItemStack item) {

        if (item == null || item.getType() != Material.STICK) return false;
        if (!item.hasItemMeta()) return false;

        return item.getItemMeta()
                .getPersistentDataContainer()
                .has(Keys.PUFFS, PersistentDataType.INTEGER);
    }
}
