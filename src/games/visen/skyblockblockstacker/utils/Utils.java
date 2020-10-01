package games.visen.skyblockblockstacker.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Utils {

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static void message(CommandSender sender, String message) {
        sender.sendMessage(color(message));
    }

}