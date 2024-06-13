package ashinmc.myfirstplugin;

import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InfoCommand implements CommandExecutor {

    private final ClaimManager claimManager;

    public InfoCommand(ClaimManager claimManager) {
        this.claimManager = claimManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }
        Player player = (Player) sender;
        UUID playerId = player.getUniqueId();

        List<String> claimedChunks = new ArrayList<>();

        for (Map.Entry<String, UUID> entry : claimManager.getChunkClaims().entrySet()) {
            if (entry.getValue().equals(playerId)) {
                String[] parts = entry.getKey().split(";");
                String world = parts[0];
                int x = Integer.parseInt(parts[1]);
                int z = Integer.parseInt(parts[2]);
                claimedChunks.add("World: " + world + ", X: " + x + ", Z: " + z);
            }
        }

        if (claimedChunks.isEmpty()) {
            player.sendMessage("You have no claimed chunks.");
        } else {
            player.sendMessage("Your claimed chunks:");
            for (String chunkInfo : claimedChunks) {
                player.sendMessage(chunkInfo);
            }
        }

        return true;
    }
}
