/*
 * PaperPluginExample
 *
 * Copyright (c) 2024. Namiu (Unitarou)
 *                    Contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.github.namiuni.paperpluginexample.config;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@DefaultQualifier(NonNull.class)
@ConfigSerializable
public final class PrimaryConfig {

    @Comment("参加メッセージを書き換える")
    private boolean rewriteJoinMessage = true;

    @Comment("aboveteleportコマンドで対象をテレポートさせる距離(ブロック)")
    private int teleportHeight = 1000;

    public boolean rewriteJoinMessage() {
        return this.rewriteJoinMessage;
    }

    public int teleportHeight() {
        return this.teleportHeight;
    }
}
