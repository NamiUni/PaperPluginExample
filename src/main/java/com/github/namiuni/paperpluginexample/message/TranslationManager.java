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

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;
import net.kyori.adventure.util.UTF8ResourceBundleControl;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.StringJoiner;

/**
 * TranslationManagerクラスは、PaperPluginExampleプラグインの翻訳管理を担当する。
 *
 * <p>翻訳ファイルを読み込んでサーバーに登録することで、どこからでも翻訳されたメッセージを呼び出せるようになる。</p>
 */
@DefaultQualifier(NonNull.class)
public class TranslationManager {
    /** PaperPluginExampleのjarに内包されている言語のリスト*/
    public static final List<Locale> INCLUDE_LOCALES = List.of(Locale.US, Locale.JAPAN);

    private final ComponentLogger logger;

    /**
     * コンストラクタは、ログ出力に使用するComponentLoggerを受け取る。
     *
     * @param logger ログ出力に使用するComponentLogger
     */
    public TranslationManager(final ComponentLogger logger) {
        this.logger = logger;
    }

    /**
     * このメソッドは新しいレジストリを作成して内包されている全ての翻訳をサーバーに登録する。
     */
    public void registerTranslations() {

        // 新しい翻訳レジストリを作成
        final var registry = TranslationRegistry.create(Key.key("paperpluginexample", "messages"));

        // デフォルト言語としてINCLUDE_LOCALESの最初のロケール(Locale.US)を読み込む。
        registry.defaultLocale(INCLUDE_LOCALES.getFirst());

        for (final Locale locale : INCLUDE_LOCALES) {

            // resources/localeからリソースバンドルを取得する
            final var bundle = ResourceBundle.getBundle("locale/messages", locale, UTF8ResourceBundleControl.get());

            // 取得したバンドルをレジストリに登録する
            registry.registerAll(locale, bundle, false);
        }

        /*
         * このプラグインだけでなく、サーバー全体で翻訳を認識できるようにグローバルソースに登録する。
         * Component.translatable(String key)メソッドでサーバーに登録されたバンドルを取得する。
         * それをプレイヤーなどに送信すると、設定言語をチェックして該当するバンドルがあれば
         * キーに紐づいたメッセージをバンドルから取得し、プレイヤーに送信する。
         * バンドルに存在しなかった場合は、レジストリに登録されているデフォルトの言語を参照する。
         */
        GlobalTranslator.translator().addSource(registry);

        // 登録されたロケールをログに出力する
        final var formated = new StringJoiner(", ");
        for (final Locale locale : INCLUDE_LOCALES) {
            formated.add(locale.toString());
            this.logger.info("Loaded {} locales: [{}]", INCLUDE_LOCALES.size(), formated);
        }
    }
}
