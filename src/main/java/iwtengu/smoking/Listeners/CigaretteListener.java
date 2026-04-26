package iwtengu.smoking.Listeners;

import iwtengu.smoking.Items.Cigarette;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CigaretteListener implements Listener {

    private static final long COOLDOWN_TIME = 5000;

    private final Map<UUID, Long> cooldowns = new HashMap<>();

    @EventHandler
    public void onSmoke(PlayerInteractEvent event) {

        Action action = event.getAction();

        if (action != Action.RIGHT_CLICK_AIR &&
                action != Action.RIGHT_CLICK_BLOCK) return;

        if (event.getHand() != EquipmentSlot.HAND) return;

        ItemStack item = event.getItem();

        if (!Cigarette.isCigarette(item)) return;

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        long now = System.currentTimeMillis();

        if (cooldowns.containsKey(uuid)) {
            long lastUse = cooldowns.get(uuid);
            if ((now - lastUse) < COOLDOWN_TIME) return;
        }

        int puffs = Cigarette.getAmount(item);

        if (puffs <= 0) {
            item.setAmount(0);
            return;
        }

        startSmoke(player);

        puffs--;

        if (puffs <= 0) {
            item.setAmount(0);
        } else {
            Cigarette.setAmount(item, puffs);
        }

        cooldowns.put(uuid, now);

        showActionBar(player, puffs);
    }

    private void startSmoke(Player player) {

        new BukkitRunnable() {

            int ticks = 0;

            @Override
            public void run() {

                if (ticks >= 60) {
                    cancel();
                    return;
                }

                Vector dir = player.getLocation().getDirection().normalize();

                player.getWorld().spawnParticle(
                        Particle.CAMPFIRE_SIGNAL_SMOKE,
                        player.getEyeLocation().add(dir.multiply(0.3)),
                        1,
                        0, 0, 0,
                        0.01
                );

                ticks += 2;
            }
        }.runTaskTimer(
                JavaPlugin.getProvidingPlugin(getClass()),
                0L,
                2L
        );
    }

    private void showActionBar(Player player, int puffs) {

        StringBuilder bar = new StringBuilder();

        for (int i = 0; i < puffs; i++) {
            bar.append(ChatColor.WHITE).append("▌");
        }

        for (int i = puffs; i < 3; i++) {
            bar.append(ChatColor.GRAY).append("▌");
        }

        player.spigot().sendMessage(
                ChatMessageType.ACTION_BAR,
                new TextComponent(bar.toString())
        );
    }
}