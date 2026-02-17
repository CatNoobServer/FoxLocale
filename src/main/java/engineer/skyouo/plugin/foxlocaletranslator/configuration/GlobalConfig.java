package engineer.skyouo.plugin.foxlocaletranslator.configuration;

import engineer.skyouo.plugin.foxlocaletranslator.FoxLocaleTranslator;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GlobalConfig {
    private YamlConfiguration configuration;
    public GlobalConfig(FoxLocaleTranslator plugin) throws IOException {
        configuration = readFile(plugin, "config.yml");
    }

    public boolean isPreferSystemLanguage() {
        return configuration.getBoolean("prefer-system-lang", true);
    }

    public static YamlConfiguration readFile(JavaPlugin plugin, String file) throws IOException {
        File configFile = new File(plugin.getDataFolder(), file);
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            Path path = Files.createFile(configFile.toPath());
            byte[] data = plugin.getResource(file).readAllBytes();

            Files.write(path, data);
        }

        return YamlConfiguration.loadConfiguration(configFile);
    }
}
