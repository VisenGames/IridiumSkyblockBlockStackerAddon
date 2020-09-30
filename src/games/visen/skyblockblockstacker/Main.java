package games.visen.skyblockblockstacker;

import games.visen.skyblockblockstacker.data.Storage;
import games.visen.skyblockblockstacker.data.YAML;
import games.visen.skyblockblockstacker.utils.Config;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        YAML.config = new Config("config.yml");
        loadConfig();
    }

    public void loadConfig() {
        List<String> neededBlocks = YAML.config.getStringList("blocks");
        for(String i : neededBlocks) {
            Material block = Material.matchMaterial(i);
            Storage.allowedBlocks.add(block);
        }
    }

    public static Main getInstance() { return instance; }
}
