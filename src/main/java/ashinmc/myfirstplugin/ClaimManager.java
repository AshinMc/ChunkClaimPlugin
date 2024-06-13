package ashinmc.myfirstplugin;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClaimManager {


    private final Map<String, UUID> chunkClaims = new HashMap<>();

    public boolean claimChunk(Player player, Chunk chunk) {
        String chunkKey = getChunkKey(chunk);
        if (chunkClaims.containsKey(chunkKey)) {
            return false; // Chunk is already claimed
        }
        chunkClaims.put(chunkKey, player.getUniqueId());
        return true;
    }

    public boolean unclaimChunk(Player player, Chunk chunk) {
        String chunkKey = getChunkKey(chunk);
        if (!chunkClaims.containsKey(chunkKey)) {
            return false; // Not claimed
        }
        if (!chunkClaims.get(chunkKey).equals(player.getUniqueId())) {
            return false; // Not the owner
        }
        chunkClaims.remove(chunkKey);
        return true;
    }

    public UUID getChunkOwner(Chunk chunk) {
        return chunkClaims.get(getChunkKey(chunk));
    }

    public Map<String, UUID> getChunkClaims() {
        return chunkClaims;
    }

    private String getChunkKey(Chunk chunk) {
        return chunk.getWorld().getName() + ";" + chunk.getX() + ";" + chunk.getZ();
    }
}
