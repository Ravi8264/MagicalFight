package com.example.MagicalArena.service;

import com.example.MagicalArena.model.BattleResult;

public interface BattleRoundService {
    BattleResult startBattle(Long playerId1, Long playerId2);
}
