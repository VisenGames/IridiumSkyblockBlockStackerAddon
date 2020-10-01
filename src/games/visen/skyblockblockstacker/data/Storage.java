package games.visen.skyblockblockstacker.data;

import games.visen.skyblockblockstacker.core.StackedBlock;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.LinkedList;

public class Storage {
    public static final LinkedList<Material> allowedBlocks = new LinkedList<>();
    public static final LinkedList<StackedBlock> blocks = new LinkedList<>();
    public static final HashMap<Material, Integer> blockValues = new HashMap<>();
    public static int currentId;
}
