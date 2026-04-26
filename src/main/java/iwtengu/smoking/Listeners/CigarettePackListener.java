package iwtengu.smoking.Listeners;

import iwtengu.smoking.Items.Cigarette;
import iwtengu.smoking.Items.CigarettePack;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CigarettePackListener implements Listener {

    private final Map<UUID, Long> cooldowns = new HashMap<>();

    private static final long COOLDOWN_TIME = 1000;

    @EventHandler
    public void onUse(PlayerInteractEvent event) {

        Action action = event.getAction();

        if (action != Action.RIGHT_CLICK_AIR &&
                action != Action.RIGHT_CLICK_BLOCK) return;

        ItemStack item = event.getItem();

        if (!CigarettePack.isPack(item)) return;

        if (item == null) return;

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        long now = System.currentTimeMillis();

        if (cooldowns.containsKey(uuid)) {
            long lastUse = cooldowns.get(uuid);

            if ((now - lastUse) < COOLDOWN_TIME) {
                return;
            }
        }

        int amount = CigarettePack.getAmount(item);

        if (amount <= 0) {
            item.setAmount(0);
            return;
        }

        player.getInventory().addItem(
                Cigarette.get()
        );

        cooldowns.put(uuid, now);

        amount--;

        if (amount <= 0) {
            item.setAmount(0);
            return;
        }

        CigarettePack.setAmount(item, amount);
    }
}