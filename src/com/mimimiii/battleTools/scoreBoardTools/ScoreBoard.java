package com.mimimiii.battleTools.scoreBoardTools;

import java.util.LinkedList;

public class ScoreBoard {
    private LinkedList<ScoreRelated> scores;

    public ScoreBoard(){
        this.scores= new LinkedList<ScoreRelated>();
    }

    public LinkedList<ScoreRelated> getScores() {
        return scores;
    }
    public void addScore(ScoreRelated s){
        scores.add(s);
    }

    @Override
    public String toString() {
        return "ScoreBoard{" +
                "scores=" + scores +
                '}';
    }
}
