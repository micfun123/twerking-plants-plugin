package tea.twerkingplants;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import tea.twerkingplants.handlers.PlayerHandler;

public final class TwerkingPlants extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        Bukkit.getLogger().info("\n" +
                " ______  __    __    ___  ____   __  _  ____  ____    ____              ____  _       ____  ____   ______  _____       \n" +
                "|      ||  |__|  |  /  _]|    \\ |  |/ ]|    ||    \\  /    |            |    \\| |     /    ||    \\ |      |/ ___/       \n" +
                "|      ||  |  |  | /  [_ |  D  )|  ' /  |  | |  _  ||   __|            |  o  ) |    |  o  ||  _  ||      (   \\_        \n" +
                "|_|  |_||  |  |  ||    _]|    / |    \\  |  | |  |  ||  |  |            |   _/| |___ |     ||  |  ||_|  |_|\\__  |       \n" +
                "  |  |  |  `  '  ||   [_ |    \\ |     \\ |  | |  |  ||  |_ |            |  |  |     ||  _  ||  |  |  |  |  /  \\ |       \n" +
                "  |  |   \\      / |     ||  .  \\|  .  | |  | |  |  ||     |            |  |  |     ||  |  ||  |  |  |  |  \\    |       \n" +
                "  |__|    \\_/\\_/  |_____||__|\\_||__|\\_||____||__|__||___,_|            |__|  |_____||__|__||__|__|  |__|   \\___|      \n");

                Bukkit.getLogger().info("Thank you for using the twerking plants! Feel free to donate here https://www.buymeacoffee.com/Michaelrbparker");

        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new PlayerHandler(this), this);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("SHUTDOWN");
    }
}
