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
import org.bukkit.block.data.Ageable;
import org.bukkit.event.block.BlockGrowEvent;

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
        int radius = config.getInt("radius");

        // Iterate through a square area around the player
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                Location loc = playerLocation.clone().add(x, 0, z);
                Block block = loc.getBlock();

                Material blockType = block.getType();

                // Check if the block is a sapling or a crop
                if (isSapling(blockType)) {
                    if (config.getBoolean("TREES")) {
                        // Replace sapling with a grown tree of the same type
                        TreeType treeType = getTreeTypeFromSapling(blockType);
                        block.setType(Material.AIR); // Clear the sapling
                        block.getWorld().generateTree(loc, treeType); // Generate the tree

                        // Add green particles
                        block.getWorld().spawnParticle(Particle.HAPPY_VILLAGER, loc.add(0.5, 0.5, 0.5), 20, 0.5, 0.5, 0.5);
                    }
                } else if (isCrop(blockType)) {
                    // Check if the crop is fully grown
                    if (!isFullyGrownCrop(block)) {
                        // Increment the growth stage of the crop
                        Ageable ageable = (Ageable) block.getBlockData();
                        if (ageable.getAge() < ageable.getMaximumAge()) {
                            ageable.setAge(ageable.getAge() + 1);
                            block.setBlockData(ageable);
                            block.getWorld().spawnParticle(Particle.HAPPY_VILLAGER, loc.add(0.5, 0.5, 0.5), 20, 0.5, 0.5, 0.5);
                        }
                    }
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
                material == Material.DARK_OAK_SAPLING ||
                material == Material.PALE_OAK_SAPLING ||
                material == Material.CHERRY_SAPLING ||
                material == Material.MANGROVE_PROPAGULE;
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
            case PALE_OAK_SAPLING:
                return TreeType.PALE_OAK;
            case CHERRY_SAPLING:
                return TreeType.CHERRY;
            case MANGROVE_PROPAGULE:
                return TreeType.MANGROVE;
            default:
                return TreeType.TREE; // Default to oak tree if the sapling type is unknown
        }
    }

    // Helper method to check if a material is a crop
    private boolean isCrop(Material material) {
        // Check if the material is a crop block and its enabled in the config
        FileConfiguration config = plugin.getConfig();
        switch (material) {
            case WHEAT:
                return config.getBoolean("WHEAT");
            case CARROTS:
                return config.getBoolean("CARROTS");
            case POTATOES:
                return config.getBoolean("POTATOES");
            case BEETROOTS:
                return config.getBoolean("BEETROOTS");
            case NETHER_WART:
                return config.getBoolean("NETHER_WART");
            case TORCHFLOWER_CROP:
                return config.getBoolean("TORCHFLOWER");
            case PITCHER_CROP:
                return config.getBoolean("PITCHER");
            default:
                return false;
        }
    }

    // Helper method to check if a crop block is fully grown
    private boolean isFullyGrownCrop(Block block) {
        switch (block.getType()) {
            case WHEAT:
            case POTATOES:
            case BEETROOTS:
            case CARROTS:
            case TORCHFLOWER_CROP:
            case PITCHER_CROP:
                Ageable crop = (Ageable) block.getBlockData();
                return crop.getAge() == crop.getMaximumAge();
            case NETHER_WART:
                Ageable netherWart = (Ageable) block.getBlockData();
                return netherWart.getAge() == 3;
            default:
                return false;
        }
    }

    // Helper method to get the next growth stage of a crop
    private Material getNextGrowthStage(Material cropType) {
        switch (cropType) {
            case WHEAT:
                return Material.WHEAT;
            case CARROTS:
                return Material.CARROTS;
            case POTATOES:
                return Material.POTATOES;
            case BEETROOTS:
                return Material.BEETROOTS;
            case NETHER_WART:
                return Material.NETHER_WART;
            case TORCHFLOWER_CROP:
                return Material.TORCHFLOWER_CROP;
            case PITCHER_CROP:
                return Material.PITCHER_CROP;
            default:
                return null;
        }
    }
}
