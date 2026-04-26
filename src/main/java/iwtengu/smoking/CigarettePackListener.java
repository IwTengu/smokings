package iwtengu.smoking;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CigarettePackListener implements Listener {

    private final CigarettePackItem cigarettePackItem;

    public CigarettePackListener(CigarettePackItem cigarettePackItem) {
        this.cigarettePackItem = cigarettePackItem;
    }

    @EventHandler
    public void onUsePack(PlayerInteractEvent event) {

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        ItemStack item = event.getItem();

        if (!cigarettePackItem.isCigarettePack(item)) {
            return;
        }

        Player player = event.getPlayer();

        int cigarettes = cigarettePackItem.getCigarettes(item);

        if (cigarettes <= 0) {
            player.getInventory().remove(item);
            return;
        }

        player.getInventory().addItem(new ItemStack(Material.STICK, 1));

        cigarettes--;

        if (cigarettes <= 0) {
            player.getInventory().remove(item);
            return;
        }

        cigarettePackItem.setCigarettes(item, cigarettes);
    }
}