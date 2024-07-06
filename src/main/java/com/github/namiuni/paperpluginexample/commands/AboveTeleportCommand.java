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
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.EntitySelectorArgumentResolver;
import org.bukkit.entity.Entity;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.framework.qual.DefaultQualifier;

import java.util.List;

@SuppressWarnings("UnstableApiUsage")
@DefaultQualifier(NonNull.class)
public final class AboveTeleportCommand implements BaseCommand {

    final ConfigManager configManager;

    public AboveTeleportCommand(final ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public LiteralCommandNode<CommandSourceStack> create() {
        return Commands.literal("aboveteleport")
                .requires(context -> context.getSender().hasPermission("paperpluginexample.command.aboveteleport"))
                .then(Commands.argument("victims", ArgumentTypes.entities())
                        .executes(context -> {

                            // 被害者たちのインスタンスを取得する
                            final List<Entity> victims = context.getArgument("victims", EntitySelectorArgumentResolver.class)
                                    .resolve(context.getSource());

                            // 被害者たち全てに対して実行する
                            for (final var victim : victims) {

                                // 被害者が今いる座標を取得する
                                final var victimLoc = victim.getLocation();

                                // テレポートする座標をセットする
                                final var targetLoc = victim.getLocation().set(victimLoc.x(), victimLoc.y() + this.configManager.config().teleportHeight(), victimLoc.z());

                                // 被害者をテレポートさせる
                                victim.teleportAsync(targetLoc);

                                // 被害者にメッセージを送信する
                                victim.sendMessage(Message.aboveTeleport(targetLoc.blockY()));
                            }

                            return Command.SINGLE_SUCCESS;
                        }))
                .build();
    }

    @Override
    public @Nullable String description() {
        return "指定したエンティティを上空1kmにテレポートさせるコマンド";
    }

    @Override
    public List<String> aliases() {
        return List.of("at");
    }
}
