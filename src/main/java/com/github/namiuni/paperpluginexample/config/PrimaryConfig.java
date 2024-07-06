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
