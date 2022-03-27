package com.mimimiii.battleTools.battle;


import java.util.ArrayList;
import java.util.List;

public class BattleService {
    private List<Battle> battleData;

    public BattleService() {
        battleData = new ArrayList<>();
        battleData.add(new Battle(1,"ork", 9.f));
        battleData.add(new Battle(2,"battle", 8.f));
        battleData.add(new Battle(3,"kraken", 12.f));
    }

    // GET /battle/:id
    public Battle getBattle(Integer ID) {
        Battle foundBattle = battleData.stream()
                .filter(battle -> ID == battle.getId())
                .findAny()
                .orElse(null);

        return foundBattle;
    }

    // GET /battle
    public List<Battle> getBattle() {
        return battleData;
    }

    // POST /battle
    public void addBattle(Battle battle) {
        battleData.add(battle);
    }
}
