package iwtengu.smoking.Items;

import iwtengu.smoking.Commands.GivePackCommand;
import iwtengu.smoking.Listeners.CigarettePackListener;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CigarettePack {

    public static final int MAX = 6;

    public static void register(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(
                new CigarettePackListener(),
                plugin
        );

        plugin.getCommand("givepack")
                .setExecutor(new GivePackCommand());
    }

    public static ItemStack create() {

        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.WHITE + "Пачка сигарет");

        meta.getPersistentDataContainer().set(
                Keys.CIGARETTES,
                PersistentDataType.INTEGER,
                MAX
        );

        meta.getPersistentDataContainer().set(
                Keys.UNIQUE,
                PersistentDataType.STRING,
                UUID.randomUUID().toString()
        );

        updateLore(meta, MAX);

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
                .get(Keys.CIGARETTES, PersistentDataType.INTEGER);

        return val == null ? 0 : val;
    }

    public static void setAmount(ItemStack item, int amount) {

        if (item == null || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();

        meta.getPersistentDataContainer().set(
                Keys.CIGARETTES,
                PersistentDataType.INTEGER,
                amount
        );

        updateLore(meta, amount);

        item.setItemMeta(meta);
    }

    private static void updateLore(ItemMeta meta, int amount) {

        List<String> lore = new ArrayList<>();

        lore.add(" ");
        lore.add(ChatColor.GRAY + "Сигарет в пачке: " + amount + "/" + MAX);
        lore.add(" ");

        meta.setLore(lore);
    }

    public static boolean isPack(ItemStack item) {

        if (item == null || item.getType() != Material.PAPER) return false;
        if (!item.hasItemMeta()) return false;

        return item.getItemMeta()
                .getPersistentDataContainer()
                .has(Keys.CIGARETTES, PersistentDataType.INTEGER);
    }
}