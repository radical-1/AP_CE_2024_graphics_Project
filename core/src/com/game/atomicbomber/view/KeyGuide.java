package com.game.atomicbomber.view;

public enum KeyGuide {
    ARROW_KEYS("Arrow Keys\n", "Use the arrow keys to move the ship\n"),
    WASD_KEYS("WASD Keys\n", "Use the WASD keys to move the ship\n"),
    SPACE_BAR("Space Bar\n", "Press the space bar to drop normal bomb\n"),
    B_KEY("B Key\n", "Press the R key to drop a Radio Active Bomb\n"),
    C_KEY("C Key\n", "Press the C key to drop a Cluster Bomb\n"),
    TAB_KEY("Tab Key\n", "Press the Tab key to freeze the game\n notice the freeze bar that is in the right side of the screen\n"),
    H_KEY("H Key\n", "( cheat code(; )\nPress the H key to refill the health of the ship after you lost game\n"),
    P_KEY("P Key\n", "( cheat code (; )\nPress the Tab key skip this wave of game and go to the next wave\n"),
    G_KEY("G Key\n", "( cheat code (; )\nPress the G key to get a Radio Active Bomb\n"),
    CONTROL_KEY("Control Key\n", "(cheat code (; )\nPress the Control key to get a Cluster Bomb\n"),
    T_KEY("T Key\n", "( cheat code (; )\nPress the T key to put a tank in a random place in the game\n"),

    ;


    private final String key;
    private final String Explanation;
    KeyGuide(String key, String Explanation) {
        this.key = key;
        this.Explanation = Explanation;
    }

    public String getKey() {
        return key;
    }
    public String getExplanation() {
        return Explanation;
    }

}
