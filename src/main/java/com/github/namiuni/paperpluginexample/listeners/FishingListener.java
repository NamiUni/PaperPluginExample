package com.github.namiuni.paperpluginexample.listeners;

import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.framework.qual.DefaultQualifier;

@DefaultQualifier(NonNull.class)
public final class FishingListener implements Listener {

    /*
     * このメソッドは正常に動かない。
     * なぜなのか自分で考えて修正してみよう。
     */
    @EventHandler
    public void onFish(final PlayerFishEvent event) {

        // 魚釣り関連のイベントがPlayerFishEvent１つにまとめられている。謎
        // 魚を釣った時だけ処理を実行したいので、State.CAUGHT_FISHじゃなければリターンして終了
        if (event.getState() != PlayerFishEvent.State.CAUGHT_FISH) {
            return;
        }

        // 釣った魚はnullになる事はないが、PlayerFishEventの仕様上戻り値がNull許容になっている。
        // また、釣った魚はドロップアイテムだが、エンティティでもある。
        // 本当は直接event.setCaughtとしたいが出来ないためちょっと工夫する
        final @Nullable Entity caught = event.getCaught();
        if (caught != null) {

            // 釣った魚の座標と速度を取得する
            final var caughtLoc = caught.getLocation();
            final var velocity = caught.getVelocity();

            // クリーパーを召喚する。3番目の引数はConsumer型なのでラムダ式で処理を記述できる。
            // ここに処理を記述する事でスポーン前にクリーパーに対して処理を実行できる。
            // 今回はスポーン前に速度をセットする。
            caught.getWorld().spawn(caughtLoc, Creeper.class, before -> {
                before.setVelocity(velocity);
            });

            // 釣った魚をきちんと削除する。
            caught.remove();
        }
    }
}
