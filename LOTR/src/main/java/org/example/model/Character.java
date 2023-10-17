package org.example.model;

public class Character {
    private int lifePoints;
    private String name;
    private Race race;
    private int armor;
    private int roll;
    private String rollSuccess;

    public Character(int lifePoints, String name, Race race, int armor) {
        this.lifePoints = lifePoints;
        this.name = name;
        this.race = race;
        this.armor = armor;
        if(lifePoints == 0 || armor == 0 || name == null || race == null) {
            throw new IllegalArgumentException("Todos los parámetros deben tener un valor.");
        }
    }

    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    public String getRollSuccess() {
        return rollSuccess;
    }

    public void setRollSuccess(String rollSuccess) {
        this.rollSuccess = rollSuccess;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Character attack (Character character) {
       return character;
    }
}
