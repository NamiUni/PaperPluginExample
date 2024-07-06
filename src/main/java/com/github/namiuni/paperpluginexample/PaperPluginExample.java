package com.github.namiuni.paperpluginexample;

import com.github.namiuni.paperpluginexample.config.ConfigManager;
import com.github.namiuni.paperpluginexample.listeners.FishingListener;
import com.github.namiuni.paperpluginexample.listeners.PlayerJoinListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

import java.util.Set;

/**
 * PaperPluginExampleBootstrapperクラスは、Paperプラグインのロードや有効化を行う。
 *
 * <p>コマンドはここでも登録が可能だが、ここで登録されたコマンドは/minecraft:executeコマンドでは使えない事に注意が必要
 * イベントをコントロールするリスナークラスの登録もこのクラスで行う。</p>
 */
@DefaultQualifier(NonNull.class)
public final class PaperPluginExample extends JavaPlugin {

    private final ConfigManager configManager;

    /**
     * コンストラクタは、リスナーに使用するConfigManagerを受け取る。
     *
     * @param configManager リスナーに使用するConfigManager
     */
    public PaperPluginExample(final ConfigManager configManager) {
        this.configManager = configManager;
    }

    /**
     * このプラグインがサーバーによって有効化された時に呼び出されるメソッド
     */
    @Override
    public void onEnable() {

        /*
         * Listenerを実装したクラスを登録することで、発生する事象(イベント)をコントロールできる。
         * 例えば、参加メッセージを馬鹿げたものに書き換えたり、釣った魚をクリーパーに変えてプレイヤーをびっくりさせたり...etc
         * 実際に登録されているリスナーはlistenersパッケージにある各リスナークラスを参照。
         */
        final var listeners = this.exampleListeners();
        for (var listener : listeners) {
            this.getServer().getPluginManager().registerEvents(listener, this);
        }

        this.getComponentLogger().info("Server startup process is completed.");
    }

    /**
     * リスナークラスのインスタンスを生成し、リストでまとめる。
     *
     * @return コマンドクラスのリスト
     */
    private Set<Listener> exampleListeners() {
        return Set.of(
                new PlayerJoinListener(this.configManager),
                new FishingListener());
    }
}
