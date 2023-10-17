package org.example.view;

import org.example.controller.WarService;
import org.example.model.Beast;
import org.example.model.Hero;
import org.example.model.Race;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Console {
    public WarService warService;

    public Console(WarService warService) {
        this.warService = warService;
    }

    public void generateHero () {
        Scanner scanner = new Scanner(System.in);
        System.out.println("😀😀😀 Bienvenid@ a la Batalla de Morannon 😀😀😀\n" +
                "A continuación vamos a crear a nuestros héroes.\n");

        while (true) {
            int raceNumber = 0;
            Race race = null;
            while (true) {
                try {
                    System.out.println("Elige una raza:\n 1.Elfo   2.Hobbit   3.Humano\n" +
                            "Escriba el número de la raza.");
                    raceNumber = scanner.nextInt();
                    scanner.nextLine();
                    while (raceNumber != 1 && raceNumber != 2 && raceNumber != 3) {
                        System.out.println("No seas listo. El número debe ser uno de los mostrados anteriormente.\n" +
                                "Elige sabiamente ente 1.Elfo   2.Hobbit   3.Humano:");
                        raceNumber = scanner.nextInt();
                    }
                    if (raceNumber == 1) {
                        race = Race.ELF;
                    } else if (raceNumber == 2) {
                        race = Race.HOBBIT;
                    } else if (raceNumber == 3) {
                        race = Race.HUMAN;
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Debes ingresar un número.");
                    scanner.nextLine();
                }
            }

            System.out.println("¡Muy bien! Ahora vamos a darle un nombre.\n" +
                    "Escriba el nombre de su héroe:");
            String name = scanner.nextLine();


            int lifePoints = 0;
            int armor = 0;

            while (true) {
                try {
                    System.out.println("Perfecto. Ahora solo queda darle puntos de vida...\n");
                    while(lifePoints < 10 || lifePoints > 300) {
                        System.out.println("Escriba un número entero que esté entre 10 y 300:");
                        lifePoints = scanner.nextInt();
                        if (lifePoints < 10 || lifePoints > 300) {
                            System.out.println("Números entre 10 y 300, por favor.");
                        }
                    }
                    while (armor < 10 || armor > 60) {
                        System.out.println("Y, por último, los puntos de armadura. Elige un número entre 10 y 60.");
                        armor = scanner.nextInt();
                        if (armor < 10 || armor > 60) {
                            System.out.println("Números entre 10 y 60, por favor.");
                        }
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Debes ingresar un número válido.");
                    scanner.nextLine();
                }
            }

            warService.addHero(new Hero(lifePoints, name, race, armor));

            System.out.println("¡Genial! Tu héroe ha sido añadido a las filas que lucharán contra el ejército de Sauron.\n" +
                    "¿Quieres seguir añadiendo héroes o vamos a por las bestias?\n" +
                    "1.Sigamos con los héroes   2.Vamos a por las bestias");

            int choice = scanner.nextInt();
            while (choice != 1 && choice != 2) {
                System.out.println("Estamos graciosillos, anda, elige un número de los dos.\n" +
                        "1.Sigamos con los héroes   2.Vamos a por las bestias");
                choice = scanner.nextInt();
            }
            if (choice != 1) {
                break;
            }
        }
    }

    public void generateBeast () {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bien, ahora que tenemos a los héroes listos para la batalla, las bestias no se deben quedar atrás.\n");
        while (true) {
            int raceNumber = 0;
            Race race = null;
            while (true) {
                try {
                    System.out.println("Primero, vamos a elegir una raza.\n" +
                            "1.Orco   2.Trasgo(Goblin)");
                    raceNumber = scanner.nextInt();
                    scanner.nextLine();
                    while (raceNumber != 1 && raceNumber != 2) {
                        System.out.println("No puedes elegir un número que no sea 1 o 2. Prueba de nuevo.\n" +
                                "1.Orco   2.Trasgo(Goblin)");
                        raceNumber = scanner.nextInt();
                    }
                    if (raceNumber == 1) {
                        race = Race.ORC;
                    } else if (raceNumber == 2) {
                        race = Race.GOBLIN;
                    }
                    break;
                } catch (InputMismatchException e){
                    System.out.println("Entrada inválida. Debes ingresar un número válido.");
                    scanner.nextLine();
                }
            }

            System.out.println("Muy bien. Ahora hay que ponerle nombre a tu Bestia.\n" +
                    "Escribe el nombre a continuación:");
            String name = scanner.nextLine();

            int lifePoints = 0;
            int armor = 0;
            while (true) {
                try {
                        System.out.println("Perfecto. Como ya sabes solo quedan 2 pasos más.\n");
                    while (lifePoints < 10 || lifePoints > 300) {
                        System.out.println("Escribe a continuación sus puntos de vida (un número entero entre 10 y 300):");
                        lifePoints = scanner.nextInt();
                        if (lifePoints < 10 || lifePoints > 300) {
                            System.out.println("Números entre 10 y 300, por favor.");
                        }
                    }
                    while (armor < 10 || armor > 60) {
                        System.out.println("Y, por último, sus puntos de aramdura (un número entre 10 y 60):");
                        armor = scanner.nextInt();
                        if(armor < 10 || armor > 60) {
                            System.out.println("Números entre 10 y 60, por favor.");
                        }
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Debes ingresar un número válido.");
                    scanner.nextLine();
                }
            }

            warService.addBeast(new Beast(lifePoints, name, race, armor));

            System.out.println("¡Estupendo! Ya hemos agregado tu bestia a las filas del ejército que luchará por Sauron.\n" +
                    "¿Quieres seguir añadiendo bestias sedientas de sangre de los héroes de la Tierra Media o prefieres que comience la batalla?\n" +
                    "1.Quiero seguir añadiendo bestias   2.¡Que empiece la guerra!");
            int choice = scanner.nextInt();

            while (choice != 1 && choice != 2) {
                System.out.println("Estamos graciosillos, anda, elige un número de los dos.\n" +
                        "1.Sigamos con los héroes   2.Vamos a por las bestias");
                choice = scanner.nextInt();
            }

            if (choice != 1) {
                break;
            }
        }
        warService.forFrodo();
    }
}
