package games.visen.skyblockblockstacker.utils;

import games.visen.skyblockblockstacker.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config extends YamlConfiguration {

    private final File configFile;

    public Config(String configName) {
        this(new File(Main.getInstance().getDataFolder(), configName));
    }

    public Config(File file) {
        this.configFile = file;

        initConfig();
    }

    private void initConfig() {
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            Main.getInstance().saveResource(configFile.getName(), false);
        }

        try {
            super.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            this.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        initConfig();
    }

    public File getConfigFile() {
        return configFile;
    }
}