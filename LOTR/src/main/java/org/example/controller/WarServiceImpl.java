package org.example.controller;

import org.example.model.Beast;
import org.example.model.Hero;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WarServiceImpl implements WarService {
    List<Hero> heroes = new ArrayList<>();
    List<Beast> beasts = new ArrayList<>();
    @Override
    public List<Hero> addHero(Hero hero) {
        heroes.add(hero);
        return heroes;
    }

    @Override
    public List<Beast> addBeast(Beast beast) {
        beasts.add(beast);
        return beasts;
    }

    @Override
    public List<String> forFrodo() {
        List<String> fight = new ArrayList<>();
            int index = 0;
            int shift = 1;
            while (!heroes.isEmpty() && !beasts.isEmpty()) {
                fight.add("******* Turno " + shift + ": *******");
                Hero hero = heroes.get(index);
                Beast beast = beasts.get(index);

                hero.attack(beast);
                fight.add(hero.getName().toUpperCase() + " ha sacado una potencia de ataque de " + hero.getRoll());
                fight.add(hero.getRollSuccess());
                fight.add(hero.getName().toUpperCase() + " ⚔⚔⚔ atacó ⚔⚔⚔ a " + beast.getName().toUpperCase() +
                        ". \n❤❤❤ Puntos de vida ❤❤❤ de " + beast.getName().toUpperCase() + ": " +
                        beast.getLifePoints() + ".");

                if(beast.getLifePoints() <= 0){
                    fight.add("💀💀💀 " + beast.getName().toUpperCase() + " ha muerto 💀💀💀");
                    beasts.remove(beast);
                    index = 0;
                }
                if(beast.getLifePoints() > 0) {
                    beast.attack(hero);
                    fight.add(beast.getName().toUpperCase() + " ha sacado una potencia de ataque de " + beast.getRoll());
                    fight.add(beast.getRollSuccess());
                    fight.add(beast.getName().toUpperCase() + " ⚔⚔⚔ atacó ⚔⚔⚔ a " + hero.getName().toUpperCase() +
                            ". \n❤❤❤ Puntos de vida ❤❤❤ de " + hero.getName().toUpperCase() + ": " +
                            hero.getLifePoints() + ".");
                }

                if(hero.getLifePoints() <= 0){
                    fight.add("💀💀💀 " + hero.getName().toUpperCase() + " ha muerto 💀💀💀");
                    heroes.remove(hero);
                    index = 0;
                }

                if(beasts.size() == 0){
                    fight.add("¡¡Los héroes ganan!!.\nLanza el Anillo Único al fuego del Monte del Destino.");
                    index = -1;
                    break;
                } else if(heroes.size() == 0) {
                    fight.add("¡¡Las bestias ganan!!.\nSauron ha recuperado el Anillo Único y, con él, su poder.");
                    index = -1;
                    break;
                }

                index++;
                if(index >= heroes.size() || index >= beasts.size()){
                    index = 0;
                    shift ++;
                }
            }
            return fight;
    }

    @Override
    public List<Hero> changeOrderHero(int direction, int targetIndex) {
        Hero hero = heroes.get(targetIndex);
        if(direction > 0) {
            if(targetIndex - 1 >= 0) {
                heroes.remove(targetIndex);
                heroes.add(targetIndex - 1, hero);
            }
        } else if (direction < 0) {
            if(targetIndex + 1 <= heroes.size() - 1) {
                heroes.remove(targetIndex);
                heroes.add(targetIndex + 1, hero);
            }
        }
        return heroes;
    }

    @Override
    public List<Beast> changeOrderBeast(int direction, int targetIndex) {
        Beast beast = beasts.get(targetIndex);
        if(direction > 0) {
            if(targetIndex - 1 >= 0) {
                beasts.remove(targetIndex);
                beasts.add(targetIndex - 1, beast);
            }
        } else if (direction < 0) {
            if(targetIndex + 1 <= beasts.size() - 1) {
                beasts.remove(targetIndex);
                beasts.add(targetIndex + 1, beast);
            }
        }
        return beasts;
    }

    @Override
    public List<Hero> getAllHeroes() {
        return heroes;
    }

    @Override
    public List<Beast> getAllBeasts() {
        return beasts;
    }

    @Override
    public List<Hero> deleteHero(int index) {
        heroes.remove(index);
        return heroes;
    }

    @Override
    public List<Beast> deleteBeast(int index) {
        beasts.remove(index);
        return beasts;
    }
}
