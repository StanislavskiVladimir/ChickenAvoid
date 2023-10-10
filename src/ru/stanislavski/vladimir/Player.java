package ru.stanislavski.vladimir;

public class Player extends  CreatureImpl{
    private int countHeal = 4;
    Player(int hitPoints, int attack, int defence, int minDamage, int maxDamage) throws IllegalArgumentException {
        super(hitPoints, attack, defence, minDamage, maxDamage);
    }
    Player(int hitPoints, int attack, int defence, int minDamage, int maxDamage,int positionX,int positionY, int speed) throws IllegalArgumentException {
        super(hitPoints, attack, defence, minDamage, maxDamage);
        setPositionX(positionX);
        setPositionY(positionY);
        setSpeed(speed);
    }
    public void heal(){
        if(countHeal > 0 && getHitPoints() < 20 ) {
            int hp = getHitPoints() + (int) (getMaxHitPoints() * 0.3);
            setHitPoints(hp);
            if (getHitPoints() > getMaxHitPoints()) {
                setHitPoints(getMaxHitPoints());
            }
            countHeal--;
        }
    }
    public int getCountHeal() {
        return countHeal;
    }
}
