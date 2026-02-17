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

package engineer.skyouo.plugin.foxlocaletranslator.lang;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.net.ssl.HttpsURLConnection;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.GZIPInputStream;

public class AssetsFetcher {
    private String DOWNLOAD_ENDPOINT =
            "https://github.com/misode/mcmeta/raw/refs/tags/%s-summary/assets/lang/data.json.gz";

    public void saveVersion(String targetDir) throws IOException {
        File descriptor = new File(targetDir, "updator.yml");
        YamlConfiguration config = new YamlConfiguration();

        config.set("version", Bukkit.getServer().getMinecraftVersion());
        config.save(descriptor);
    }

    public boolean checkUpdates(String targetDir) {
        File descriptor = new File(targetDir, "updator.yml");

        if (!descriptor.exists()) {
            return false;
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(descriptor);

        String savedVersion = config.getString("version");
        String currentVersion = Bukkit.getServer().getMinecraftVersion();

        if (!currentVersion.equals(savedVersion)) {
            return false;
        }

        File assetsFile = new File(targetDir, "data.json");
        return assetsFile.exists();
    }

    public boolean downloadAssets(String targetDir) throws IOException {
        String minecraftVersion = Bukkit.getServer().getMinecraftVersion();

        URL url;
        try {
            url = new URI(DOWNLOAD_ENDPOINT.formatted(minecraftVersion)).toURL();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        connection.setConnectTimeout(10_000);
        connection.setReadTimeout(10_000);

        Path outputFile = Paths.get(targetDir, "data.json");

        try (
                InputStream rawInput = connection.getInputStream();
                GZIPInputStream gzipInput = new GZIPInputStream(rawInput);
                OutputStream fileOutput = Files.newOutputStream(outputFile)
        ) {
            byte[] buffer = new byte[8192];
            int read;
            while ((read = gzipInput.read(buffer)) != -1) {
                fileOutput.write(buffer, 0, read);
            }
            return true;
        } finally {
            connection.disconnect();
        }
    }
}
