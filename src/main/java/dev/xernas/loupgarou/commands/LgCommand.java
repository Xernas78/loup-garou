package dev.xernas.loupgarou.commands;

import dev.xernas.loupgarou.GameManager;
import dev.xernas.loupgarou.LoupGarou;
import dev.xernas.loupgarou.messages.Message;
import dev.xernas.loupgarou.utils.PromptUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LgCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        Message.setSender(sender);

        if (!(sender instanceof Player player)) {
            Message.ERROR_NOT_PLAYER.send();
            return false;
        }

        if (args.length == 0) {
            player.sendMessage("/lg [set, remove, start, reset]");
            return true;
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("set")) {
                Message.ERROR_NOT_ENOUGH_ARGS.send();
            }
            if (args[0].equalsIgnoreCase("remove")) {
                Message.ERROR_NOT_ENOUGH_ARGS.send();
            }
            if (args[0].equalsIgnoreCase("start")) {
                Message result = GameManager.start();
                return result.send();
            }
            if (args[0].equalsIgnoreCase("stop")) {
                Message result = GameManager.stop();
                return result.send();
            }
            if (args[0].equalsIgnoreCase("reset")) {
                ConversationFactory factory = new ConversationFactory(LoupGarou.getInstance());
                factory.withFirstPrompt(PromptUtils.getYesNoPrompt(Message.CONFIRM_RESET, (res, ctx) -> {
                    if (res) {
                        Message reset = GameManager.reset();
                        ctx.getForWhom().sendRawMessage(reset.getMessage());
                    }
                }));
                factory.withLocalEcho(false);
                Conversation conversation = factory.buildConversation(player);
                conversation.begin();
                return true;
            }
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("set")) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    Message.setPluginData(new PluginData(-1, args[1]));
                    Message.send(Message.ERROR_PLAYER_NOT_FOUND);
                    return false;
                }
                GameManager.setSpawnPoint(target, player.getLocation());
                Message.SUCCESS_SPAWN_SET
                        .setData(new PluginData(GameManager.getSpawnPoints().size(), target.getName()))
                        .send();
                return true;
            }
            if (args[0].equalsIgnoreCase("remove")) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    Message.ERROR_PLAYER_NOT_FOUND
                            .setData(new PluginData(-1, args[1]))
                            .send();
                    return false;
                }
                GameManager.removeSpawnPoint(target);
                Message.SUCCESS_SPAWN_REMOVE
                        .setData(new PluginData(-1, target.getName()))
                        .send();
                return true;
            }
        }
        return false;
    }


    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> options = new ArrayList<>();
        if (args.length == 1) {
            options.add("set");
            options.add("remove");
            options.add("start");
            options.add("reset");
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("set")) {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    options.add(player.getName());
                });
            }
            if (args[0].equalsIgnoreCase("remove")) {
                GameManager.getPlayers().forEach(player -> {
                    options.add(player.getName());
                });
            }
        }
        return options;
    }
}
