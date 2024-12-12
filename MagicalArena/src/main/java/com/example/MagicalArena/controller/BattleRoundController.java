package com.example.MagicalArena.controller;

import com.example.MagicalArena.advice.ApiResponse;
import com.example.MagicalArena.model.BattleResult;
import com.example.MagicalArena.repository.PlayerRepository;
import com.example.MagicalArena.service.BattleRoundService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/players")
public class BattleRoundController {

    private final BattleRoundService battleRoundService;
    private final PlayerRepository playerRepository;

    public BattleRoundController(BattleRoundService battleRoundService, PlayerRepository playerRepository) {
        this.battleRoundService = battleRoundService;
        this.playerRepository = playerRepository;
    }

    @PostMapping("/battle/playerId1/{playerId1}/playerId2/{playerId2}")
    public ResponseEntity<ApiResponse<Object>> startBattle(
            @PathVariable Long playerId1,
            @PathVariable Long playerId2) {

        BattleResult battleResult = battleRoundService.startBattle(playerId1, playerId2);

        if (battleResult.getWinner() == null) {
            ApiResponse<Object> response = new ApiResponse<>(false, battleResult.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        ApiResponse<Object> response = new ApiResponse<>(
                true,
                battleResult.getMessage(),
                battleResult.getWinner()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
