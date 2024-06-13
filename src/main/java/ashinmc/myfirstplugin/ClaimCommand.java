package ashinmc.myfirstplugin;

import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClaimCommand implements CommandExecutor {

    private final ClaimManager claimManager;

    public ClaimCommand(ClaimManager claimManager) {
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
        if (claimManager.claimChunk(player, chunk)) {
            player.sendMessage("Chunk claimed successfully!");
        } else {
            player.sendMessage("This chunk is already claimed.");
        }
        return true;
    }
}
