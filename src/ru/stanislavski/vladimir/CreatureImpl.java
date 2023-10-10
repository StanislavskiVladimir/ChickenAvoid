package ru.stanislavski.vladimir;

public abstract class CreatureImpl implements Creature {
    final int DICE_SUCCESS = 5;
    private int hitPoints;
    private int maxHitPoints;
    private int attack;
    private int defence;
    private int minDamage;
    private int maxDamage;
    private int positionX;
    private int positionY;
    private int speed;


    CreatureImpl(int hitPoints,int attack, int defence, int minDamage, int maxDamage) throws IllegalArgumentException{
        if(hitPoints < 0){
            throw new IllegalArgumentException("Здоровье не может быть отрицательным");
        } else{
            this.hitPoints = hitPoints;
            this.maxHitPoints = hitPoints;
        }
        if(attack < 0 || attack >30){
            throw new IllegalArgumentException("Неверное значение атаки");
        } else {
            this.attack = attack;
        }
        if(defence < 0 || defence >30){
            throw new IllegalArgumentException("Неверное значение защиты");
        } else {
            this.defence = defence;
        }
        if(minDamage < 0 || maxDamage < 1 || minDamage > maxDamage){
            throw new IllegalArgumentException("Неверное значение урона");
        } else {
            this.minDamage = minDamage;
            this.maxDamage = maxDamage;
        }
    }
    @Override
    public void attack(CreatureImpl creature) {
        int attackModifier =  getAttack() - creature.getDefence() - 1;
        if( attackModifier < 1) {
            attackModifier = 1;
        }
        for(int i = 0; i < attackModifier; i++){
            int dice = getRandomNumber(1,6);
            if(dice >= DICE_SUCCESS){
                creature.setHitPoints(creature.getHitPoints() - getRandomNumber(minDamage,maxDamage));
                return;
            }
        }
    }

    public int getHitPoints() {
        return hitPoints;
    }
    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getMaxHitPoints() {
        return maxHitPoints;
    }

    public int getAttack() {
        return attack;
    }
    public int getDefence() {
        return defence;
    }
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min + 1)) + min);
    }

    public int getPositionX() {
        return positionX;
    }
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }
    public int getPositionY() {
        return positionY;
    }
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
