package ashinmc.myfirstplugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private ClaimManager claimManager;

    @Override
    public void onEnable() {
        this.claimManager = new ClaimManager();
        getCommand("claimchunk").setExecutor(new ClaimCommand(claimManager));
        getCommand("unclaimchunk").setExecutor(new UnclaimCommand(claimManager));
        getCommand("checkchunk").setExecutor(new CheckCommand(claimManager));
        getCommand("infochunk").setExecutor(new InfoCommand(claimManager));
        getCommand("visualizechunk").setExecutor(new VisualizeCommand(claimManager, this));
        Bukkit.getPluginManager().registerEvents(new ChunkProtectionListener(claimManager), this);
    }

    @Override
    public void onDisable() {
        // Save claims to disk if necessary
    }
}
