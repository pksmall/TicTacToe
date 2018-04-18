package ru.geekbrains.homework08.controller;

import ru.geekbrains.homework08.model.GameFieldMap;
import java.util.Random;

public class GameLogic {
    private static Random random = new Random();
    private final int SIZE = 3;
    private static final int DOTS_TO_WIN = 3;
    private static final char DOT_X = 'X';
    private static final char DOT_O = 'O';
    private static final char DOT_EMPTY = '*';
    private char humanSymb = DOT_X;
    private char aiSymb = DOT_O;
    private char[][] map;
    private boolean who = true;

    public GameLogic(GameFieldMap gameMap){
        this.map = gameMap.getMap();
        initMap();
    }

    public String mainLoop(int clX, int clY) {
        String title = "TicTacToe - You Move";
        if (clX < SIZE && clY < SIZE && isCellValid(clX, clY) && !isMapFull()
                && !checkWin(humanSymb) && !checkWin(aiSymb)) {
            whoTurn(clX, clY);
            if (isMapFull()) title = "TicTacToe - Draw!";
            else {
                whoTurn(clX, clY);
                if (isMapFull()) title = "TicTacToe - Draw!";
            }
        } else {
            if (isMapFull()) title = "TicTacToe - Draw!";
            else {
                title = "TicTacToe - Choose Another Field!";
            }
        }
        if (checkWin(humanSymb) ) title = "TicTacToe - You Win!";
        else if (checkWin(aiSymb) ) {
            title = "TicTacToe - AI Win!";
        }
        return title;
    }

    public void whoTurn(int x, int y) {
        if (this.who) {
            humanTurn(x,y);
        } else {
            aiTurn();
        }
        // rotate move
        this.who = !this.who;
    }

    /*
     * human move
     */
    public void humanTurn(int x, int y) {
        System.out.println("Человек сделал свой ход в точку " + (x + 1) + " " + (y + 1));
        this.map[x][y] = humanSymb;
    }

    /*
     *TODO:
     * own class and smart moves.
     */
    public void aiTurn(){
        int x,y;
        do{
            x = random.nextInt(SIZE);
            y = random.nextInt(SIZE);
        }while(!isCellValid(x,y));
        System.out.println("Компьютер сделал свой ход в точку " + (x + 1) + " " + (y+1));
        map[x][y] = aiSymb;
    }

    public boolean isCellValid(int x, int y){
        //проверяем попадают ли введенные координаты в размер игрового поля
        if(x < 0 || x >= SIZE || y < 0 || y >= SIZE) return false;

        //проверяем, что там нет символов игрока
        if(map[x][y] == DOT_EMPTY) return true;

        return false;
    }

    public boolean isMapFull(){
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++) if(map[i][j] == DOT_EMPTY) return false;
        }
        return true;
    }

    public boolean checkWin(char symb){
        int winToD1 = 0,  winToD2 = 0;

        for (int i = 0; i < SIZE; i++) {
            int winToCol = 0, winToRow = 0;

            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == symb) winToRow++;
                if (map[j][i] == symb) winToCol++;
            }
            //System.out.println(" S: " + symb + " R: " + winToRow + " C: " + winToCol);

            // условия победы
            if (winToRow == DOTS_TO_WIN || winToCol == DOTS_TO_WIN) return true;

            // диоганали
            if (map[i][i] == symb) winToD1++;
            if (map[i][map[i].length-i-1] == symb) winToD2++;
        }

        //System.out.println(" S: " + symb + " D1: " + winToD1 + " D2: " + winToD2);
        // условия победы
        if (winToD1 == DOTS_TO_WIN || winToD2 == DOTS_TO_WIN) return  true;

        return false;
    }
    private void initMap() {
        map = new char[SIZE][SIZE];
        clearMap();
    }

    public char[][] getMap() {
        return map;
    }

    public int getSIZE() {
        return SIZE;
    }

    public void clearMap() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }

    // who move first
    public void setFirstMove(boolean guessWho) {
        this.who = guessWho;
        if (!guessWho) {
            humanSymb = DOT_O;
            aiSymb = DOT_X;
            whoTurn(0,0); // AI moves
        }
    }

    /*
     * debug info
     */
    public void printMap(){
        //Выводим шапку с подсказками координат
        for(int i = 0; i <= SIZE ; i++) System.out.print(i + " ");

        System.out.println();
        for(int i = 0; i < SIZE; i++){
            System.out.print(i+1 + " ");
            for(int j = 0; j < SIZE; j++) System.out.print(map[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }

}
