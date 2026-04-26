package iwtengu.smoking.Listeners;

import iwtengu.smoking.Items.CigarettePack;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CigarettePackListener implements Listener {

    @EventHandler
    public void onUse(PlayerInteractEvent event) {

        Action action = event.getAction();

        if (action != Action.RIGHT_CLICK_AIR &&
                action != Action.RIGHT_CLICK_BLOCK) return;

        ItemStack item = event.getItem();

        if (!CigarettePack.isPack(item)) return;

        if (item == null) return;

        int amount = CigarettePack.getAmount(item);

        if (amount <= 0) {
            item.setAmount(0);
            return;
        }

        event.getPlayer().getInventory().addItem(
                new ItemStack(Material.STICK)
        );

        amount--;

        if (amount <= 0) {
            item.setAmount(0);
            return;
        }

        CigarettePack.setAmount(item, amount);
    }
}