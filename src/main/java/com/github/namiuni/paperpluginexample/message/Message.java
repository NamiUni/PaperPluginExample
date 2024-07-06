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
