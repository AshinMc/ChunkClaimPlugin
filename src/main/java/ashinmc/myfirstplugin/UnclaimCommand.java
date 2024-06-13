package ashinmc.myfirstplugin;

import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnclaimCommand implements CommandExecutor {

    private final ClaimManager claimManager;

    public UnclaimCommand(ClaimManager claimManager) {
        this.claimManager = claimManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }
        Player player = (Player) sender;
        Chunk chunk = player.getLocation().getChunk();
        if (claimManager.unclaimChunk(player, chunk)) {
            player.sendMessage("Chunk unclaimed successfully!");
        } else {
            player.sendMessage("You do not own this chunk.");
        }
        return true;
    }
}
