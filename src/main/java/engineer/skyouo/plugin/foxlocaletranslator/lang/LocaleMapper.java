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

import engineer.skyouo.plugin.foxlocaletranslator.FoxLocaleTranslator;
import engineer.skyouo.plugin.foxlocaletranslator.api.LocaleApi;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.json.JSONObject;
import org.jspecify.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Locale;

public class LocaleMapper extends LocaleApi {
    private JSONObject object;
    public LocaleMapper(File file) throws IOException {
        String result = Files.readString(file.toPath());
        object = new JSONObject(result);
    }

    @Override
    public @Nullable String matchName(Object object) {
        return matchName(object, Locale.getDefault());
    }

    @Override
    public @Nullable String matchName(Object object, Player player) {
        return matchName(object, FoxLocaleTranslator.getInstance().config.isPreferSystemLanguage() ?
                Locale.getDefault() : player.locale());
    }

    @Override
    public @Nullable String matchName(Object object, Locale locale) {
        if (object instanceof Material material) {
            return getMaterialName(material, locale);
        } else if (object instanceof Block block) {
            return getBlockName(block, locale);
        } else if (object instanceof Entity entity) {
            return getEntityName(entity, locale);
        } else if (object instanceof EntityType entityType) {
            return getEntityName(entityType, locale);
        } else if (object instanceof String name) {
            Material mat;
            if ((mat = Material.getMaterial(name)) != null) return mat.isBlock() ? getBlockName(mat, locale) :
                    getMaterialName(mat, locale);

            EntityType ent;
            if ((ent = EntityType.fromName(name)) != null) return getEntityName(ent, locale);
        }

        return null;
    }

    @Override
    public @Nullable String getMaterialName(Material material) {
        return getMaterialName(material, Locale.getDefault());
    }

    @Override
    public @Nullable String getMaterialName(Material material, Player player) {
        return getMaterialName(material, FoxLocaleTranslator.getInstance().config.isPreferSystemLanguage() ?
                Locale.getDefault() : player.locale());
    }

    @Override
    public @Nullable String getMaterialName(Material material, Locale locale) {
        JSONObject localeDict = object.getJSONObject(locale.toString().toLowerCase());

        if (localeDict == null) return null;

        return localeDict.getString(material.getItemTranslationKey());
    }

    @Override
    public @Nullable String getBlockName(Block block) {
        return getBlockName(block, Locale.getDefault());
    }

    @Override
    public @Nullable String getBlockName(Block block, Player player) {
        return getBlockName(block, FoxLocaleTranslator.getInstance().config.isPreferSystemLanguage() ?
                Locale.getDefault() : player.locale());
    }

    @Override
    public @Nullable String getBlockName(Block block, Locale locale) {
        JSONObject localeDict = object.getJSONObject(locale.toString().toLowerCase());

        if (localeDict == null) return null;

        return localeDict.getString(block.translationKey());
    }

    @Override
    public @Nullable String getBlockName(Material block) {
        return getBlockName(block, Locale.getDefault());
    }

    @Override
    public @Nullable String getBlockName(Material block, Player player) {
        return getBlockName(block, FoxLocaleTranslator.getInstance().config.isPreferSystemLanguage() ?
                Locale.getDefault() : player.locale());
    }

    @Override
    public @Nullable String getBlockName(Material block, Locale locale) {
        JSONObject localeDict = object.getJSONObject(locale.toString().toLowerCase());

        if (localeDict == null) return null;

        return localeDict.getString(block.getBlockTranslationKey());
    }

    @Override
    public @Nullable String getEntityName(Entity entity) {
        return getEntityName(entity.getType(), Locale.getDefault());
    }

    @Override
    public @Nullable String getEntityName(Entity entity, Player player) {
        return getEntityName(entity.getType(), FoxLocaleTranslator.getInstance().config.isPreferSystemLanguage() ?
                Locale.getDefault() : player.locale());
    }

    @Override
    public @Nullable String getEntityName(Entity entity, Locale locale) {
        return getEntityName(entity.getType(), locale);
    }

    @Override
    public @Nullable String getEntityName(EntityType entity) {
        return getEntityName(entity, Locale.getDefault());
    }

    @Override
    public @Nullable String getEntityName(EntityType entity, Player player) {
        return getEntityName(entity, FoxLocaleTranslator.getInstance().config.isPreferSystemLanguage() ?
                Locale.getDefault() : player.locale());
    }

    @Override
    public @Nullable String getEntityName(EntityType entity, Locale locale) {
        JSONObject localeDict = object.getJSONObject(locale.toString().toLowerCase());

        if (localeDict == null) return null;

        return localeDict.getString(entity.translationKey());
    }
}
