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

package com.github.namiuni.paperpluginexample.commands;

import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.framework.qual.DefaultQualifier;

import java.util.List;

/**
 * {@code AbstractCommand}クラスはコマンドを定義するための基本クラス。
 * {@code Commands}オブジェクトを使用して、コマンドを登録するための基本的なメカニズムを提供する。
 *
 * <p>すべてのフィールド、メソッドの戻り値、およびパラメータは
 * {@code @DefaultQualifier(NonNull.class)}アノテーションによりデフォルトで{@code @NonNull}と見なされる。</p>
 *
 * <p>このクラスは不安定なAPIの使用に関する警告を
 * {@code @SuppressWarnings("UnstableApiUsage")}アノテーションで抑制する。</p>
 */
@DefaultQualifier(NonNull.class)
@SuppressWarnings("UnstableApiUsage")
public interface BaseCommand {

    /**
     * サブクラスが実装する必要のあるコマンド定義を返す。
     *
     * @return コマンド定義を{@code LiteralCommandNode<CommandSourceStack>}として返す
     */
    LiteralCommandNode<CommandSourceStack> create();

    /**
     * コマンドの説明を返す。
     * デフォルトの実装では{@code null}を返す。
     * サブクラスはこのメソッドをオーバーライドして具体的な説明を提供できる。
     *
     * @return コマンドの説明、指定されていない場合は{@code null}
     */
    default @Nullable String description() {
        return null;
    }

    /**
     * コマンドのエイリアスのリストを返す。
     * デフォルトの実装では空のリストを返す。
     * サブクラスはこのメソッドをオーバーライドして具体的なエイリアスを提供できる。
     *
     * @return コマンドのエイリアスのリスト
     */
    default List<String> aliases() {
        return List.of();
    }
}
