package ohtu;

import java.util.HashMap;

public class TennisGame {
    
    private int points1 = 0;
    private int points2 = 0;
    private final String player1;
    private final String player2;
    private final HashMap<Integer, String> scores = new HashMap<>();

    public TennisGame(String player1, String player2) {
        this.player1 = player1;
        this.player2 = player2;

        scores.put(0, "Love");
        scores.put(1, "Fifteen");
        scores.put(2, "Thirty");
        scores.put(3, "Forty");
    }

    public void wonPoint(String player) {
        if (player.equals(player1)) {
            points1 += 1;
        } else {
            points2 += 1;
        }
    }

    public String getScore() {
        if (points1 == points2) {
            if (scores.containsKey(points1)) {
                return scores.get(points1) + "-All";
            } else {
                return "Deuce";
            }
        } else if (points1 >= 4 || points2 >= 4) {
            String leadingPlayer = (points1 > points2) ? player1 : player2;
            int pointsDifference = Math.abs(points1 - points2);
            if (pointsDifference > 1) {
                return "Win for " + leadingPlayer;
            } else {
                return "Advantage " + leadingPlayer;
            }
        } else {
            return scores.get(points1) + "-" + scores.get(points2);
        }
    }
}