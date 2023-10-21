package dev.xernas.loupgarou.messages;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class Message {

    private final String message;
    private final ChatColor color;
    private String[] data = null;

    private static CommandSender sender = null;

    private Message(String message, ChatColor color) {
        this.message = message;
        this.color = color;
    }

    public static Message create(String message, ChatColor color) {
        return new Message(message, color);
    }

    public String getMessage() {
        if (data == null) {
            setData("-1", "NONE");
        }
        return color + "" + ChatColor.BOLD + format(message, data);
    }

    public ChatColor getColor() { return color; }

    public boolean send() {
        if (sender != null) {
            sender.sendMessage(this.getMessage());
        }
        else {
            throw new NullPointerException("sender or data is null");
        }
        return this.color == ChatColor.GREEN || this.color == ChatColor.LIGHT_PURPLE;
    }

    public static void setSender(CommandSender sender) {
        Message.sender = sender;
    }

    public Message setData(String... data) {
        this.data = data;
        return this;
    }

    private String format(String message, String[] data) {
        for (String s : data) {
            String replaced = message.replace("%spawnpoint_number%", data[0]);
        }
    }

    public static final Message ERROR_NOT_PLAYER = Message.create("Désolé, seuls les joueurs peuvent utiliser cette commande", ChatColor.RED);

    public static final Message ERROR_NOT_ENOUGH_ARGS = Message.create("Vous n'avez pas donné assez d'arguments", ChatColor.RED);
    public static final Message ERROR_PLAYER_NOT_FOUND = Message.create("Le joueur %player% n'est pas trouvable", ChatColor.RED);

    public static final Message ERROR_NO_GAME = Message.create("Il n'y a pas de partie à arrêter", ChatColor.RED);
    public static final Message ERROR_ALREADY_STARTED = Message.create("La partie est déjà en cours", ChatColor.RED);
    public static final Message ERROR_NOT_ENOUGH_PLAYERS = Message.create("Il n'y a pas assez de joueurs pour lancer la partie", ChatColor.RED);

    public static final Message SUCCESS_GAME_STARTED = Message.create("La partie a bien commencée !", ChatColor.GREEN);
    public static final Message SUCCESS_GAME_STOPPED = Message.create("La partie s'est bien terminée !", ChatColor.GREEN);
    public static final Message SUCCESS_SPAWN_SET = Message.create("Vous avez bien set le point de spawn n°%spawnpoint_number% pour le joueur %player%!", ChatColor.GREEN);
    public static final Message SUCCESS_SPAWN_REMOVE = Message.create("Vous avez bien supprimé le point de spawn du joueur %player%!", ChatColor.GREEN);

    public static final Message SUCCESS_RESET = Message.create("Vous avez bien reset tous les points de spawn", ChatColor.GREEN);

    public static final Message CONFIRM_RESET = Message.create("Vous êtes sûr de vouloir reset tous les points de spawn ? (Y/N)", ChatColor.LIGHT_PURPLE);
}
