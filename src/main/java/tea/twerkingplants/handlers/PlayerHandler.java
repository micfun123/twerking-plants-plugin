package tea.twerkingplants.handlers;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import tea.twerkingplants.TwerkingPlants;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerHandler implements Listener {
    private TwerkingPlants plugin;

    public PlayerHandler(TwerkingPlants plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();

        // Check if the player starts sneaking
        if (!event.isSneaking())
            return;

        UUID playerId = player.getUniqueId();

        // pick a number between 1 and 5 if it is 1 then grow trees around the player
        if ((int) (Math.random() * 5) == 1) {
            growTreesAroundPlayer(player);
        }


    }

    private void growTreesAroundPlayer(Player player) {
        Location playerLocation = player.getLocation();
        FileConfiguration config = plugin.getConfig();
        int radius = config.getInt("twerking-radius");

        // Iterate through a square area around the player
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                Location loc = playerLocation.clone().add(x, 0, z);
                Block block = loc.getBlock();

                // Check if the block is a sapling
                if (isSapling(block.getType())) {
                    Material saplingType = block.getType();
                    // Replace sapling with a grown tree of the same type
                    TreeType treeType = getTreeTypeFromSapling(saplingType);
                    block.setType(Material.AIR); // Clear the sapling
                    block.getWorld().generateTree(loc, treeType); // Generate the tree

                    // Add green particles
                    block.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, loc.add(0.5, 0.5, 0.5), 20, 0.5, 0.5, 0.5);
                }
            }
        }
    }

    // Helper method to check if a material is a sapling
    private boolean isSapling(Material material) {
        return material == Material.OAK_SAPLING ||
                material == Material.BIRCH_SAPLING ||
                material == Material.SPRUCE_SAPLING ||
                material == Material.JUNGLE_SAPLING ||
                material == Material.ACACIA_SAPLING ||
                material == Material.DARK_OAK_SAPLING;
    }

    // Helper method to get the corresponding tree type from a sapling type
    private TreeType getTreeTypeFromSapling(Material saplingType) {
        switch (saplingType) {
            case OAK_SAPLING:
                return TreeType.TREE;
            case BIRCH_SAPLING:
                return TreeType.BIRCH;
            case SPRUCE_SAPLING:
                return TreeType.REDWOOD;
            case JUNGLE_SAPLING:
                return TreeType.SMALL_JUNGLE;
            case ACACIA_SAPLING:
                return TreeType.ACACIA;
            case DARK_OAK_SAPLING:
                return TreeType.DARK_OAK;
            default:
                return TreeType.TREE; // Default to oak tree if the sapling type is unknown
        }
    }
}
