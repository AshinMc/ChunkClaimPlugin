package ashinmc.myfirstplugin;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class VisualizeCommand implements CommandExecutor {

    private final ClaimManager claimManager;
    private final Main plugin;

    public VisualizeCommand(ClaimManager claimManager, Main plugin) {
        this.claimManager = claimManager;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }
        Player player = (Player) sender;
        Chunk chunk = player.getLocation().getChunk();
        if (!claimManager.getChunkOwner(chunk).equals(player.getUniqueId())) {
            player.sendMessage("You do not own this chunk.");
            return true;
        }
        showChunkBorders(player, chunk);
        player.sendMessage("Visualizing the borders of your claimed chunk.");
        return true;
    }

    private void showChunkBorders(Player player, Chunk chunk) {
        int minX = chunk.getX() * 16;
        int minZ = chunk.getZ() * 16;
        int maxX = minX + 15;
        int maxZ = minZ + 15;
        int y = player.getWorld().getHighestBlockYAt(minX, minZ) + 1;

        new BukkitRunnable() {
            int counter = 0;

            @Override
            public void run() {
                if (counter >= 20 * 3) { // 3 seconds (60 ticks)
                    this.cancel();
                    return;
                }
                for (int x = minX, tempy =y,tempx = x; x <= maxX; x++, tempy++) {
                    player.spawnParticle(Particle.HAPPY_VILLAGER, new Location(player.getWorld(), x, y, minZ), 1);
                    player.spawnParticle(Particle.HAPPY_VILLAGER, new Location(player.getWorld(), x, y, maxZ), 1);
                    player.spawnParticle(Particle.HAPPY_VILLAGER, new Location(player.getWorld(), tempx, tempy, minZ), 1);
                    player.spawnParticle(Particle.HAPPY_VILLAGER, new Location(player.getWorld(), tempx, tempy, maxZ), 1);

                }
                for (int z = minZ,tempz = z, tempy =y; z <= maxZ; z++, tempy++) {
                    player.spawnParticle(Particle.HAPPY_VILLAGER, new Location(player.getWorld(), minX, y, z), 1);
                    player.spawnParticle(Particle.HAPPY_VILLAGER, new Location(player.getWorld(), maxX, y, z), 1);
                    player.spawnParticle(Particle.HAPPY_VILLAGER, new Location(player.getWorld(), minX, tempy, tempz), 1);
                    player.spawnParticle(Particle.HAPPY_VILLAGER, new Location(player.getWorld(), maxX, tempy, tempz), 1);
                }
                counter++;
            }
        }.runTaskTimer(plugin, 0, 10); // Repeat every 10 ticks (0.5 seconds)
    }

}
