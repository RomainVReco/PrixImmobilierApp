package com.priximmo.userinput;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 */
public class GestionUser {

    Pattern p = Pattern.compile("[0-9]+[0-9]+[0-9]+[0-9]");
    Matcher m;

    public GestionUser() {
    }

    public String promptString(String prompt) {
        System.out.print(prompt+" : ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        return input;
    }

    public String promptYear() {
        Scanner scanner = new Scanner(System.in);
        String userInput = "";
        boolean validInput = false;
        do {
            String input = scanner.nextLine();
            m = p.matcher(input);
            if (input.length() == 4 && m.matches() && (Integer.parseInt(input)>=2010)) {
                userInput = input;
                validInput = true;
            } else {
                System.out.println("Année incorrecte");
            }
        } while (!validInput);

        return userInput;
    }

    public String promptSingleDigit(String prompt, int listSize) {
        Scanner scanner = new Scanner(System.in);
        String userInput = "";
        boolean validInput = false;
        do {
            System.out.print(prompt+" : ");
            String input = scanner.nextLine().toUpperCase();
            if (input.length() <= String.valueOf(listSize).length()) {
                userInput = input;
                validInput = true;
                try {
                    Integer.valueOf(userInput);
                } catch (NumberFormatException e) {
                    validInput = false;
                    System.out.println("Vous n'avez pas rentré un entier, veuiller effectuer une nouvelle saisie de 0 à "+listSize);
                }
            } else {
                System.out.println("L'entier renseigné hors périmètre. Faites un choix de 0 à "+listSize);
            }
        } while (!validInput);

        return userInput;
    }

    public String promptYesNo(String prompt) {
        Scanner scanner = new Scanner(System.in);
        String userInput = "";
        boolean validInput = false;
        do {
            System.out.print(prompt + " (O/N): ");
            String input = scanner.nextLine().toUpperCase();

            if (input.length() == 1 && (input.equals("O") || input.equals("N"))) {
                userInput = input;
                validInput = true;
            } else {
                System.out.println("Entrée invalide. Saisissez \"O\" pour Oui ou \"N\" pour Non");
            }
        } while (!validInput);

        return userInput;
    }

}
