package main;

import userInterface.MainMenu;

public class Main {
    static MainMenu mainMenu;
    public static void main(String[] args) {

        mainMenu= new MainMenu();
        mainMenu.mainMenu();
    }
}