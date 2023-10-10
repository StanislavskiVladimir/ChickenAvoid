package ru.stanislavski.vladimir;

public class Monster extends CreatureImpl{
    Monster(int hitPoints, int attack, int defence, int minDamage, int maxDamage) throws IllegalArgumentException {
        super(hitPoints, attack, defence, minDamage, maxDamage);
    }
}
