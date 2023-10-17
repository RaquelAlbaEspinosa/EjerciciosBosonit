package com.example.lotrfx.model;

public class Beast extends Character{
    private final int DICE = 90;
    public Beast(int lifePoints, String name, Race race, int armor) {
        super(lifePoints, name, race, armor);
        if(race != Race.ORC && race != Race.GOBLIN){
            throw new IllegalArgumentException("La raza no puede ser ELF ni HOBBIT ni HUMAN para una Bestia.");
        }
    }

    @Override
    public Character attack(Character character) {
        int roll = (int) (Math.random() * DICE);
        setRoll(roll);
        int currentArmor = character.getArmor();
        if (this.getRace() == Race.ORC){
            currentArmor = character.getArmor() - (character.getArmor() * 10 / 100);
        }
        if (currentArmor < roll){
            int excess = roll - currentArmor;
            character.setLifePoints(character.getLifePoints() - excess);
            setRollSuccess("¡La bestia ha dado en el blanco!");
        } else {
            setRollSuccess("La bestia no tuvo fuerza suficiente.");
        }
        return character;
    }
}
