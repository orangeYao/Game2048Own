/*
 * Date: 
 * Student Name:
 * Student ID:
 * 
 */
package student;

import game.v2.Console;
import game.v2.Game;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

/**
 * Game skeleton of 2048
 *
 * @author Van
 */
public class My2048 extends Game {
    int count2 =0;
    int count = 0;
    int count_for_cycle = 0;
    int key_get_pressed = 0;
    int new_game = 0;
    int music = 1;
    int already_won = 0;
    BackgroundMusic BGM = new BackgroundMusic();

    /*
     You can declare any data fields here for your game as usual.
     */
    private Board board = new ImplementBoard();        // initialize it with your own implementation

    /*
     Main method
     */
    public static void main(String[] args) {

        /*
         Customize the console window per your need but do not show it yet.
         */
        Console.getInstance()
                .setTitle("Game 2048")
                .setWidth(480)
                .setHeight(640)
                .setTheme(Console.Theme.LIGHT);

        /*
         Similar to the Console class, use the chaining setters to configure the game. Call start() at the end of
         the chain to start the game loop.
         */
        new My2048()
                .setFps(50) // set frame rate
                .setShowFps(true) // set to display fps on screen
                .setBackground(Console.loadImage("/assets/board.png")) // set background image
                .start();                                               // start game loop
    }

    /**
     * ********************************************************************
     * protected void cycle() { if(board.start()){ board.setScore();
     * board.InitialArray(); }
     *
     * board.draw(); board.setScore(); // Uncomment this line when the board is
     * initialized with your implemention of Board interface
     * board.setNewTileValue(8);************************** There are three
     * abstract methods must be overriden: protected abstract void cycle();
     * protected abstract void keyPressed(KeyEvent e); protected abstract void
     * mouseClicked(MouseEvent e);
     */
    @Override
    protected void cycle() {

        if (music == 0) {
            BGM.loop();
        }
        music--;
        board.howToPlay();
        board.musicButton();

        if (board.start()) {
            //board.readHistory();
            board.InitialArray();

        }

        board.setHistory();

        if (new_game > 0) {
            board.InitialArray();
            new_game = 0;

        }

        board.setScore();
        board.setBestScore(1000);//I don't know the meaning of 1000, wo luan xie de
        // Uncomment this line when the board is initialized with your implemention of Board interface
        board.setNewTileValue(2);
        
        

        if (key_get_pressed > 0) {
            board.newTile();
            //board.score_added_clear();
            key_get_pressed = 0;
            count = 15;
            
            
        }
        

        //count_for_cycle = ++count_for_cycle%20;
        count--;
        if (count > 0) {
            board.score_added();
        }

        if (count == 0) {

            board.score_added_clear();
        }

        if (count == 14) {
            board.mergeMusic();
        }

        if (count > 8) {
            board.drawLarger();
        }
        if (count < 9) {
            board.draw();
        }
        
        if (count < 7) {
        if (!board.hasLegalMove()) {
                board.loseMusic();
                Object[] options = {"Hehe, I don't like it", "Try again!!"};
                if (JOptionPane.showOptionDialog(null, "You lose the game", "", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]) == JOptionPane.OK_OPTION) {
                    Console.getInstance().close();
                }
                Console.getInstance().clear().update();
                board.InitialArray();
            }
            
        }
        
        if (already_won == 0) {
            if (board.isWon()) {
                board.winMusic();
                Object[] options = {"So easy, I want to exit", "Interesting, I want to go on!!!"};
                if (JOptionPane.showOptionDialog(null, "Congratulation! You win the game!", "", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]) == JOptionPane.OK_OPTION) {
                    Console.getInstance().close();
                }
                already_won++;
                Console.getInstance().clear().update();
            }
        }
    }

    @Override
    protected void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                board.move(Board.Direction.LEFT);

                key_get_pressed++;
                break;
            case KeyEvent.VK_RIGHT:
                board.move(Board.Direction.RIGHT);

                key_get_pressed++;
                break;
            case KeyEvent.VK_UP:
                board.move(Board.Direction.UP);

                key_get_pressed++;
                break;
            case KeyEvent.VK_DOWN:
                board.move(Board.Direction.DOWN);

                key_get_pressed++;
                break;
        }
    }

    @Override
    protected void mouseClicked(MouseEvent e) {
        
        System.out.println("Click on (" + e.getX() + "," + e.getY() + ")");

        if (e.getX() > 354 && e.getX() < 464 && e.getY() > 108 && e.getY() < 146) {
            new_game++;
        }

        if (e.getX() > 30 && e.getX() < 90 && e.getY() > 110 && e.getY() < 150) {
            board.instructionToPlay();
        }
        
        if (e.getX() > 150 && e.getX() < 260 && e.getY() > 110 && e.getY() < 150) {
            count2++;
            if (count2%2 == 1)
                BGM.stop();
            if (count2%2 == 0){
                System.out.println("balka");
                BGM.loop();
            }
        }
        
    }



}
