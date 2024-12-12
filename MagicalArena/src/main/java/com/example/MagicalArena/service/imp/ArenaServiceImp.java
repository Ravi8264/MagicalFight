package com.example.MagicalArena.service.imp;

import com.example.MagicalArena.model.Player;
import com.example.MagicalArena.repository.PlayerRepository;
import com.example.MagicalArena.service.ArenaService;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

@Service
public class ArenaServiceImp implements ArenaService {

    private final PlayerRepository playerRepository;

    public ArenaServiceImp(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player addPlayer(Player player) {

        return playerRepository.save(player);
    }

    @Override
    public boolean updatePlayer(Long userId, Player playerDetails) {
        Optional<Player> playerOpt = playerRepository.findById(userId);
        if (playerOpt.isPresent()) {
            Player player = playerOpt.get();
            player.setName(playerDetails.getName());
            player.setHealth(playerDetails.getHealth());
            player.setStrength(playerDetails.getStrength());
            player.setAttack(playerDetails.getAttack());
            playerRepository.save(player);
            return true;
        }
        return false;
    }

    @Override
    public boolean deletePlayer(Long userId) {
        Optional<Player> existingPlayer=playerRepository.findById(userId);
        if(existingPlayer.isPresent()){
            playerRepository.deleteById(userId);
            return true;
        }
        return  false;
    }

    @Override
    public Player updatePartialPlayerById(Long playerId, Map<String, Object> updates) {

        // Check if player exists
        Optional<Player> existingPlayerOpt = playerRepository.findById(playerId);

        if (existingPlayerOpt.isPresent()) {
            Player player = existingPlayerOpt.get();

            // Iterate through the updates map and set each field using reflection
            updates.forEach((field, value) -> {
                // Ensure we're working with valid fields in Player entity
                try {
                    Field fieldToBeUpdated = Player.class.getDeclaredField(field); // Use Player class directly
                    fieldToBeUpdated.setAccessible(true); // Make the field accessible
                    fieldToBeUpdated.set(player, value); // Set the value to the player object
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();  // Log the exception if the field doesn't exist or can't be accessed
                }
            });

            // Save the updated player
            return playerRepository.save(player);
        }

        return null;  // Return null if player not found

    }
}
