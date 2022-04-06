package com.mimimiii.battleTools.battle;

public class BattleMatchResult {

    private String winner;
    private String looser;

    private boolean remis;



    public BattleMatchResult() {
    }
// bug incluír -----------------------------------------------------------------
    public BattleMatchResult(String winner, String looser, boolean remis) {
        this.winner = winner;
        this.looser = looser;
        this.remis = remis;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getLooser() {
        return looser;
    }

    public void setLooser(String looser) {
        this.looser = looser;
    }

    public boolean isRemis() {
        return remis;
    }

    public void setRemis(boolean remis) {
        this.remis = remis;
    }
}
