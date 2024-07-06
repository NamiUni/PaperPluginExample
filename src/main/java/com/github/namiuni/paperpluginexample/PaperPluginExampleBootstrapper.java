package com.github.namiuni.paperpluginexample;

import com.github.namiuni.paperpluginexample.commands.BaseCommand;
import com.github.namiuni.paperpluginexample.commands.RootCommand;
import com.github.namiuni.paperpluginexample.commands.AngryZombifiedPiglinCommand;
import com.github.namiuni.paperpluginexample.commands.AboveTeleportCommand;
import com.github.namiuni.paperpluginexample.config.ConfigManager;
import com.github.namiuni.paperpluginexample.message.TranslationManager;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.bootstrap.PluginProviderContext;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

import java.util.Set;

/**
 * PaperPluginExampleBootstrapperクラスは、プラグインのブートストラップ処理を行う。
 *
 * <p>ブートストラッパーを使用することで、サーバーが起動する前にプラグイン資源を読み込みできる。
 * コンフィグの読み込みや翻訳可能なメッセージの登録、データベースの初期化処理などは
 * このクラスのbootstrapメソッドで実行することが推奨される。
 *
 * <p>現時点ではあまりAPIは提供されていないが、コマンドの登録やカスタムエンチャントの追加も可能。</p>
 */
@DefaultQualifier(NonNull.class)
@SuppressWarnings("UnstableApiUsage")
public final class PaperPluginExampleBootstrapper implements PluginBootstrap {

    private @MonotonicNonNull ConfigManager configManager;

    /**
     * プラグインのブートストラップ処理を行う。
     * データパック読み込みなどと同じタイミングで行われる為、マインクラフトレジストリの変更も可能。
     *
     * @param bootstrapContext ブートストラップコンテキスト
     */
    @Override
    public void bootstrap(final BootstrapContext bootstrapContext) {

        // コンフィグマネージャーを初期化する。
        this.configManager = new ConfigManager(bootstrapContext.getDataDirectory(), bootstrapContext.getLogger());

        // 翻訳マネージャーを初期化し、メッセージをサーバーに登録する。
        final var translationManager = new TranslationManager(bootstrapContext.getLogger());
        translationManager.registerTranslations();

        // コマンドの設定を生成する。
        final var commandConfig = LifecycleEvents.COMMANDS.newHandler(event -> {
            final var commandRegistrar = event.registrar();
            final var exampleCommands = this.exampleCommands();

            /* コマンドを登録することで、オリジナルのコマンドが使えるようになる
             * 誰かを上空1km先にテレポートさせたり、全てのゾンビピグリンを敵対させたり...etc
             * 実際に登録されているコマンドはlistenersパッケージにある各リスナークラスを参照。
             */
            for (var command : exampleCommands) {
                commandRegistrar.register(command.create(), command.description(), command.aliases());
            }
        });

        // ライフサイクルマネージャーにコマンドの設定を登録する。
        bootstrapContext.getLifecycleManager().registerEventHandler(commandConfig);

        bootstrapContext.getLogger().info("Bootstrap process is completed.");
    }

    /**
     * プラグインのインスタンスを生成する。
     *
     * @param context プラグインプロバイダコンテキスト
     * @return 生成されたプラグインのインスタンス
     */
    @Override
    public JavaPlugin createPlugin(final PluginProviderContext context) {
        return new PaperPluginExample(this.configManager);
    }

    /**
     * コマンドクラスのインスタンスを生成し、リストでまとめる。
     *
     * @return コマンドクラスのリスト
     */
    private Set<BaseCommand> exampleCommands() {
        return Set.of(
                new RootCommand(this.configManager),
                new AngryZombifiedPiglinCommand(),
                new AboveTeleportCommand(this.configManager)
        );
    }
}
