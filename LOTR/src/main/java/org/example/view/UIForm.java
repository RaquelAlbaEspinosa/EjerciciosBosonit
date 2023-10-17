package org.example.view;

import org.example.controller.WarService;
import org.example.model.Beast;
import org.example.model.Hero;
import org.example.model.Race;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UIForm extends JFrame implements ActionListener {
    private JPanel CreateHero;
    private JPanel CreateBeast;
    private JLabel heroes;
    private JLabel beasts;
    private JPanel Fight;
    private JLabel fightingHeroes;
    private JLabel fightingBeasts;
    private JButton heroUp;
    private JButton heroOut;
    private JButton heroDown;
    private JButton beastUp;
    private JButton beastOut;
    private JButton beastDown;
    private JButton fightButton;
    private JLabel heroName;
    private JLabel heroRace;
    private JLabel heroLifePoints;
    private JLabel heroArmor;
    private JTextField heroNameText;
    private JTextField heroLifePointsText;
    private JTextField heroArmorText;
    private JComboBox<String> heroRaceText;
    private JLabel beastName;
    private JLabel beastRace;
    private JLabel beastLifePoints;
    private JLabel beastArmor;
    private JTextField beastNameText;
    private JTextField beastLifePointsText;
    private JTextField beastArmorText;
    private JComboBox<String> beastRaceText;
    private JButton createHero;
    private JButton createBeast;
    private JList<String> fightingHeroesList;
    private JList<String> fightingBeastsList;
    private JTextArea fightText;
    private JPanel allForm;

    public WarService warService;

    public UIForm(WarService warService) {
        heroNameText.addActionListener(this);
        beastNameText.addActionListener(this);
        this.warService = warService;
        heroRaceText.addItem("Elfo");
        heroRaceText.addItem("Hobbit");
        heroRaceText.addItem("Humano");
        heroRaceText.addActionListener(this);

        beastRaceText.addItem("Orco");
        beastRaceText.addItem("Trasgo");
        beastRaceText.addActionListener(this);

        heroLifePointsText.addActionListener(this);
        beastLifePointsText.addActionListener(this);
        heroArmorText.addActionListener(this);
        beastArmorText.addActionListener(this);

        createHero.setVisible(true);
        createBeast.setVisible(true);
        createBeast.addActionListener(this);
        createHero.addActionListener(this);

        heroUp.addActionListener(this);
        heroDown.addActionListener(this);
        heroOut.addActionListener(this);
        beastUp.addActionListener(this);
        beastDown.addActionListener(this);
        beastOut.addActionListener(this);

        fightButton.addActionListener(this);
    }
    public JPanel showPanel () {
        return allForm;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<Hero> heroes = warService.getAllHeroes();
        List<Beast> beasts = warService.getAllBeasts();

        if (e.getSource()==createHero){
            String heroName = heroNameText.getText();
            int heroLifePoints = Integer.parseInt(heroLifePointsText.getText());
            int heroArmor = Integer.parseInt(heroArmorText.getText());
            String selectedRaceHero = Objects.requireNonNull(heroRaceText.getSelectedItem()).toString();
            Race heroRace = null;
            switch (selectedRaceHero) {
                case "Elfo" -> heroRace = Race.ELF;
                case "Hobbit" -> heroRace = Race.HOBBIT;
                case "Humano" -> heroRace = Race.HUMAN;
            }
            warService.addHero(new Hero(heroLifePoints, heroName, heroRace, heroArmor));
            List<String> listHeroes = fromObjectToStringHero(heroes);
            fightingHeroesList.setModel(getListModel(listHeroes));
            fightingHeroesList.updateUI();
        }
        if(e.getSource() == createBeast) {
            String beastName = beastNameText.getText();
            String selectedRaceBeast = Objects.requireNonNull(beastRaceText.getSelectedItem()).toString();
            Race beastRace = null;
            switch (selectedRaceBeast) {
                case "Orco" -> beastRace = Race.ORC;
                case "Trasgo" -> beastRace = Race.GOBLIN;
            }
            int beastLifePoints = Integer.parseInt(beastLifePointsText.getText());
            int beastArmor = Integer.parseInt(beastArmorText.getText());
            warService.addBeast(new Beast(beastLifePoints, beastName, beastRace, beastArmor));

            List<String> listBeasts = fromObjectToStringBeast(beasts);
            fightingBeastsList.setModel(getListModel(listBeasts));
            fightingBeastsList.updateUI();
        };

        if(e.getSource() == heroUp) {
            warService.changeOrderHero(1, fightingHeroesList.getSelectedIndex());
            List<String> listHeroes = fromObjectToStringHero(heroes);
            fightingHeroesList.setModel(getListModel(listHeroes));
            fightingHeroesList.updateUI();
        };
        if(e.getSource() == heroDown) {
            warService.changeOrderHero(-1, fightingHeroesList.getSelectedIndex());
            List<String> listHeroes = fromObjectToStringHero(heroes);
            fightingHeroesList.setModel(getListModel(listHeroes));
            fightingHeroesList.updateUI();
        };
        if(e.getSource() == heroOut) {
            warService.deleteHero(fightingHeroesList.getSelectedIndex());
            List<String> listHeroes = fromObjectToStringHero(heroes);
            fightingHeroesList.setModel(getListModel(listHeroes));
            fightingHeroesList.updateUI();
        };
        if(e.getSource() == beastUp) {
            warService.changeOrderBeast(1, fightingBeastsList.getSelectedIndex());
            List<String> listBeasts = fromObjectToStringBeast(beasts);
            fightingBeastsList.setModel(getListModel(listBeasts));
            fightingBeastsList.updateUI();
        };
        if(e.getSource() == beastDown) {
            warService.changeOrderBeast(-1, fightingBeastsList.getSelectedIndex());
            List<String> listBeasts = fromObjectToStringBeast(beasts);
            DefaultListModel<String> beastListModel = new DefaultListModel<>();
            for (String beastInfo : listBeasts) {
                beastListModel.addElement(beastInfo);
            }
            fightingBeastsList.setModel(beastListModel);
            fightingBeastsList.updateUI();
        };
        if(e.getSource() == beastOut){
            warService.deleteBeast(fightingBeastsList.getSelectedIndex());
            List<String> listBeasts = fromObjectToStringBeast(beasts);
            fightingBeastsList.setModel(getListModel(listBeasts));
            fightingBeastsList.updateUI();
        };

        if(e.getSource() == fightButton) {
            List<String> fight = warService.forFrodo();
            SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
                @Override
                protected Void doInBackground() throws Exception {
                    for (String s : fight) {
                        publish(s);
                        Thread.sleep(1000);
                    }
                    return null;
                }
                @Override
                protected void process(List<String> chunks) {
                    for (String chunk : chunks) {
                        fightText.append(chunk + "\n");
                    }
                }
            };
            worker.execute();
        };
    }
    private List<String> fromObjectToStringHero (List<Hero> lista) {
        List<String> listHeroes =  new ArrayList<>();
        for (Hero hero : lista) {
            String currentHeroRace = "";
            switch (hero.getRace()) {
                case ELF -> currentHeroRace = "Elfo";
                case HOBBIT -> currentHeroRace = "Hobbit";
                case HUMAN -> currentHeroRace = "Humano";
            }
            listHeroes.add( hero.getName() + " - " + currentHeroRace + "(" + hero.getLifePoints() +
                    ", " + hero.getArmor() + ")");
        }
        return listHeroes;
    }
    private List<String> fromObjectToStringBeast (List<Beast> lista){
        List<String> listBeasts =  new ArrayList<>();
        for (Beast beast : lista) {
            String currentBeastRace = "";
            switch (beast.getRace()) {
                case ORC -> currentBeastRace = "Orco";
                case GOBLIN -> currentBeastRace = "Trasgo";
            }
            listBeasts.add(beast.getName() + " - " + currentBeastRace + "(" + beast.getLifePoints() +
                    ", " + beast.getArmor() + ")");
        }
        return listBeasts;
    }

    private DefaultListModel<String> getListModel(List<String> lista){
        DefaultListModel<String> beastListModel = new DefaultListModel<>();
        for (String beastInfo : lista) {
            beastListModel.addElement(beastInfo);
        }
        return beastListModel;
    }
}
