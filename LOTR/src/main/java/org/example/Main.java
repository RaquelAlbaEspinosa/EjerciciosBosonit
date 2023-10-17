package org.example;

import org.example.controller.WarService;
import org.example.controller.WarServiceImpl;
import org.example.view.Console;
import org.example.view.UIForm;

import javax.swing.*;
import java.awt.*;

public class Main {
    private JPanel panelMain;
    public static void main(String[] args) {
        WarService warService = new WarServiceImpl();
//        Console console = new Console(warService);
//        console.generateHero();
//        console.generateBeast();
        UIForm uiForm = new UIForm(warService);
        JFrame frame = new JFrame("Batalla por la tierra media");
        frame.setContentPane(uiForm.showPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}