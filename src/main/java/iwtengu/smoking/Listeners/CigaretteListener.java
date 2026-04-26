package iwtengu.smoking.Listeners;

import iwtengu.smoking.Items.Cigarette;
import iwtengu.smoking.Utils.CooldownUtil;
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
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class CigaretteListener implements Listener {

    private static final long COOLDOWN_TIME = 5000;

    @EventHandler
    public void onSmoke(PlayerInteractEvent event) {

        if (event.getAction() != Action.RIGHT_CLICK_AIR &&
                event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        if (event.getHand() != EquipmentSlot.HAND) return;

        ItemStack item = event.getItem();

        if (!Cigarette.isCigarette(item)) return;

        Player player = event.getPlayer();

        if (CooldownUtil.isOnCooldown(player, "cd_smoke", COOLDOWN_TIME)) {
            return;
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

        CooldownUtil.setCooldown(player, "cd_smoke");

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
                org.bukkit.plugin.java.JavaPlugin.getProvidingPlugin(getClass()),
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