package com.mimimiii.battleTools.user;

public class Profile {
    private String username;
    private String name;
    private String bio;
    private String image;
    private int won;
    private int lost;
    private int elo;
    private int coins;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                ", image='" + image + '\'' +
                ", won=" + won +
                ", lost=" + lost +
                ", elo=" + elo +
                ", coins=" + coins +
                '}';
    }
}
