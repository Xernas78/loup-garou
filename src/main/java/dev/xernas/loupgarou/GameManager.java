package dev.xernas.loupgarou;

import dev.xernas.loupgarou.messages.Message;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameManager {

    private static final Map<Player, Location> spawnPoints = new HashMap<>();
    private static final Map<Player, Location> playerLocationsBeforeGame = new HashMap<>();
    private static final List<Player> players = new ArrayList<>();
    public static boolean inGame = false;

    public static Map<Player, Location> getSpawnPoints() {
        return spawnPoints;
    }

    public static List<Player> getPlayers() {
        return players;
    }

    public static void setSpawnPoint(Player player, Location spawnPoint) {
        spawnPoints.put(player, spawnPoint);
        players.add(player);
    }

    public static void removeSpawnPoint(Player player) {
        spawnPoints.remove(player);
        players.remove(player);
    }

    public static Message start() {
        if (inGame) {
            return Message.ERROR_ALREADY_STARTED;
        }
        if (players.size() < 1) {
            return Message.ERROR_NOT_ENOUGH_PLAYERS;
        }
        players.forEach(player -> playerLocationsBeforeGame.put(player, player.getLocation()));
        spawnPoints.forEach(Entity::teleport);
        inGame = true;
        return Message.SUCCESS_GAME_STARTED;
    }

    public static Message stop() {
        if (!inGame) {
            return Message.ERROR_NO_GAME;
        }
        players.forEach(player -> player.teleport(playerLocationsBeforeGame.get(player)));
        playerLocationsBeforeGame.clear();
        inGame = false;
        return Message.SUCCESS_GAME_STOPPED;
    }

    public static Message reset() {
        players.clear();
        spawnPoints.clear();
        playerLocationsBeforeGame.clear();
        return Message.SUCCESS_RESET;
    }

}
