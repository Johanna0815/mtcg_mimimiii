package com.mimimiii.battleTools.battle;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
public class Battle {
/*
    @Getter
    String matched = "thihi";
*/

    @Getter
    @Setter
    @JsonAlias({"id"})
    private Integer id;
    @Getter
    @Setter
    @JsonAlias({"battle"})
    private String battle;
    @Getter
    @Setter
    @JsonAlias({"battlePoints"})
    private float battlePoints;

    // Jackson needs the default constructor
    public Battle() {}


    /*
    public Battle(Integer id, String battle, float points) {
        this.id = id;
        this.battle = battle;
        this.battlePoints = points;
    }


     */
}
