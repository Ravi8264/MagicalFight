package com.example.MagicalArena;

import com.example.MagicalArena.service.BattleRoundService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class MagicalArenaApplicationTests {

	@Autowired
	private BattleRoundService battleRoundService;

	@Test
	void contextLoads() {
		assertNotNull(battleRoundService);
	}
}
