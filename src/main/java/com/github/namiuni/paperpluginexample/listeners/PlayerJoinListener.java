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

package com.github.namiuni.paperpluginexample.listeners;

import com.github.namiuni.paperpluginexample.config.ConfigManager;
import com.github.namiuni.paperpluginexample.message.Message;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

@DefaultQualifier(NonNull.class)
public final class PlayerJoinListener implements Listener {

    private final ConfigManager configManager;

    public PlayerJoinListener(final ConfigManager configManager) {
        this.configManager = configManager;
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {

        // config.confファイルでrewrite-join-messageがtrueに設定されている場合メッセージを書き換える
        if (this.configManager.config().rewriteJoinMessage()) {
            final var joinPlayer = event.getPlayer();

            /* 参加メッセージを直接書き換えるとコンソールにはキーが表示される。
             * これは、サーバーがバニラのレジストリを参照しているためなので、
             * 参加メッセージを空にして、サーバー全体にメッセージを送信する事で解決する。
             */
            event.joinMessage(Component.empty());
            final var message = Message.joinMessage(joinPlayer.displayName());
            Bukkit.getServer().sendMessage(message);
        }
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        // onJoinと同じ
        if (this.configManager.config().rewriteJoinMessage()) {
            final var quitPlayer = event.getPlayer();

            // onJoinと同じ
            event.quitMessage(Component.empty());
            final var message = Message.quitMessage(quitPlayer.displayName());
            Bukkit.getServer().sendMessage(message);
        }
    }
}
