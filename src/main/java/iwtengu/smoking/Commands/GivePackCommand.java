package iwtengu.smoking.Commands;

import iwtengu.smoking.Items.CigarettePack;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GivePackCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) return true;

        player.getInventory().addItem(CigarettePack.get());

        player.sendMessage("§aВы получили пачку сигарет.");

        return true;
    }
}