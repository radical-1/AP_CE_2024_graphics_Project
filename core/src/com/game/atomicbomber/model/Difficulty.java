package com.game.atomicbomber.model;

public enum Difficulty {
    EASY(5F, 5F, 5F, 5F),
    MEDIUM(10F, 10F, 10F, (float) (0.75 * 5)),
    HARD(15F, 15F, 15F, (float) (0.5 * 5));
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
