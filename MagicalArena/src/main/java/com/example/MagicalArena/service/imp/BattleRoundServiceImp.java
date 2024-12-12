package com.example.MagicalArena.service.imp;

import com.example.MagicalArena.model.BattleResult;
import com.example.MagicalArena.model.Player;
import com.example.MagicalArena.repository.PlayerRepository;
import com.example.MagicalArena.service.BattleRoundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class BattleRoundServiceImp implements BattleRoundService {

    private final PlayerRepository playerRepository;
    private static final Logger logger = LoggerFactory.getLogger(BattleRoundServiceImp.class);

    public BattleRoundServiceImp(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public BattleResult startBattle(Long playerId1, Long playerId2) {
        Optional<Player> playerAOpt = playerRepository.findById(playerId1);
        Optional<Player> playerBOpt = playerRepository.findById(playerId2);

        if (playerAOpt.isEmpty() && playerBOpt.isEmpty()) {
            return new BattleResult(null, "One or both players not found");
        }

        if (playerAOpt.isEmpty()) {
            return new BattleResult(null, "Player with ID " + playerId1 + " not found");
        }

        if (playerBOpt.isEmpty()) {
            return new BattleResult(null, "Player with ID " + playerId2 + " not found");
        }

        Player playerA = playerAOpt.get();
        Player playerB = playerBOpt.get();
        Random random = new Random();

        while (playerA.isAlive() && playerB.isAlive()) {
            if (playerA.getHealth() < playerB.getHealth()) {
                attackTurn(playerA, playerB, random);
                if (playerB.isAlive()) {
                    attackTurn(playerB, playerA, random);
                }
            } else {
                attackTurn(playerB, playerA, random);
                if (playerA.isAlive()) {
                    attackTurn(playerA, playerB, random);
                }
            }
        }

        if (playerA.isAlive()) {
            return new BattleResult(playerA, playerA.getName() + " wins!");
        } else if (playerB.isAlive()) {
            return new BattleResult(playerB, playerB.getName() + " wins!");
        }

        return new BattleResult(null, "Draw");
    }

    private void attackTurn(Player attacker, Player defender, Random random) {
        int attackRoll = random.nextInt(6) + 1;
        int attackDamage = attacker.getAttack() * attackRoll;

        int defendRoll = random.nextInt(6) + 1;
        int defendDamage = defender.getStrength() * defendRoll;

        int finalDamage = Math.max(attackDamage - defendDamage, 0);
        defender.reduceHealth(finalDamage);

        logger.info("{} attacks with {} (damage: {})", attacker.getName(), attackRoll, attackDamage);
        logger.info("{} defends with {} (damage reduced: {})", defender.getName(), defendRoll, defendDamage);
        logger.info("{}'s health is now {}", defender.getName(), defender.getHealth());
    }
}
