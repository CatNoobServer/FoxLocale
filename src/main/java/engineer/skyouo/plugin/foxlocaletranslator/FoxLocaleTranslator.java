/**
 *     FoxLocaleTranslator
 *     Copyright (C) 2026 TheArcFox
 * <p>
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * <p>
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 * <p>
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package engineer.skyouo.plugin.foxlocaletranslator;

import engineer.skyouo.plugin.foxlocaletranslator.api.LocaleApi;
import engineer.skyouo.plugin.foxlocaletranslator.configuration.GlobalConfig;
import engineer.skyouo.plugin.foxlocaletranslator.external.LocalePlaceholder;
import engineer.skyouo.plugin.foxlocaletranslator.lang.AssetsFetcher;
import engineer.skyouo.plugin.foxlocaletranslator.lang.LocaleMapper;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public final class FoxLocaleTranslator extends JavaPlugin {
    private static FoxLocaleTranslator instance;
    private AssetsFetcher assetsFetcher;
    public GlobalConfig config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        assetsFetcher = new AssetsFetcher();

        String basePath = getDataFolder().getPath();
        Bukkit.getAsyncScheduler().runNow(this, (ignored) -> {
            if (!assetsFetcher.checkUpdates(basePath)) {
                try {
                    if (assetsFetcher.downloadAssets(basePath)) {
                        assetsFetcher.saveVersion(basePath);
                        getLogger().info("Success to download the lang data.");
                    }
                } catch (IOException e) {
                    getLogger().log(Level.SEVERE, "Failed to download data from remote, See exception:", e);
                }
            } else {
                getLogger().info("The language file is up-to-date.");
            }

            try {
                config = new GlobalConfig(this);
                LocaleApi.registerInstance(new LocaleMapper(new File(getDataFolder(), "data.json")));
            } catch (IOException e) {
                getLogger().log(Level.SEVERE, "Failed to load config, See exception:", e);
            }

            if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                new LocalePlaceholder().register();
            }
        });
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static FoxLocaleTranslator getInstance() {
        return instance;
    }
}
