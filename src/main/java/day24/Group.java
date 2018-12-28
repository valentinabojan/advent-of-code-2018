package day24;

import java.util.List;

public class Group {

    private Army army;
    private int units;
    private int hitPoints;
    private int attackDamage;
    private int initiative;
    private String attackType;
    private List<String> weaknesses;
    private List<String> immunities;

    public Group(Army army, int units, int hitPoints, int attackDamage, int initiative, String attackType, List<String> weaknesses, List<String> immunities) {
        this.army = army;
        this.units = units;
        this.hitPoints = hitPoints;
        this.attackDamage = attackDamage;
        this.initiative = initiative;
        this.attackType = attackType;
        this.weaknesses = weaknesses;
        this.immunities = immunities;
    }

    public Army getArmy() {
        return army;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public int getInitiative() {
        return initiative;
    }

    public String getAttackType() {
        return attackType;
    }

    public int getEffectivePower() {
        return this.units * this.attackDamage;
    }

    public List<String> getWeaknesses() {
        return weaknesses;
    }

    public List<String> getImmunities() {
        return immunities;
    }


}
