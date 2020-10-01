package games.visen.skyblockblockstacker.core;

import com.avaje.ebean.validation.NotNull;
import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Island;
import com.iridium.iridiumskyblock.IslandManager;
import games.visen.skyblockblockstacker.data.Storage;
import games.visen.skyblockblockstacker.data.YAML;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class StackedBlock {

    public int id;

    public Block block;

    public Location block_location;

    public Material block_material;

    public Island island;

    public int count;

    public int value;

    public boolean exists;



    public StackedBlock(Block b, int id) {
        if(Storage.allowedBlocks.contains(b.getType())) {
            this.block = b;
            this.count = 0;
            this.block_location = b.getLocation();
            this.block_material = b.getType();
            this.id = id;
            this.island = IridiumSkyblock.getIslandManager().getIslandViaLocation(block_location);
            this.exists = true;
            HashMap<Material, Integer> blockValues = Storage.blockValues;
            if(blockValues.get(block_material) != null) {
                this.value = blockValues.get(block_material);
            } else {
                this.value = 0;
            }
        }
    }

    public void addBlock() {
        count++;
        if(this.island != null) {
            this.island.addExtraValue(value);
        }
    }

    public ItemStack removeBlock() {
        ItemStack item = new ItemStack(block_material, count);
        if(this.island != null) {
            this.island.removeExtraValue((count - 1) * value);
            this.island = null;
        }
        this.block = null;
        this.block_material = null;
        this.block_location = null;
        count = -1;
        this.exists = false;
        return item;
    }

    public void save() {
        ConfigurationSection config = YAML.database.getConfigurationSection("block" + this.id);
        if(config == null) {
            YAML.database.createSection("block" + this.id);
            config = YAML.database.getConfigurationSection("block" + this.id);
        }
        if(this.exists) {
            config.set("world", block.getWorld().getName());
            config.set("x", block.getLocation().getX());
            config.set("y", block.getLocation().getY());
            config.set("z", block.getLocation().getZ());
            config.set("id", this.id);
            config.set("count", this.count);
            config.set("exists", 1);
        }
        else {
            config.set("exists", 0);
        }
        YAML.database.save();
    }

    public static StackedBlock getStackedBlock(Block b) {
        for(StackedBlock sb : Storage.blocks) {
            if(sb.exists) {
                if (sb.block.equals(b)) {
                    return sb;
                }
            }
        }
        return null;
    }
}
