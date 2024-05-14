package com.game.atomicbomber.model;

public enum Difficulty {
    EASY(50F, 250, 200F, 10),
    MEDIUM(100F, 450F, 400F, (float) (0.75 * 10)),
    HARD(150F, 750F, 600F, (float) (0.5 * 10));
    private float tankSpeed;
    private float migAttackRange;
    private float tankAttackRange;
    private float timeBetweenMigPassing;

    Difficulty(float tankSpeed, float migAttackRange, float tankAttackRange, float timeBetweenMigPassing) {
        this.tankSpeed = tankSpeed;
        this.migAttackRange = migAttackRange;
        this.tankAttackRange = tankAttackRange;
        this.timeBetweenMigPassing = timeBetweenMigPassing;
    }

    public float getTankSpeed() {
        return tankSpeed;
    }

    public float getMigAttackRange() {
        return migAttackRange;
    }

    public float getTankAttackRange() {
        return tankAttackRange;
    }

    public float getTimeBetweenMigPassing() {
        return timeBetweenMigPassing;
    }
}
