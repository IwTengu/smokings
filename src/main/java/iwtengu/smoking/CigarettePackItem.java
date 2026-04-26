package iwtengu.smoking;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CigarettePackItem {

    private final NamespacedKey cigarettesKey;

    private final NamespacedKey uniqueKey;

    public CigarettePackItem(JavaPlugin plugin) {
        this.cigarettesKey = new NamespacedKey(plugin, "cigarettes_count");
        this.uniqueKey = new NamespacedKey(plugin, "unique_pack_id");
    }

    public ItemStack createPack() {

        ItemStack item = new ItemStack(Material.PAPER, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.WHITE + "Пачка сигарет");

        meta.getPersistentDataContainer().set(cigarettesKey, PersistentDataType.INTEGER, 6);

        meta.getPersistentDataContainer().set(uniqueKey, PersistentDataType.STRING, UUID.randomUUID().toString());

        updateLore(meta, 6);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);

        return item;
    }

    public boolean isCigarettePack(ItemStack item) {

        if (item == null) return false;
        if (item.getType() != Material.PAPER) return false;
        if (!item.hasItemMeta()) return false;

        ItemMeta meta = item.getItemMeta();

        if (!meta.hasDisplayName()) return false;

        return meta.getDisplayName().equals(ChatColor.WHITE + "Пачка сигарет");
    }

    public int getCigarettes(ItemStack item) {

        ItemMeta meta = item.getItemMeta();

        Integer amount = meta.getPersistentDataContainer().get(cigarettesKey, PersistentDataType.INTEGER);

        if (amount == null) return 0;

        return amount;
    }

    public void setCigarettes(ItemStack item, int amount) {

        ItemMeta meta = item.getItemMeta();

        meta.getPersistentDataContainer().set(cigarettesKey, PersistentDataType.INTEGER, amount);

        updateLore(meta, amount);

        item.setItemMeta(meta);
    }

    private void updateLore(ItemMeta meta, int amount) {

        List<String> lore = new ArrayList<>();

        lore.add(" ");
        lore.add(ChatColor.GRAY + "Сигарет в пачке " + amount + "/6");
        lore.add(" ");

        meta.setLore(lore);
    }
}