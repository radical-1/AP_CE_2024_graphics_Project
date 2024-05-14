package com.game.atomicbomber.model;

public enum Difficulty {
    EASY(25F, 25F, 25F, 25F),
    MEDIUM(50F, 50F, 50F, (float) (0.75 * 25)),
    HARD(75F, 75F, 75F, (float) (0.5 * 25));
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
