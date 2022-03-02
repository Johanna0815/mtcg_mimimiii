package com.mimimiii.battleTools;

import com.mimimiii.battleTools.elementTypes.ElementType;

public class Card {

    private String name;


    private float damage;

    private ElementType element;


    public Card(String name, float damage, ElementType element) {
        this.name = name;
        this.damage = damage;
        this.element = element;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public ElementType getElement() {
        return element;
    }

    public void setElement(ElementType element) {
        this.element = element;
    }






}
