package com.github.namiuni.paperpluginexample.commands;

import com.github.namiuni.paperpluginexample.message.Message;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.Bukkit;
import org.bukkit.entity.PigZombie;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

import java.util.List;

@DefaultQualifier(NonNull.class)
@SuppressWarnings("UnstableApiUsage")
public final class AngryZombifiedPiglinCommand implements BaseCommand {

    @Override
    public LiteralCommandNode<CommandSourceStack> create() {
        return Commands.literal("angryzombifiedpiglin") //helloコマンドを生成する
                .requires(context -> context.getSender().hasPermission("paperpluginexample.command.angryzombifiedpiglin")) //パーミッションチェック
                .executes(context -> { //コマンドを送信した際に実行する処理内容

                    // 全てのワールドを取得する。
                    final var worlds = Bukkit.getWorlds();

                    // サーバーで読み込まれている全てのゾンビピグリンを怒らせる。
                    var count = 0;
                    for (final var world : worlds) {
                        for (final var livingEntity : world.getLivingEntities()) { // 全てのLivingEntityに対して操作を行う
                            if (livingEntity instanceof final PigZombie pigZombie) { // ゾンビピグリンのみを対象にする

                                // LivingEntityをゾンビピグリンにキャストする
                                pigZombie.setAngry(true); //ゾンビピグリンを怒らせる。
                                count++;
                            }
                        }
                    }

                    context.getSource().getSender().sendMessage(Message.angryZombifiedPiglin(count));

                    // 戻り値は毎回これで大丈夫
                    return Command.SINGLE_SUCCESS;
                })
                .build();
    }

    @Override
    public String description() {
        return "ネザーで読み込まれているゾンビピグリンを全て怒らせるコマンド";
    }

    @Override
    public List<String> aliases() {
        return List.of("azp"); // エイリアスを設定する。
    }
}
