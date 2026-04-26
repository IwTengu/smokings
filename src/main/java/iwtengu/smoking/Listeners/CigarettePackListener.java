package iwtengu.smoking.Listeners;

import iwtengu.smoking.Items.Cigarette;
import iwtengu.smoking.Items.CigarettePack;
import iwtengu.smoking.Utils.CooldownUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class CigarettePackListener implements Listener {

    private static final long COOLDOWN_TIME = 1000;

    @EventHandler
    public void onUse(PlayerInteractEvent event) {

        Action action = event.getAction();

        if (action != Action.RIGHT_CLICK_AIR &&
                action != Action.RIGHT_CLICK_BLOCK) return;

        if (event.getHand() != EquipmentSlot.HAND) return;

        ItemStack item = event.getItem();

        if (item == null || !CigarettePack.isPack(item)) return;

        Player player = event.getPlayer();

        if (CooldownUtil.isOnCooldown(player, "cd_pack", COOLDOWN_TIME)) {
            return;
        }

        int amount = CigarettePack.getAmount(item);

        if (amount <= 0) {
            item.setAmount(0);
            return;
        }

        player.getInventory().addItem(Cigarette.get());

        CooldownUtil.setCooldown(player, "cd_pack");

        amount--;

        if (amount <= 0) {
            item.setAmount(0);
            return;
        }

        CigarettePack.setAmount(item, amount);
    }
}