package ru.geekbrains.homework08.view;

import ru.geekbrains.homework08.controller.GameLogic;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class GameField extends JPanel {
    private int width;
    private int height;
    private int cellWidth;
    private int cellHeight;
    private JFrame mainFrame;
    private static Image xSymb;
    private static Image oSymb;
    // controller
    private GameLogic gameLogic;
    // model
    private int gameMapSize;
    private char[][] map;

    public GameField(JFrame mainFrame, GameLogic gameLogic) throws IOException {
        this.mainFrame = mainFrame;
        this.gameLogic = gameLogic;
        this.gameMapSize = gameLogic.getSIZE();
        this.map = gameLogic.getMap();

        width = getWidth();
        height = getHeight();

        xSymb = ImageIO.read(GameField.class.getResourceAsStream("../res/x-symb.png"));
        oSymb = ImageIO.read(GameField.class.getResourceAsStream("../res/o-symb2.jpg"));

        MouseAdapter mA = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e){
                String myTitle;
                //System.out.println(e.getX() + " " + e.getY());
                int clX = e.getX()/cellWidth;
                int clY = e.getY()/cellHeight;
                //System.out.println(clX + " " + clY);

                myTitle = gameLogic.mainLoop(clX, clY);
                mainFrame.setTitle(myTitle);

                //gameLogic.printMap();
                repaint();
            }
        };
        addMouseListener(mA);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        width = getWidth();
        height = getHeight();
        cellWidth = width/gameMapSize;
        cellHeight = height/gameMapSize;
        for (int i = 0; i < gameMapSize + 1; i++) {
            g.drawLine(0, i*cellHeight, width, i*cellHeight);   // горизонт
            g.drawLine(i*cellWidth, 0, i*cellWidth, height); // верикаль
        }
        paintMap(g);
    }

    private void paintMap(Graphics g) {
        for (int i = 0; i < gameMapSize; i++) {
            for (int j = 0; j < gameMapSize; j++) {
                if(map[i][j] == 'X') {
                    //g.setColor(Color.BLUE);
                    //g.fillOval(i*cellWidth+10,j*cellHeight+10, cellWidth-20, cellHeight-20);
                    g.drawImage(xSymb, i*cellWidth+10, j*cellHeight+10, cellWidth-20, cellHeight-20, null);
                }
                if(map[i][j] == 'O') {
                    //g.setColor(Color.RED);
                    //g.fillOval(i*cellWidth+10,j*cellHeight+10, cellWidth-20, cellHeight-20);
                    g.drawImage(oSymb, i*cellWidth+10, j*cellHeight+10, cellWidth-20, cellHeight-20, null);
                }
                if(map[i][j] == '*') {
                    g.clearRect(i*cellWidth+1,j*cellHeight+1, cellWidth-1, cellHeight-1);
                }
            }
        }
    }

    public void newGame() {
        Object[] options = {"I go first",
                "AI goes first",
                "No way, cancel!"};
        int n = JOptionPane.showOptionDialog(mainFrame,
                "Would do you like a new game? Who moves first?",
                "New Game?",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[2]);
        //System.out.println("N: " + n);
        boolean who = true;
        if (n==1) who = false;
        if (n != 2) {
            gameLogic.clearMap();
            gameLogic.setFirstMove(who);
            this.mainFrame.setTitle("TicTaToe - You Move!");
            revalidate();
            repaint();
        }
    }
}
