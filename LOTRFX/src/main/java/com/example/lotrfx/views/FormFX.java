package com.example.lotrfx.views;

import com.example.lotrfx.controller.WarService;
import com.example.lotrfx.controller.WarServiceImpl;
import com.example.lotrfx.model.Beast;
import com.example.lotrfx.model.Hero;
import com.example.lotrfx.model.Race;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FormFX {
    @FXML
    public Button addHero;
    @FXML
    public TextField heroName;
    @FXML
    public ComboBox<String> heroRace = new ComboBox<>();;
    @FXML
    public Spinner<Integer> heroLifePoints;
    @FXML
    public Spinner<Integer> heroArmor;
    @FXML
    public ListView<String> heroList;
    @FXML
    public TextField beastName;
    @FXML
    public ComboBox<String> beastRace = new ComboBox<>();;
    @FXML
    public Spinner<Integer> beastLifePoints;
    @FXML
    public Spinner<Integer> beastArmor;
    @FXML
    public ListView<String> beastList;
    @FXML
    public Button fightButton;
    @FXML
    public TextArea fightText;
    @FXML
    public AnchorPane bgForm;
    @FXML
    public Pane fatherPane;
    public WarService warService;
    public List<Hero> heroes = new ArrayList<>();
    public List<Beast> beasts = new ArrayList<>();
    public Image backgroundImage;

    public FormFX () {
        if(warService == null){
            warService = new WarServiceImpl();
        }
    }

    @FXML
    public void initialize() {
        heroRace.getItems().addAll("Elfo", "Hobbit", "Humano");
        beastRace.getItems().addAll("Orco", "Trasgo");

        SpinnerValueFactory<Integer> valueFactoryHeroLifePoints =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 300, 10);
        heroLifePoints.setValueFactory(valueFactoryHeroLifePoints);
        heroLifePoints.setEditable(true);

        SpinnerValueFactory<Integer> valueFactoryHeroArmor =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 60, 1);
        heroArmor.setValueFactory(valueFactoryHeroArmor);
        heroArmor.setEditable(true);

        SpinnerValueFactory<Integer> valueFactoryBeastLifePoints =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 300, 10);
        beastLifePoints.setValueFactory(valueFactoryBeastLifePoints);
        beastLifePoints.setEditable(true);

        SpinnerValueFactory<Integer> valueFactoryBeastArmor =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 60, 1);
        beastArmor.setValueFactory(valueFactoryBeastArmor);
        beastArmor.setEditable(true);

        backgroundImage = new Image(Objects.requireNonNull(getClass().getResource("/team.gif")).toExternalForm());
        bgForm.setStyle("-fx-background-image: url('" + backgroundImage.getUrl() + "');" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-size: cover;");

        fightText.setStyle("-fx-alignment: center;");
    }
    @FXML
    public void onClickAddHero () {
        Race currentRace = null;
        if(heroRace.getValue().equals("Elfo")){
            currentRace = Race.ELF;
        } else if (heroRace.getValue().equals("Hobbit")){
            currentRace = Race.HOBBIT;
        } else if (heroRace.getValue().equals("Humano")){
            currentRace = Race.HUMAN;
        }
        Hero currentHero = new Hero(heroLifePoints.getValue(), heroName.getText(), currentRace, heroArmor.getValue());
        heroes.add(currentHero);
        heroList.getItems().add(currentHero.getName() + " - " + heroRace.getValue() + " (" +
                currentHero.getLifePoints() + ", " + currentHero.getArmor() + ")");
        warService.getAllHeroes().add(currentHero);
        backgroundImage = new Image(Objects.requireNonNull(getClass().getResource("/team.gif")).toExternalForm());
        bgForm.setStyle("-fx-background-image: url('" + backgroundImage.getUrl() + "');" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-size: cover;");
    }
    @FXML
    public void onClickAddBeast () {
        Race currentRace = null;
        if(beastRace.getValue().equals("Orco")){
            currentRace = Race.ORC;
        } else if (beastRace.getValue().equals("Trasgo")){
            currentRace = Race.GOBLIN;
        }
        Beast currentBeast = new Beast(beastLifePoints.getValue(), beastName.getText(), currentRace, beastArmor.getValue());
        beasts.add(currentBeast);
        beastList.getItems().add(currentBeast.getName() + " - " + beastRace.getValue() + " (" +
                currentBeast.getLifePoints() + ", " + currentBeast.getArmor() + ")");
        warService.getAllBeasts().add(currentBeast);
        backgroundImage = new Image(Objects.requireNonNull(getClass().getResource("/mordor.gif")).toExternalForm());
        bgForm.setStyle("-fx-background-image: url('" + backgroundImage.getUrl() + "');" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-size: cover;");
    }
    @FXML
    public void onClickUpHero () {
        int index = heroList.getSelectionModel().getSelectedIndex();
        heroList.getItems().setAll(warService.changeOrderHero(1, index).stream().map(currentHero -> {
            return currentHero.getName() + " - " + getStringRace(currentHero.getRace()) + " (" +
                    currentHero.getLifePoints() + ", " + currentHero.getArmor() + ")";
        }).toList());
        backgroundImage = new Image(Objects.requireNonNull(getClass().getResource("/team.gif")).toExternalForm());
        bgForm.setStyle("-fx-background-image: url('" + backgroundImage.getUrl() + "');" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-size: cover;");
    }
    @FXML
    public void onClickDownHero () {
        int index = heroList.getSelectionModel().getSelectedIndex();
        heroList.getItems().setAll(warService.changeOrderHero(-1, index).stream().map(currentHero -> {
            return currentHero.getName() + " - " + getStringRace(currentHero.getRace()) + " (" +
                    currentHero.getLifePoints() + ", " + currentHero.getArmor() + ")";
        }).toList());
        backgroundImage = new Image(Objects.requireNonNull(getClass().getResource("/team.gif")).toExternalForm());
        bgForm.setStyle("-fx-background-image: url('" + backgroundImage.getUrl() + "');" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-size: cover;");
    }
    @FXML
    public void onClickOutHero () {
        int index = heroList.getSelectionModel().getSelectedIndex();
        warService.deleteHero(index);
        heroList.getItems().remove(index);
        backgroundImage = new Image(Objects.requireNonNull(getClass().getResource("/team.gif")).toExternalForm());
        bgForm.setStyle("-fx-background-image: url('" + backgroundImage.getUrl() + "');" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-size: cover;");
    }
    @FXML
    public void onClickUpBeast () {
        int index = beastList.getSelectionModel().getSelectedIndex();
        beastList.getItems().setAll(warService.changeOrderBeast(1, index).stream().map(currentBeast -> {
            return currentBeast.getName() + " - " + getStringRace(currentBeast.getRace()) + " (" +
                    currentBeast.getLifePoints() + ", " + currentBeast.getArmor() + ")";
        }).toList());
        backgroundImage = new Image(Objects.requireNonNull(getClass().getResource("/mordor.gif")).toExternalForm());
        bgForm.setStyle("-fx-background-image: url('" + backgroundImage.getUrl() + "');" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-size: cover;");
    }
    @FXML
    public void onClickDownBeast () {
        int index = beastList.getSelectionModel().getSelectedIndex();
        beastList.getItems().setAll(warService.changeOrderBeast(-1, index).stream().map(currentBeast -> {
            return currentBeast.getName() + " - " + getStringRace(currentBeast.getRace()) + " (" +
                    currentBeast.getLifePoints() + ", " + currentBeast.getArmor() + ")";
        }).toList());
        backgroundImage = new Image(Objects.requireNonNull(getClass().getResource("/mordor.gif")).toExternalForm());
        bgForm.setStyle("-fx-background-image: url('" + backgroundImage.getUrl() + "');" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-size: cover;");
    }
    @FXML
    public void onClickOutBeast () {
        int index = beastList.getSelectionModel().getSelectedIndex();
        warService.deleteBeast(index);
        beastList.getItems().remove(index);
        backgroundImage = new Image(Objects.requireNonNull(getClass().getResource("/mordor.gif")).toExternalForm());
        bgForm.setStyle("-fx-background-image: url('" + backgroundImage.getUrl() + "');" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-size: cover;");
    }
    @FXML
    public void onClickFight () {
        fightText.deleteText(0, fightText.getLength());
        List<String> lineas = warService.forFrodo();
        Task<Void> animationTask = new Task<>() {
            @Override
            protected Void call() {
                for (String linea : lineas) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    Platform.runLater(() -> fightText.appendText(linea + "\n"));
                }
                return null;
            }
        };

        new Thread(animationTask).start();
        heroList.getItems().clear();
        beastList.getItems().clear();
        backgroundImage = new Image(Objects.requireNonNull(getClass().getResource("/horses.gif")).toExternalForm());
        bgForm.setStyle("-fx-background-image: url('" + backgroundImage.getUrl() + "');" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-size: cover;");
    }

    private String getStringRace (Race race) {
        if(race == Race.ELF) {
            return "Elfo";
        } else if (race == Race.HOBBIT) {
            return "Hobbit";
        } else if (race == Race.HUMAN) {
            return "Humano";
        } else if (race == Race.ORC) {
            return "Orco";
        } else if (race == Race.GOBLIN) {
            return "Trasgo";
        }
        return "ERROR: Sin raza";
    }
}
