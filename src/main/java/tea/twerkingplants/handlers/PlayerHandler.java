package tea.twerkingplants.handlers;

import com.sun.tools.classfile.ConstantPool;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.configuration.file.FileConfiguration;
import tea.twerkingplants.TwerkingPlants;

public class PlayerHandler implements Listener {
    private TwerkingPlants plugin;

    public PlayerHandler(TwerkingPlants plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        Bukkit.getLogger().info("Player toggled sneak");
        Player player = event.getPlayer();
        FileConfiguration config = plugin.getConfig();
        int radius = config.getInt("radius");

        if (event.isSneaking()) {
            // Player started crouching
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    Block targetBlock = player.getLocation().add(x, 0, z).getBlock();

                    // Check if the block is eligible for bone meal (e.g., grass or crops)
                    if (isEligibleForBoneMeal(targetBlock)) {
                        // Apply bone meal to the block
                    }
                }
            }
        }
    }

    private boolean isEligibleForBoneMeal(Block block) {
        Material type = block.getType();
        FileConfiguration config = plugin.getConfig();

        Bukkit.getLogger().info("Checking block: " + type.toString());

        // Check if the block type is present in the config and set to true
        if (config.contains(type.toString()) && config.getBoolean(type.toString())) {
            Bukkit.getLogger().info("Block " + type.toString() + " is eligible for bone meal");
            return true;
        }

        Bukkit.getLogger().info("Block " + type.toString() + " is not eligible for bone meal");
        return false;
    }

}
