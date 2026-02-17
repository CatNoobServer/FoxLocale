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

package engineer.skyouo.plugin.foxlocaletranslator.external;

import engineer.skyouo.plugin.foxlocaletranslator.FoxLocaleTranslator;
import engineer.skyouo.plugin.foxlocaletranslator.api.LocaleApi;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fox;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Locale;

public class LocalePlaceholder extends PlaceholderExpansion {
    private static LocaleApi api;
    public LocalePlaceholder() {
        if (api == null)
            api = LocaleApi.getInstance();
    }
    @Override
    public @NotNull String getIdentifier() {
        return "locale";
    }

    @Override
    public @NotNull String getAuthor() {
        return "TheArcFox";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        Locale locale = Locale.getDefault();
        if (player.isOnline() && player.getPlayer() != null && !FoxLocaleTranslator.getInstance().config.isPreferSystemLanguage())
            locale = player.getPlayer().locale();

        if (params.startsWith("material_")) {
            String[] p = params.split("_");

            if (p.length < 2) return null;

            String result = String.join("_", Arrays.stream(p).skip(1).toList()).toUpperCase();
            return api.getMaterialName(Material.getMaterial(result), locale);
        } else if (params.startsWith("material_legacy_")) {
            String[] p = params.split("_");

            if (p.length < 3) return null;

            String result = String.join("_", Arrays.stream(p).skip(2).toList()).toUpperCase();
            return api.getMaterialName(Material.getMaterial(result), locale);
        } else if (params.startsWith("block_")) {
            String[] p = params.split("_");

            if (p.length < 2) return null;

            String result = String.join("_", Arrays.stream(p).skip(1).toList()).toUpperCase();
            return api.getBlockName(Material.getMaterial(result), locale);
        } else if (params.startsWith("entity_")) {
            String[] p = params.split("_");

            if (p.length < 2) return null;

            String result = String.join("_", Arrays.stream(p).skip(1).toList()).toUpperCase();
            return api.getEntityName(EntityType.fromName(result), locale);
        }

        return api.matchName(params, locale);
    }
}
