package com.example.MagicalArena.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "players")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters")
    private String name;

    @NotNull(message = "Health is required")
    @Min(value = 1, message = "Health must be greater than 0")
    private int health;

    @NotNull(message = "Strength is required")
    @Min(value = 1, message = "Strength must be greater than 0")
    private int strength;

    @NotNull(message = "Attack is required")
    @Min(value = 1, message = "Attack must be greater than 0")
    private int attack;

    public boolean isAlive() {
        return health > 0;
    }

    public void reduceHealth(int damage) {
        this.health = Math.max(this.health - damage, 0);
    }
}
