package ashinmc.myfirstplugin;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.UUID;

public class ChunkProtectionListener implements Listener {

    private final ClaimManager claimManager;

    public ChunkProtectionListener(ClaimManager claimManager) {
        this.claimManager = claimManager;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Chunk chunk = event.getBlock().getChunk();
        UUID owner = claimManager.getChunkOwner(chunk);
        if (owner != null && !owner.equals(player.getUniqueId())) {
            player.sendMessage("You cannot break blocks in this chunk.");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Chunk chunk = event.getBlock().getChunk();
        UUID owner = claimManager.getChunkOwner(chunk);
        if (owner != null && !owner.equals(player.getUniqueId())) {
            player.sendMessage("You cannot place blocks in this chunk.");
            event.setCancelled(true);
        }
    }
}
