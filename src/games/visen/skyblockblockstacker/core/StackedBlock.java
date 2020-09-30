package games.visen.skyblockblockstacker.core;

import games.visen.skyblockblockstacker.data.Storage;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class StackedBlock {
    public Block block;

    public Material block_material;

    public int count;

    public StackedBlock(Block b) {
        if(Storage.allowedBlocks.contains(b.getType())) {
            this.block = b;
            this.count = 0;
        }
    }

    public void addBlock() {
        count++;
    }
}
