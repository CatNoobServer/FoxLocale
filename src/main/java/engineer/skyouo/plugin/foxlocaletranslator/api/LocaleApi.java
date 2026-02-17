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

package engineer.skyouo.plugin.foxlocaletranslator.api;


import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.Locale;

public abstract class LocaleApi {
    private static LocaleApi instance;

    public abstract @Nullable String matchName(Object object);
    public abstract @Nullable String matchName(Object object, Player player);
    public abstract @Nullable String matchName(Object object, Locale locale);

    public abstract @Nullable String getMaterialName(Material material);
    public abstract @Nullable String getMaterialName(Material material, Player player);
    public abstract @Nullable String getMaterialName(Material material, Locale locale);

    public abstract @Nullable String getBlockName(Block block);
    public abstract @Nullable String getBlockName(Block block, Player player);
    public abstract @Nullable String getBlockName(Block block, Locale locale);
    public abstract @Nullable String getBlockName(Material block);
    public abstract @Nullable String getBlockName(Material block, Player player);
    public abstract @Nullable String getBlockName(Material block, Locale locale);

    public abstract @Nullable String getEntityName(Entity entity);
    public abstract @Nullable String getEntityName(Entity entity, Player player);
    public abstract @Nullable String getEntityName(Entity entity, Locale locale);
    public abstract @Nullable String getEntityName(EntityType entity);
    public abstract @Nullable String getEntityName(EntityType entity, Player player);
    public abstract @Nullable String getEntityName(EntityType entity, Locale locale);

    public static void registerInstance(LocaleApi api) {
        if (instance != null) {
            throw new UnsupportedOperationException("Instance has already been registered!");
        }

        instance = api;
    }

    public static LocaleApi getInstance() {
        return instance;
    }
}
