package com.example.natarian.pokemonquiz;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Natarian on 3/12/17.
 */

public class Score extends RealmObject {
    @PrimaryKey
    private String playTime;
    private String playerName;
    private int playerScore;

    public static Score create(String playTime, String playerName, int playerScore) {
        Score score = new Score();

        score.playTime = playTime;
        score.playerName = playerName;
        score.playerScore = playerScore;

        return score;
    }

    public String getPlayTime() {
        return playTime;
    }

    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }
}
