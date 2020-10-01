package games.visen.skyblockblockstacker.events;

import games.visen.skyblockblockstacker.Main;
import games.visen.skyblockblockstacker.core.StackedBlock;
import games.visen.skyblockblockstacker.data.Storage;
import games.visen.skyblockblockstacker.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreakEventL implements Listener {

    private Main plugin;

    public BlockBreakEventL(Main plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if(!(player == null)){
            StackedBlock stackedBlock = StackedBlock.getStackedBlock(block);
            if(Storage.allowedBlocks.contains(block.getType()) && !(stackedBlock == null)) {
                event.setCancelled(true);
                Utils.message(player, "You have destroyed a stacked block and recived " + stackedBlock.count + " items!");
                ItemStack items = stackedBlock.removeBlock();
                player.getInventory().addItem(items);
                block.setType(Material.AIR);
            }
        }

    }
}
