package org.example.model;

public class Hero extends Character{
    private final int DICE = 100;
    public Hero(int lifePoints, String name, Race race, int armor) {
            super(lifePoints, name, race, armor);
        if(race == Race.ORC || race == Race.GOBLIN){
            throw new IllegalArgumentException("La raza no puede ser ORC ni GOBLIN para un héroe.");
        }
    }

    @Override
    public Character attack(Character character) {
        int roll = 0;
        int roll1 = (int) (Math.random() * DICE);
        int roll2 = (int) (Math.random() * DICE);
        roll = Math.max(roll1, roll2);
        setRoll(roll);
        if (this.getRace() == Race.ELF && character.getRace() == Race.ORC){
            roll += 10;
        } else if (this.getRace() == Race.HOBBIT && character.getRace() == Race.GOBLIN){
            roll -= 5;
        }
        if (character.getArmor() <= roll){
            int excess = roll - character.getArmor();
            character.setLifePoints(character.getLifePoints() - excess);
            setRollSuccess("¡El héroe ha dado en el blanco!");
        } else {
            setRollSuccess("El héroe no tuvo fuerza suficiente.");
        }
        return character;
    }
}
