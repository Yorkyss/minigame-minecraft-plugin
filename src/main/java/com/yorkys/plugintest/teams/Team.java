package com.yorkys.plugintest.teams;

import com.yorkys.plugintest.MiniGame;
import com.yorkys.plugintest.players.MGPlayer;
import com.yorkys.plugintest.players.PlayerType;
import org.bukkit.ChatColor;

import java.util.*;

public class Team {
    private List<MGPlayer> mgPlayers = new ArrayList<>();
    private String color;
    private ChatColor chatColor;
    private int maxSize;
    private MiniGame miniGame;

    private int blueStars = 0;
    private int redStars = 0;

    public Team (String color, int maxSize, ChatColor chatColor, MiniGame miniGame) {
        this.color = color;
        this.maxSize = maxSize;
        this.miniGame = miniGame;
        this.chatColor = chatColor;
    }

    public void giveRoles() {
        Set<PlayerType> availableTypes = EnumSet.allOf(PlayerType.class);
        for (MGPlayer player : mgPlayers) {
            if (player.getType() != null) {
                availableTypes.remove(player.getType());
            }
        }

        Iterator<PlayerType> iterator = availableTypes.iterator();
        for (MGPlayer player : mgPlayers) {
            if (player.getType() == null && iterator.hasNext()) {
                player.setType(iterator.next());
            }
        }
    }


    public boolean addPlayer(MGPlayer mgPlayer) {
        if (mgPlayers.size() < maxSize) {
            mgPlayers.add(mgPlayer);
            mgPlayer.setTeam(this);
            return true;
        }
        return false;
    }

    public boolean removePlayer(MGPlayer mgPlayer) {
        if (mgPlayers.contains(mgPlayer)) {
            mgPlayers.remove(mgPlayer);
            mgPlayer.removeTeam();
            return true;
        }
        return false;
    }

    public int getSize() {
        return mgPlayers.size();
    }

    public int getMaxSize() {
        return maxSize;
    }

    public String getColor() {
        return color;
    }

    public List<MGPlayer> getPlayers() {
        return mgPlayers;
    }

    public MGPlayer getPlayerFromType(PlayerType type) {
        return mgPlayers.stream()
                .filter(MGPlayer -> MGPlayer.getType() == type)
                .findFirst()
                .orElse(null);
    }

    public void addBlueStar(int value) {
        blueStars += value;
    }

    public void removeBlueStar(int value) {
        blueStars -= value;
    }

    public void resetBlueStars() {
        blueStars = 0;
    }

    public int getBlueStars() {
        return blueStars;
    }

    public void updateRedStars() {
        int newRedStars = mgPlayers.stream().mapToInt(MGPlayer::getStars).sum();
        redStars = newRedStars;
    }

    public int getRedStars() {
        return redStars;
    }

    public void resetRedStars() {
        mgPlayers.forEach(MGPlayer::resetStars);
        redStars = 0;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }
}
