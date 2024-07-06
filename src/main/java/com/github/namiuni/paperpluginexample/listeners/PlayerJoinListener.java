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
