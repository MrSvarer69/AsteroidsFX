package dk.sdu.mmmi.cbse.common.data;

public class GameScore {

    private int score = 0;

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        score += points;
    }
}