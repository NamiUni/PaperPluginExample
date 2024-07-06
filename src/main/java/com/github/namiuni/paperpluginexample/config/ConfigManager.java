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

import com.github.namiuni.paperpluginexample.util.FileUtil;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.framework.qual.DefaultQualifier;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * ConfigManagerクラスは、プラグインの設定ファイルの管理を行う。
 * 設定の読み込み、再読み込み、保存を行うためのメソッドを提供する。
 */
@DefaultQualifier(NonNull.class)
public final class ConfigManager {

    private final Path dataDirectory;
    private final ComponentLogger logger;

    // 設定のインスタンス。必要に応じて再読み込みされる
    private @MonotonicNonNull PrimaryConfig primaryConfig = null;

    /**
     * コンストラクタは、データディレクトリのパスとログ出力に使用するComponentLoggerを受け取る。
     *
     * @param dataDirectory 設定ファイルが保存されるディレクトリのパス
     * @param logger ログ出力に使用するComponentLogger
     */
    public ConfigManager(final Path dataDirectory, final ComponentLogger logger) {
        this.dataDirectory = dataDirectory;
        this.logger = logger;
    }

    /**
     * 設定を取得する。設定が読み込まれていない場合は再読み込みを行う。
     *
     * @return 現在の設定
     */
    public PrimaryConfig config() {
        // 設定が読み込まれていない場合は読み込みを行い、読み込まれている場合はそのまま返す

        if (this.primaryConfig != null) {
            return this.primaryConfig;
        } else {
            return this.reloadConfig();
        }
    }

    /**
     * 設定を再読み込みする。設定の再読み込みが失敗した場合は例外をスローする。
     *
     * @return 再読み込みされた設定
     */
    public PrimaryConfig reloadConfig() {
        // 設定を読み込み、インスタンス変数に保存する。nullだった場合は例外をスローする。
        this.primaryConfig = Objects.requireNonNull(this.load(), "Failed to reload config. See above for further details.");

        // 設定を返す。
        return this.primaryConfig;
    }

    /**
     * 設定を読み込みする。設定ファイルが存在しない場合は新しいファイルを作成する。
     *
     * @return 読み込まれた設定、読み込み失敗時はnull
     */
    public @Nullable PrimaryConfig load() {
        try {
            // ディレクトリが存在しない場合は作成
            FileUtil.createDirectoriesIfNotExists(this.dataDirectory);
        } catch (final IOException exception) {
            // ディレクトリの作成に失敗した場合はエラーメッセージをログに出力
            this.logger.error("Failed to create directories: {}", this.dataDirectory, exception);
            return null;
        }

        // 設定ファイルのパスを取得
        final var configFile = this.dataDirectory.resolve("config.conf");

        // HoconConfigurationLoaderを使用して設定ファイルを読み込むためのローダーを作成
        final var loader = HoconConfigurationLoader.builder()
                .defaultOptions(options -> options.shouldCopyDefaults(true))
                .path(configFile)
                .build();

        try {
            // 設定ファイルを読み込み、PrimaryConfigのインスタンスにマッピング
            final var node = loader.load();
            final @Nullable PrimaryConfig config = node.get(PrimaryConfig.class);

            // 設定ファイルが存在しない場合はPrimaryConfigに設定されたデフォルト値をconfig.confに書き込む
            if (!Files.exists(configFile)) {
                node.set(PrimaryConfig.class, config);
                loader.save(node);
            }

            return config;
        } catch (final ConfigurateException exception) {
            // 設定の読み込みに失敗した場合はエラーメッセージをログに出力
            this.logger.error("Failed to load config.", exception);
            return null;
        }
    }
}
