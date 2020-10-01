package games.visen.skyblockblockstacker.events;

import com.iridium.iridiumskyblock.Island;
import com.iridium.iridiumskyblock.User;
import games.visen.skyblockblockstacker.Main;
import games.visen.skyblockblockstacker.core.StackedBlock;
import games.visen.skyblockblockstacker.data.Storage;
import games.visen.skyblockblockstacker.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractEventL implements Listener {

    private Main plugin;

    public PlayerInteractEventL(Main plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            Block block = event.getClickedBlock();
            if(Storage.allowedBlocks.contains(block.getType())) {
                event.setCancelled(true);
                StackedBlock stackedBlock = StackedBlock.getStackedBlock(block);
                if (stackedBlock == null) {
                    stackedBlock = new StackedBlock(block, Storage.currentId + 1);
                    stackedBlock.count = 1;
                    Storage.currentId = stackedBlock.id;
                    Storage.blocks.add(stackedBlock);
                    //stackedBlock.save();
                }
                if (event.getMaterial() == block.getType()) {
                    stackedBlock.addBlock();
                    ItemStack item = player.getInventory().getItemInHand();
                    if(item.getAmount() > 1) {
                        item.setAmount(item.getAmount() - 1);
                    } else {
                        player.getInventory().remove(item);
                    }
                    Utils.message(player, "&eAdded block to the stack. Current size: " + stackedBlock.count);
                } else {
                    assert stackedBlock.island != null;
                    Utils.message(player, "&eThis is a stack of " + stackedBlock.count + " blocks!");
                }
            }
        }

    }
}
