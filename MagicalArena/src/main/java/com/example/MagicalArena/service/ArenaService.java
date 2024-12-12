package com.example.MagicalArena.service;

import com.example.MagicalArena.model.Player;

import java.util.Map;

public interface ArenaService {
    Player addPlayer(Player player);

    boolean updatePlayer(Long userId, Player playerDetails);

    boolean deletePlayer(Long userId);

    Player updatePartialPlayerById(Long playerId, Map<String, Object> updates);
}
