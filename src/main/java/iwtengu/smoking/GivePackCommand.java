package iwtengu.smoking;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class GivePackCommand implements CommandExecutor {

    private final CigarettePackItem cigarettePackItem;

    public GivePackCommand(JavaPlugin plugin) {
        this.cigarettePackItem = new CigarettePackItem(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Только игрок может использовать эту команду.");
            return true;
        }

        player.getInventory().addItem(cigarettePackItem.createPack());

        player.sendMessage("§aВы получили пачку сигарет.");

        return true;
    }
}