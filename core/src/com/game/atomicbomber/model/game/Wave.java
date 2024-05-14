package com.game.atomicbomber.model.game;

import com.game.atomicbomber.AtomicBomber;
import com.game.atomicbomber.model.Difficulty;
import com.game.atomicbomber.model.game.enemy_objects.*;

import java.util.Random;

public class Wave {
    private int numTanks;
    private int numTrucks;
    private int numBuildings;
    private int numRiflepits;
    private int numTrees;
    private boolean spawnMig;
    private Difficulty difficulty;

    public Wave(int numTanks, int numTrucks, int numBuildings, int numRiflepits, int numTrees, boolean doesSpawnMig, Difficulty difficulty) {
        this.numTanks = numTanks;
        this.numTrucks = numTrucks;
        this.numBuildings = numBuildings;
        this.numRiflepits = numRiflepits;
         this.numTrees = numTrees;
        this.spawnMig = doesSpawnMig;
        this.difficulty = difficulty;
        spawnEnemies(Game.getPlayingGame());
    }

    public void spawnEnemies(Game game) {
        Random random = new Random();
        // Spawn tanks
        for (int i = 0; i < numTanks; i++) {
            float x = random.nextFloat() * AtomicBomber.WIDTH;

            game.addEnemy(new Tank(x, 50, difficulty.getTankSpeed(), difficulty.getTankAttackRange()));

        }
        // Spawn trucks, buildings, riflepits, and trees similarly...
        for (int i = 0; i < numTrucks; i++) {
            float x = random.nextFloat() * AtomicBomber.WIDTH;
            game.addEnemy(new Truck(x, 50));
        }
        for (int i = 0; i < numBuildings; i++) {
            float x = random.nextFloat() * AtomicBomber.WIDTH;
            game.addEnemy(new Building(x, 50));
        }
        for (int i = 0; i < numRiflepits; i++) {
            float x = random.nextFloat() * AtomicBomber.WIDTH;
            game.addEnemy(new riflePit(x, 50));
        }
        for (int i = 0; i < numTrees; i++) {
            float x = random.nextFloat() * AtomicBomber.WIDTH;

            game.addEnemy(new Tree(x, 50));
        }

        if(spawnMig) {
            int rand = random.nextInt() * 2;
            float y = (random.nextFloat() * AtomicBomber.HEIGHT - 500) + 400;
            if(rand == 0)
                game.addEnemy(new Mig(-160, y, Mig.MIG_SPEED, difficulty.getMigAttackRange()));
            else {
                Mig mig = new Mig(AtomicBomber.WIDTH + 160, y, -Mig.MIG_SPEED, difficulty.getMigAttackRange());
                mig.getSprite().flip(true, false);
                game.addEnemy(mig);


            }

        }
    }
}