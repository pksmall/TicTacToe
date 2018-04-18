package ru.geekbrains.homework08;

import ru.geekbrains.homework08.view.TicTacToe;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                createAndShowGUI();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void createAndShowGUI() throws Exception {
        new TicTacToe();
    }
}
