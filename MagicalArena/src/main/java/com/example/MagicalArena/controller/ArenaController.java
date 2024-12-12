package com.example.MagicalArena.controller;

import com.example.MagicalArena.advice.ApiError;
import com.example.MagicalArena.advice.ApiResponse;
import com.example.MagicalArena.model.Player;
import com.example.MagicalArena.repository.PlayerRepository;
import com.example.MagicalArena.service.ArenaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/players")
@RequiredArgsConstructor
public class ArenaController {

    private final ArenaService arenaService;
    private final PlayerRepository playerRepository;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Player>> addPlayer(@Valid @RequestBody Player player) {
        Player createdPlayer = arenaService.addPlayer(player);
        ApiResponse<Player> response = new ApiResponse<>(createdPlayer);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<ApiResponse<String>> updatePlayer(@PathVariable Long userId, @Valid @RequestBody Player playerDetails) {
        boolean isUpdated = arenaService.updatePlayer(userId, playerDetails);
        if (isUpdated) {
            return ResponseEntity.ok(new ApiResponse<>("Player updated successfully"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>("Player not found"));
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse<String>> deletePlayer(@PathVariable Long userId) {
        boolean isDeleted = arenaService.deletePlayer(userId);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>("Player deleted successfully"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>("Player not found"));
    }


    @PatchMapping("/{playerId}")
    public ResponseEntity<ApiResponse<Player>> updatePartialPlayer(@PathVariable Long playerId, @RequestBody Map<String, Object> updates) {
        Player updatedPlayer = arenaService.updatePartialPlayerById(playerId, updates);
        if (updatedPlayer != null) {
            return ResponseEntity.ok(new ApiResponse<>(updatedPlayer));
        }
        ApiError apiError = new ApiError("Player not found", HttpStatus.NOT_FOUND, List.of("The player with the specified ID does not exist."));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(apiError));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<Player>> getPlayer(@PathVariable Long userId) {
        Optional<Player> player = playerRepository.findById(userId);
        if (player.isPresent()) {
            return ResponseEntity.ok(new ApiResponse<>(player.get()));
        }
        ApiError apiError = new ApiError("Player not found", HttpStatus.NOT_FOUND, List.of("The player with the specified ID does not exist."));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(apiError));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Player>>> getAllPlayers() {
        List<Player> players = playerRepository.findAll();
        return ResponseEntity.ok(new ApiResponse<>(players));
    }
}
