package ashinmc.myfirstplugin;

import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CheckCommand implements CommandExecutor {

    private final ClaimManager claimManager;

    public CheckCommand(ClaimManager claimManager) {
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
        UUID owner = claimManager.getChunkOwner(chunk);
        if (owner == null) {
            player.sendMessage("This chunk is unclaimed.");
        } else if (owner.equals(player.getUniqueId())) {
            player.sendMessage("You own this chunk.");
        } else {
            player.sendMessage("This chunk is owned by another player.");
        }
        return true;
    }
}
