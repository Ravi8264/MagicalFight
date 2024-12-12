package com.example.MagicalArena.repository;

import com.example.MagicalArena.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player,Long> {

}
