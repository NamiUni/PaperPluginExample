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

import com.github.namiuni.paperpluginexample.config.ConfigManager;
import com.github.namiuni.paperpluginexample.message.Message;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

import java.util.List;

@DefaultQualifier(NonNull.class)
@SuppressWarnings("UnstableApiUsage")
public final class RootCommand implements BaseCommand {

    private final ConfigManager configManager;

    public RootCommand(final ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public LiteralCommandNode<CommandSourceStack> create() {
        return Commands.literal("paperpluginexample") // /paperpluginexampleコマンドを生成する
                .then(this.reloadCommand()) // thenでつなげることで/paperpluginexampleにサブコマンドを登録できる
                .then(this.infoCommand())
                .build();
    }

    @Override
    public String description() {
        return "ルートコマンド";
    }

    @Override
    public List<String> aliases() {
        return List.of("ppe", "example"); // エイリアスを設定する。 /ppeや/exampleでもコマンドが実行できるようになる
    }

    private LiteralCommandNode<CommandSourceStack> infoCommand() {
        return Commands.literal("info")
                .requires(context -> context.getSender().hasPermission("paperpluginexample.command.info")) //パーミッションチェック
                .executes(context -> {
                    final var sender = context.getSource().getSender();
                    final var message = """
                                    PaperPluginExample_v1.0.0-SNAPSHOT
                                    初心者向けサンプルプラグイン
                                    
                                    Author: Unitarou
                                    """;
                    sender.sendRichMessage(message);
                    return Command.SINGLE_SUCCESS;
                })
                .build();
    }

    private LiteralCommandNode<CommandSourceStack> reloadCommand() {
        return Commands.literal("reload") // reloadコマンド
                .requires(context -> context.getSender().hasPermission("paperpluginexample.command.reload")) //パーミッションチェック
                .executes(context -> {

                    // 設定を再読み込みする
                    this.configManager.reloadConfig();

                    // コマンドの送信者に再読み込みが完了した旨のメッセージを送信する
                    context.getSource().getSender().sendMessage(Message.configReloaded());

                    // 戻り値は毎回これで大丈夫
                    return Command.SINGLE_SUCCESS;
                })
                .build();
    }
}
