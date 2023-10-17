package com.example.lotrfx.controller;

import com.example.lotrfx.model.Beast;
import com.example.lotrfx.model.Hero;

import java.util.List;

public interface WarService {
    List<Hero> addHero (Hero hero);
    List<Beast> addBeast (Beast beast);
    List<String> forFrodo ();
    List<Hero> changeOrderHero (int direction, int targetIndex);
    List<Beast> changeOrderBeast (int direction, int targetIndex);
    List<Hero> getAllHeroes ();
    List<Beast> getAllBeasts ();
    List<Hero> deleteHero (int index);
    List<Beast> deleteBeast (int index);
}
