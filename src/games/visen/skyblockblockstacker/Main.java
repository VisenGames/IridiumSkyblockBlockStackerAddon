package games.visen.skyblockblockstacker;

import games.visen.skyblockblockstacker.core.StackedBlock;
import games.visen.skyblockblockstacker.data.Storage;
import games.visen.skyblockblockstacker.data.YAML;
import games.visen.skyblockblockstacker.events.BlockBreakEventL;
import games.visen.skyblockblockstacker.events.PlayerInteractEventL;
import games.visen.skyblockblockstacker.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        YAML.config = new Config("config.yml");
        YAML.database = new Config("database.yml");
        new PlayerInteractEventL(this);
        new BlockBreakEventL(this);
        Bukkit.getScheduler().runTaskLater(this, this::loadConfig, 80);
    }

    public void loadConfig() {
        List<String> neededBlocks = YAML.config.getStringList("blocks");
        for(String i : neededBlocks) {
            Material block = Material.matchMaterial(i);
            Storage.allowedBlocks.add(block);
        }
        for(Material m : Storage.allowedBlocks) {
            Storage.blockValues.put(m, YAML.config.getInt(m.name().toLowerCase()));
        }
        System.out.println(Storage.blockValues.size() + Storage.blockValues.get(Storage.allowedBlocks.getFirst()));
        int block_num = YAML.database.getInt("stackedBlockNum");
        Storage.currentId = block_num - 1;
        for(int i = 0; i < block_num; i++) {
            ConfigurationSection block = YAML.database.getConfigurationSection("block" + i);
            if(block.getInt("exists") == 1) {
                if(Bukkit.getWorld(block.getString("world")) == null) {
                    new WorldCreator(block.getString("world")).createWorld();
                }
                Location location = new Location(Bukkit.getWorld(block.getString("world")), block.getInt("x"), block.getInt("y"), block.getInt("z"));
                System.out.println(Bukkit.getWorld(block.getString("world")) == null);
                System.out.println(block.getString("world"));
                Block b = Bukkit.getWorld(block.getString("world")).getBlockAt(location);
                int id = block.getInt("id");
                int count = block.getInt("count");
                StackedBlock sb = new StackedBlock(b, id);
                sb.count = count;
                Storage.blocks.add(sb);
            }
        }
    }

    @Override
    public void onDisable() {
        System.out.println("Saving Database!");
        YAML.database.set("stackedBlockNum", Storage.blocks.getLast().id + 1);
        Storage.blocks.forEach(StackedBlock::save);
    }

    public static Main getInstance() { return instance; }
}
