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

package com.github.namiuni.paperpluginexample.message;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

/**
 * Messageクラスは、プラグイン内で使用されるメッセージを管理する。
 * このクラスはインスタンス化できないようにプライベートコンストラクタを持っている。
 */
@DefaultQualifier(NonNull.class)
public final class Message {

    private Message() {
    }

    /**
     * コンフィグの再読み込みが成功した際のメッセージを返す。
     *
     * @return コンフィグの再読み込み成功メッセージ
     */
    public static Component configReloaded() {
        ComponentLogger.logger().info(Component.translatable("paperpluginexample.config.reload.success"));
        return Component.translatable("paperpluginexample.config.reload.success");
    }

    /**
     * プレイヤーが参加した際のメッセージを返す。
     *
     * @param playerName 参加したプレイヤーの名前
     * @return 参加メッセージ
     */
    public static Component joinMessage(final Component playerName) {
        return Component.translatable("paperpluginexample.broadcast.join.message", playerName);
    }

    /**
     * プレイヤーが退出した際のメッセージを返す。
     *
     * @param playerName 退出したプレイヤーの名前
     * @return 退出メッセージ
     */
    public static Component quitMessage(final Component playerName) {
        return Component.translatable("paperpluginexample.broadcast.quit.message", playerName);
    }

    /**
     * 怒ったゾンビの数のメッセージを返す。
     *
     * @param count
     * @return
     */
    public static Component angryZombifiedPiglin(final int count) {
        return Component.translatable("paperpluginexample.angry-zombified_piglin", Component.text(count));
    }

    /**
     * 上空にテレポートされたメッセージを返す。
     */
    public static Component aboveTeleport(final int height) {
        return Component.translatable("paperpluginexample.above-teleport", Component.text(height));
    }
}
