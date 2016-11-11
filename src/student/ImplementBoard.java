/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

/**
 *
 * @author zyxie5
 */
import game.v2.Console;
import game.v2.Game;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JOptionPane;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

    import java.applet.Applet;                                                                                                                                                                                                                        
import java.applet.AudioClip;

public class ImplementBoard implements Board{
    
    int value_of_new_tile=0;
    int score = 0;
    int [][]grid = new int [4][4]; 
    int best_score = 0;
    int start = 0;
    int added = 0;
    int best_in_history = 0;
    int [][]store_merge = new int [4][4]; // get initialled in move()
    
    @Override
    public void InitialArray(){
        score = 0;
        for (int i=0; i<4; i++)
            for (int j=0; j<4; j++){
                grid[i][j] = 0;
               
            }
        
        int ith_position = ((int)(Math.random()*(16-1))+1)/4;
        int jth_position = ((int)(Math.random()*(16-1))+1)%4;
        grid[ith_position][jth_position] = 128;
        ith_position = ((int)(Math.random()*(16-1))+1)/4;
        jth_position = ((int)(Math.random()*(16-1))+1)%4;
        grid[ith_position][jth_position] = 2;
        ith_position = ((int)(Math.random()*(16-1))+1)/4;
        jth_position = ((int)(Math.random()*(16-1))+1)%4;
        grid[ith_position][jth_position] = 4;
        ith_position = ((int)(Math.random()*(16-1))+1)/4;
        jth_position = ((int)(Math.random()*(16-1))+1)%4;
        grid[ith_position][jth_position] = 4;
        ith_position = ((int)(Math.random()*(16-1))+1)/4;
        jth_position = ((int)(Math.random()*(16-1))+1)%4;
        grid[ith_position][jth_position] = 512;
        ith_position = ((int)(Math.random()*(16-1))+1)/4;
        jth_position = ((int)(Math.random()*(16-1))+1)%4;
        grid[ith_position][jth_position] = 1024;
        ith_position = ((int)(Math.random()*(16-1))+1)/4;
        jth_position = ((int)(Math.random()*(16-1))+1)%4;
        grid[ith_position][jth_position] = 512;
        ith_position = ((int)(Math.random()*(16-1))+1)/4;
        jth_position = ((int)(Math.random()*(16-1))+1)%4;
        grid[ith_position][jth_position] = 256;
        readHistory();
    }

    private Color Color(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * The four directions of move.
     */
    enum Direction {

        UP, DOWN, LEFT, RIGHT
    }

    /**
     * Check if the player has legal moves. There is no legal moves if there are
     * no empty spaces and no adjacent tiles with the same value.
     *
     * @return true if player has legal move; otherwise false
     */
    @Override
    public boolean hasLegalMove(){
        int sum = 0;
        int check = 0;
        for (int i=0; i<4; i++) //no empty spaces
            for (int j=0; j<4; j++) {
                if(toArray()[i][j] == 0)
                sum += 1;
            }
        for (int i=0; i<3; i++)  //no adjacent tiles with the same value
            for (int j=0; j<3; j++) {
                if(toArray()[i][j] == toArray()[i+1][j] || toArray()[i][j] == toArray()[i][j+1])
                    check += 1;
            }
        
        for (int i=0; i<3; i++)
         if (toArray()[i][3] == toArray()[i+1][3])
            check += 1;
        
        for (int j=0; j<3; j++)
            if (toArray()[3][j] == toArray()[3][j+1])
            check += 1;
        
        return !(sum == 0 && check == 0);
    }

    /**
     * Take a move of the tiles in the chosen direction. This call should lead
     * to a resultant grid of the merged tiles. No new tile is generated at this
     * call.
     *
     * @param dir the chosen direction (UP, DOWN, LEFT or RIGHT) of a move
     */
    @Override
    public void move(Board.Direction dir){   
        
        for (int i=0; i<4; i++)
            for (int j=0; j<4; j++){
                store_merge[i][j] = 0;
            }
        
        switch (dir){
            case LEFT: {
                for(int i=3;i>=0;i--){
                    if (toArray()[i][1]==0){
                    continue;
                    }
                    else if(toArray()[i][0]==0){
                        goLeft(i,1);
                    }
                    else if(toArray()[i][1]==toArray()[i][0]){
                        score += 2*toArray()[i][1];
                        added += 2*toArray()[i][1];
                        addTo(i,1,i,0);
                        store_merge[i][0] = 1;
                       
                    }
                }
                
                for(int i=3;i>=0;i--){
                    if(toArray()[i][2]==0){
                        continue;
                    }
                    else if(toArray()[i][1]==0){
                        goLeft(i,2);
                        if(toArray()[i][0]==0){
                            goLeft(i,1);
                        }
                        else if(toArray()[i][1]==toArray()[i][0]){
                            score += 2*toArray()[i][1];
                            added += 2*toArray()[i][1];
                            addTo(i,1,i,0);
                            store_merge[i][0] = 1;
                        }
                    }
                    else if (toArray()[i][2]==toArray()[i][1]){
                        score += 2*toArray()[i][1];
                        added += 2*toArray()[i][1];
                        addTo(i,2,i,1);
                        store_merge[i][1] = 1;
                    } 
                }
                
                for(int i=3;i>=0;i--){
                    if(toArray()[i][3]==0){
                        continue;
                    }
                    else if(toArray()[i][2]==0){
                        goLeft(i,3);
                        if (toArray()[i][1]==0){
                            goLeft(i,2);
                            if(toArray()[i][0] == 0){
                                goLeft(i,1);
                            }
                            else if(toArray()[i][1]==toArray()[i][0]){
                                score += 2*toArray()[i][1];
                                added += 2*toArray()[i][1];
                                addTo(i,1,i,0);
                                store_merge[i][0] = 1;
                            }
                        }
                        else if (toArray()[i][2]==toArray()[i][1]){
                            score += 2*toArray()[i][1];
                            added += 2*toArray()[i][1];
                            
                            addTo(i,2,i,1);
                            store_merge[i][1] = 1;
                        }    
                    }
                    else if (toArray()[i][3]==toArray()[i][2]){
                        score += 2*toArray()[i][2];
                        added += 2*toArray()[i][2];
                        addTo(i,3,i,2);
                        store_merge[i][2] = 1;
                    }  
                }
                break;
            }
            
            
            case RIGHT:{
                for(int i=0;i<4;i++){
                    if (toArray()[i][2]==0){
                    continue;
                    }
                    else if(toArray()[i][3]==0){
                        goRight(i,2);
                    }
                    else if(toArray()[i][2]==toArray()[i][3]){
                        score += 2*toArray()[i][2];
                        added += 2*toArray()[i][2];
                        addTo(i,2,i,3);
                        store_merge[i][3] = 1;
                    }
                }
                
                for(int i=0;i<4;i++){
                    if(toArray()[i][1]==0){
                        continue;
                    }
                    else if(toArray()[i][2]==0){
                        goRight(i,1);
                        if(toArray()[i][3]==0){
                            goRight(i,2);
                        }
                        else if(toArray()[i][2]==toArray()[i][3]){
                            score += 2*toArray()[i][2];
                            added += 2*toArray()[i][2];
                            addTo(i,2,i,3);
                            store_merge[i][3] = 1;
                        }
                    }
                    else if (toArray()[i][1]==toArray()[i][2]){
                        score += 2*toArray()[i][2];
                        added += 2*toArray()[i][2];
                        addTo(i,1,i,2);
                        store_merge[i][2] = 1;
                    } 
                }
                
                for(int i=0;i<4;i++){
                    if(toArray()[i][0]==0){
                        continue;
                    }
                    else if(toArray()[i][1]==0){
                        goRight(i,0);
                        if (toArray()[i][2]==0){
                            goRight(i,1);
                            if(toArray()[i][3] == 0){
                                goRight(i,2);
                            }
                            else if(toArray()[i][2]==toArray()[i][3]){
                                score += 2*toArray()[i][2];
                                added += 2*toArray()[i][2];
                                addTo(i,2,i,3);
                                store_merge[i][3] = 1;
                            }
                        }
                        else if (toArray()[i][1]==toArray()[i][2]){
                            score += 2*toArray()[i][2];
                            added += 2*toArray()[i][2];
                            addTo(i,1,i,2);
                            store_merge[i][2] = 1;
                        }    
                    }
                    else if (toArray()[i][0]==toArray()[i][1]){
                        score += 2*toArray()[i][1];
                        added += 2*toArray()[i][1];
                        addTo(i,0,i,1);
                        store_merge[i][1] = 1;
                    }  
                }
                break;
            }
            
            case UP:{
               for(int j=3;j>=0;j--){
                    if (toArray()[1][j]==0){
                    continue;
                    }
                    else if(toArray()[0][j]==0){
                        goUp(1,j);
                    }
                    else if(toArray()[1][j]==toArray()[0][j]){
                        score += 2*toArray()[1][j];
                        added += 2*toArray()[1][j];
                        addTo(1,j,0,j);
                        store_merge[0][j] = 1;
                    }
                }
                
                for(int j=3;j>=0;j--){
                    if(toArray()[2][j]==0){
                        continue;
                    }
                    else if(toArray()[1][j]==0){
                        goUp(2,j);
                        if(toArray()[0][j]==0){
                            goUp(1,j);
                        }
                        else if(toArray()[1][j]==toArray()[0][j]){
                            score += 2*toArray()[1][j];
                            added += 2*toArray()[1][j];
                            addTo(1,j,0,j);
                            store_merge[0][j] = 1;
                        }
                    }
                    else if (toArray()[2][j]==toArray()[1][j]){
                        score += 2*toArray()[1][j];
                        added += 2*toArray()[1][j];
                        addTo(2,j,1,j);
                        store_merge[1][j] = 1;
                    } 
                }
                
                for(int j=3;j>=0;j--){
                    if(toArray()[3][j]==0){
                        continue;
                    }
                    else if(toArray()[2][j]==0){
                        goUp(3,j);
                        if (toArray()[1][j]==0){
                            goUp(2,j);
                            if(toArray()[0][j] == 0){
                                goUp(1,j);
                            }
                            else if(toArray()[1][j]==toArray()[0][j]){
                                score += 2*toArray()[1][j];
                                added += 2*toArray()[1][j];
                                addTo(1,j,0,j);
                                store_merge[0][j] = 1;
                            }
                        }
                        else if (toArray()[2][j]==toArray()[1][j]){
                            score += 2*toArray()[1][j];
                            added += 2*toArray()[1][j];
                            addTo(2,j,1,j);
                            store_merge[1][j] = 1;
                        }    
                    }
                    else if (toArray()[3][j]==toArray()[2][j]){
                        score += 2*toArray()[2][j];
                        added += 2*toArray()[2][j];
                        addTo(3,j,2,j);
                        store_merge[2][j] = 1;
                    }  
                }
                break;
            }
            
            case DOWN:{
                for(int j=0;j<4;j++){
                    if (toArray()[2][j]==0){
                    continue;
                    }
                    else if(toArray()[3][j]==0){
                        goDown(2,j);
                    }
                    else if(toArray()[2][j]==toArray()[3][j]){
                        score += 2*toArray()[2][j];
                        added += 2*toArray()[2][j];
                        addTo(2,j,3,j);
                        store_merge[3][j] = 1;
                    }
                }
                
                for(int j=0;j<4;j++){
                    if(toArray()[1][j]==0){
                        continue;
                    }
                    else if(toArray()[2][j]==0){
                        goDown(1,j);
                        if(toArray()[3][j]==0){
                            goDown(2,j);
                        }
                        else if(toArray()[2][j]==toArray()[3][j]){
                            score += 2*toArray()[2][j];
                            added += 2*toArray()[2][j];
                            addTo(2,j,3,j);
                            store_merge[3][j] = 1;
                        }
                    }
                    else if (toArray()[1][j]==toArray()[2][j]){
                        score += 2*toArray()[2][j];
                        added += 2*toArray()[2][j];
                        addTo(1,j,2,j);
                        store_merge[2][j] = 1;
                    } 
                }
                
                for(int j=0;j<4;j++){
                    if(toArray()[0][j]==0){
                        continue;
                    }
                    else if(toArray()[1][j]==0){
                        goDown(0,j);
                        if (toArray()[2][j]==0){
                            goDown(1,j);
                            if(toArray()[3][j] == 0){
                                goDown(2,j);
                            }
                            else if(toArray()[2][j]==toArray()[3][j]){
                                score += 2*toArray()[2][j];
                                added += 2*toArray()[2][j];
                                addTo(2,j,3,j);
                                store_merge[3][j] = 1;
                            }
                        }
                        else if (toArray()[1][j]==toArray()[2][j]){
                            score += 2*toArray()[2][j];
                            added += 2*toArray()[2][j];
                            addTo(1,j,2,j);
                            store_merge[2][j] = 1;
                        }    
                    }
                    else if (toArray()[0][j]==toArray()[1][j]){
                        score += 2*toArray()[1][j];
                        added += 2*toArray()[1][j];
                        addTo(0,j,1,j);
                        store_merge[1][j] = 1;
                    }  
                }
            }
            
        }
    }

    /**
     * Check if the game is won. The game is won when a tile with a value of
     * 2048 appears on the board.
     *
     * @return true if the game is won; otherwise false
     */
    @Override
    public boolean isWon(){
        for (int i=0; i<4; i++) //no empty spaces
            for (int j=0; j<4; j++) {
                if (toArray()[i][j] == 2048) {
                    
                           return true;
                }
            }
        return false;
        
        
    }

    /**
     * Set the value of the new tile generated subsequently. Once the value is
     * set, all subsequent tiles will be generated with this value.
     *
     * This method could be used to lower the game difficulty by setting a
     * higher new tile value, which will lead to significantly less number of
     * moves required to finish the game.
     *
     * @param n the new value for the next and subsequent new tile. The value n
     * must be power of 2 and between 2 and 1024.
     */
    @Override
    public void setNewTileValue(int n){
        if (n%2==0)
            value_of_new_tile = n;
        else
            System.out.println("the set value needs to be power of 2!");
    }

    /**
     * Generate a new tile of the set value at a random empty position of the
     * grid.
     */
    @Override
    public void newTile(){
        int count = 0;
        for (int i=0; i<4; i++) //no empty spaces
            for (int j=0; j<4; j++) {
                if (toArray()[i][j] == 0)
                    count++;
            }
        int ith_position = (int)(Math.random()*(count-1))+1;
        int count2 = 0;
        for (int i=0; i<4; i++) //no empty spaces
            for (int j=0; j<4; j++) {
                if (toArray()[i][j] == 0){
                    count2++;
                    if (count2 == ith_position)
                        toArray()[i][j] = value_of_new_tile;
                }
            }
        
    }

    /**
     * Draw this board to game console. This call should further invoke the
     * Console's drawing methods to display the current state of itself on
     * screen, including the 4x4 grid, scoreboard and game messages.
     */
    @Override
    public void draw(){     
        
        for(int i=0; i<4;i++){
            for (int j=0; j<4; j++){
                String s = "" + toArray()[i][j];
                    
                    if(toArray()[i][j] == 2)
                    Console.getInstance().drawRectangle(25+110*j,185+110*i, 100, 100, new Color(0xeee4da), 20);
                    else if(toArray()[i][j] == 4)
                    Console.getInstance().drawRectangle(25+110*j,185+110*i, 100, 100, new Color(0xede0c8), 20);
                    else if(toArray()[i][j] == 8)
                    Console.getInstance().drawRectangle(25+110*j,185+110*i, 100, 100, new Color(0xf2b179), 20);
                    else if(toArray()[i][j] == 16)
                    Console.getInstance().drawRectangle(25+110*j,185+110*i, 100, 100, new Color(0xf59563), 20);
                    else if(toArray()[i][j] == 32)
                    Console.getInstance().drawRectangle(25+110*j,185+110*i, 100, 100, new Color(0xf67c5f), 20);
                    else if(toArray()[i][j] == 64)
                    Console.getInstance().drawRectangle(25+110*j,185+110*i, 100, 100, new Color(0xf65e3b), 20);
                    else if(toArray()[i][j] == 128)
                    Console.getInstance().drawRectangle(25+110*j,185+110*i, 100, 100, new Color(0xedcf72), 20);
                    else if(toArray()[i][j] == 256)
                    Console.getInstance().drawRectangle(25+110*j,185+110*i, 100, 100, new Color(0xedcc61), 20);
                    else if(toArray()[i][j] == 512)
                    Console.getInstance().drawRectangle(25+110*j,185+110*i, 100, 100, new Color(0xedc850), 20);
                    else if(toArray()[i][j] == 1024)
                    Console.getInstance().drawRectangle(25+110*j,185+110*i, 100, 100, new Color(0xedc53f), 20);
                    else if(toArray()[i][j] == 2048)
                    Console.getInstance().drawRectangle(25+110*j,185+110*i, 100, 100, new Color(0xedc22e), 20);
                    else if(toArray()[i][j] == 0){}
                    else 
                    Console.getInstance().drawRectangle(25+110*j,185+110*i, 100, 100, Color.YELLOW, 20);
                    
                 if (toArray()[i][j] > 0 && toArray()[i][j]<5)
                Console.getInstance().drawText(25+40+110*j,185+60+110*i, s , new Font("Arial", Font.BOLD, 40), Color.GRAY);
                 if (toArray()[i][j] == 8)
                Console.getInstance().drawText(25+40+110*j,185+60+110*i, s , new Font("Arial", Font.BOLD, 40), Color.WHITE);
                 if (toArray()[i][j] > 10 && toArray()[i][j]<100)
                Console.getInstance().drawText(14+40+110*j,185+60+110*i, s , new Font("Arial", Font.BOLD, 40), Color.WHITE);
                 if (toArray()[i][j] > 100 && toArray()[i][j]<1000)
                Console.getInstance().drawText(3+40+110*j,185+60+110*i, s , new Font("Arial", Font.BOLD, 40), Color.WHITE);
                 if (toArray()[i][j] > 1000)
                Console.getInstance().drawText(3-11+40+110*j,185+60+110*i, s , new Font("Arial", Font.BOLD, 40), Color.WHITE);
            }
        }
           
    }

    /**
     * Convert this board to a 4x4 integer 2D-array. The upper left corner of
     * the board is at array position [0,0]. Each tile is represented by an
     * integer value at the corresponding position of the array. An empty cell
     * on the board is represented by a zero value in the array.
     *
     * @return a 2D array representation of the 4x4 grid
     */
    @Override
    public int[][] toArray(){
        return grid;   
    }

    /**
     * Set tiles by a 4x4 integer 2D-array. Reset all tiles according to the
     * array representation.
     *
     * @param array an 2D array representation of the 4x4 grid
     */
    //void setArray(int[][] array);

    /**
     * Get player's current score.
     *
     * @return current score
     */
    @Override
    public int getScore(){
        return score;
    }

    /**
     * Set player's current score.
     *
     * @param score value of current score
     */
    @Override
    public void setScore(){
        String sco = "" + score;
        if (score < 100)
        Console.getInstance().drawText(270,82, sco , new Font("Arial", Font.BOLD, 30), Color.RED);
        else if (score > 100)
        Console.getInstance().drawText(250,82, sco , new Font("Arial", Font.BOLD, 30), Color.RED);
    }

    /**
     * Get player's best score.
     *
     * @return best score
     */
    @Override
    public int getBestScore(){
        return best_score;
    }

    /**
     * Set player's best score.
     *
     * @param bestScore value of best score
     */
    @Override
    public void setBestScore(int bestScore){
        if (best_score < score)
            best_score = score;
        String b_sco = "" + best_score;
        if (best_score < 100)
        Console.getInstance().drawText(400,82, b_sco , new Font("Arial", Font.BOLD, 30), Color.RED);
        else if (best_score > 100)
        Console.getInstance().drawText(380,82, b_sco , new Font("Arial", Font.BOLD, 30), Color.RED);
        
    }
    
    @Override
    public void addTo(int i, int j, int inew, int jnew){
        grid[inew][jnew] += grid[i][j];
        grid[i][j] = 0; 
    }
    
    @Override
    public void goRight(int i, int j){
        grid[i][j+1] = grid[i][j];
        grid[i][j] = 0;  
    }
    
    @Override
    public void goLeft(int i, int j){
        grid[i][j-1] = grid[i][j];
        grid[i][j] = 0;
    }
    
    @Override
    public void goDown(int i, int j){
        grid[i+1][j] = grid[i][j];
        grid[i][j] = 0;
    }
    
    @Override
    public void goUp(int i, int j){
        grid[i-1][j] = grid[i][j];
        grid[i][j] = 0;
    }
    
    /**
     *
     * @return
     */
    @Override
    public boolean start(){
        
        if(start ==0){
            start ++;
            return true;
        }
        else
            return false;
    }
    
    @Override
    public void score_added(){
        if (added != 0){
            String add = "+" + added;
            Console.getInstance().drawText(280,35, add , new Font("Arial", Font.BOLD, 20), Color.RED);
        }
    }
    
    @Override
    public void score_added_clear(){
        added = 0;
    }
    
    @Override
    public void howToPlay(){
        Console.getInstance().drawRectangle(30, 110, 60, 40, new Color(0xeee4da), 10)
                .drawText(35, 135, "Help", new Font("Arial", Font.BOLD, 22), Color.WHITE);
    }
    
    @Override
    public void musicButton(){
        Console.getInstance().drawRectangle(150, 110, 110, 40, new Color(0xeee4da), 10)
                .drawText(155, 135, "BGM SWITH", new Font("Arial", Font.BOLD, 17), Color.WHITE);
        
    }
    
    @Override
    public void instructionToPlay(){
        if (JOptionPane.showConfirmDialog(null, "2048 is played on a gray 4×4 grid, with numbered tiles that slide smoothly when a player moves them using the four arrow keys. "
              +'\n'  + "Every turn, a new tile will randomly appear in an empty spot on the board with a value of either 2 or 4. "
              +'\n'  + "Tiles slide as far as possible in the chosen direction until they are stopped by either another tile or the edge of the grid. "
              +'\n' + "If two tiles of the same number collide while moving, "
               + "they will merge into a tile with the total value of the two tiles that collided."
               +'\n' +  "And we believe our design can fulfill all the advanced requirement, enjoy it :) "
                + "                                                                                                                --Zhiyao, Aruhan", 
               "What is 2048 ", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                
            }
        
        
    }
    
    @Override
    public void setHistory(){
        if (best_in_history < best_score){
            FileWriter output = null;
            try {
                output = new FileWriter("best.txt");
                output.write(best_score);
                
            } catch (IOException ex) {
                Logger.getLogger(ImplementBoard.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    output.close();
                } catch (IOException ex) {
                    Logger.getLogger(ImplementBoard.class.getName()).log(Level.SEVERE, null, ex);
                }
            }   
        } 
        
    }
    
    @Override
    public void readHistory(){

        try {
            FileReader input;
            input = new FileReader("best.txt");
            
            try {
                /*
                if ((input.read()) != -1) {
                    best_in_history = input.read();
                }
                        */
                    best_in_history = input.read();
               
            } catch (IOException ex) {
                Logger.getLogger(ImplementBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (FileNotFoundException ex) {
            System.out.println("not found");

        }
        
        if (best_in_history > best_score){
            best_score = best_in_history;
        }
        
    }
    
    @Override
    public void drawLarger(){     
        int lgr = 110;
        
        for(int i=0; i<4;i++){
            for (int j=0; j<4; j++){
                if (store_merge[i][j] == 0){
                        String s = "" + toArray()[i][j];

                        if(toArray()[i][j] == 2)
                        Console.getInstance().drawRectangle(25+110*j,185+110*i, 100, 100, new Color(0xeee4da), 20);
                        else if(toArray()[i][j] == 4)
                        Console.getInstance().drawRectangle(25+110*j,185+110*i, 100, 100, new Color(0xede0c8), 20);
                        else if(toArray()[i][j] == 8)
                        Console.getInstance().drawRectangle(25+110*j,185+110*i, 100, 100, new Color(0xf2b179), 20);
                        else if(toArray()[i][j] == 16)
                        Console.getInstance().drawRectangle(25+110*j,185+110*i, 100, 100, new Color(0xf59563), 20);
                        else if(toArray()[i][j] == 32)
                        Console.getInstance().drawRectangle(25+110*j,185+110*i, 100, 100, new Color(0xf67c5f), 20);
                        else if(toArray()[i][j] == 64)
                        Console.getInstance().drawRectangle(25+110*j,185+110*i, 100, 100, new Color(0xf65e3b), 20);
                        else if(toArray()[i][j] == 128)
                        Console.getInstance().drawRectangle(25+110*j,185+110*i, 100, 100, new Color(0xedcf72), 20);
                        else if(toArray()[i][j] == 256)
                        Console.getInstance().drawRectangle(25+110*j,185+110*i, 100, 100, new Color(0xedcc61), 20);
                        else if(toArray()[i][j] == 512)
                        Console.getInstance().drawRectangle(25+110*j,185+110*i, 100, 100, new Color(0xedc850), 20);
                        else if(toArray()[i][j] == 1024)
                        Console.getInstance().drawRectangle(25+110*j,185+110*i, 100, 100, new Color(0xedc53f), 20);
                        else if(toArray()[i][j] == 2048)
                        Console.getInstance().drawRectangle(25+110*j,185+110*i, 100, 100, new Color(0xedc22e), 20);
                        else if(toArray()[i][j] == 0){}
                        else 
                        Console.getInstance().drawRectangle(25+110*j,185+110*i, 100, 100, Color.YELLOW, 20);

                     if (toArray()[i][j] > 0 && toArray()[i][j]<5)
                    Console.getInstance().drawText(25+40+110*j,185+60+110*i, s , new Font("Arial", Font.BOLD, 40), Color.GRAY);
                     if (toArray()[i][j] == 8)
                Console.getInstance().drawText(25+40+110*j,185+60+110*i, s , new Font("Arial", Font.BOLD, 40), Color.WHITE);
                     if (toArray()[i][j] > 10 && toArray()[i][j]<100)
                    Console.getInstance().drawText(14+40+110*j,185+60+110*i, s , new Font("Arial", Font.BOLD, 40), Color.WHITE);
                     if (toArray()[i][j] > 100 && toArray()[i][j]<1000)
                Console.getInstance().drawText(3+40+110*j,185+60+110*i, s , new Font("Arial", Font.BOLD, 40), Color.WHITE);
                 if (toArray()[i][j] > 1000)
                Console.getInstance().drawText(3-11+40+110*j,185+60+110*i, s , new Font("Arial", Font.BOLD, 40), Color.WHITE);
                }
                
                if (store_merge[i][j] == 1){
                        String s = "" + toArray()[i][j];

                        if(toArray()[i][j] == 2)
                        Console.getInstance().drawRectangle(25+110*j,185+110*i, lgr, lgr, new Color(0xeee4da), 20);
                        else if(toArray()[i][j] == 4)
                        Console.getInstance().drawRectangle(25+110*j,185+110*i, lgr, lgr, new Color(0xede0c8), 20);
                        else if(toArray()[i][j] == 8)
                        Console.getInstance().drawRectangle(25+110*j,185+110*i, lgr, lgr, new Color(0xf2b179), 20);
                        else if(toArray()[i][j] == 16)
                        Console.getInstance().drawRectangle(25+110*j,185+110*i, lgr, lgr, new Color(0xf59563), 20);
                        else if(toArray()[i][j] == 32)
                        Console.getInstance().drawRectangle(25+110*j,185+110*i, lgr, lgr, new Color(0xf67c5f), 20);
                        else if(toArray()[i][j] == 64)
                        Console.getInstance().drawRectangle(25+110*j,185+110*i, lgr, lgr, new Color(0xf65e3b), 20);
                        else if(toArray()[i][j] == 128)
                        Console.getInstance().drawRectangle(25+110*j,185+110*i, lgr, lgr, new Color(0xedcf72), 20);
                        else if(toArray()[i][j] == 256)
                        Console.getInstance().drawRectangle(25+110*j,185+110*i, lgr, lgr, new Color(0xedcc61), 20);
                        else if(toArray()[i][j] == 512)
                        Console.getInstance().drawRectangle(25+110*j,185+110*i, lgr, lgr, new Color(0xedc850), 20);
                        else if(toArray()[i][j] == 1024)
                        Console.getInstance().drawRectangle(25+110*j,185+110*i, lgr, lgr, new Color(0xedc53f), 20);
                        else if(toArray()[i][j] == 2048)
                        Console.getInstance().drawRectangle(25+110*j,185+110*i, lgr, lgr, new Color(0xedc22e), 20);
                        else if(toArray()[i][j] == 0){}
                        else 
                        Console.getInstance().drawRectangle(25+110*j,185+110*i, lgr, lgr, Color.YELLOW, 20);

                     if (toArray()[i][j] > 0 && toArray()[i][j]<5)
                    Console.getInstance().drawText(25+40+110*j,185+60+110*i, s , new Font("Arial", Font.BOLD, 40), Color.GRAY);
                     if (toArray()[i][j] == 8)
                    Console.getInstance().drawText(25+40+110*j,185+60+110*i, s , new Font("Arial", Font.BOLD, 40), Color.WHITE);
                     if (toArray()[i][j] > 10 && toArray()[i][j]<100)
                    Console.getInstance().drawText(14+40+110*j,185+60+110*i, s , new Font("Arial", Font.BOLD, 40), Color.WHITE);
                     if (toArray()[i][j] > 100 && toArray()[i][j]<1000)
                Console.getInstance().drawText(3+40+110*j,185+60+110*i, s , new Font("Arial", Font.BOLD, 40), Color.WHITE);
                 if (toArray()[i][j] > 1000)
                Console.getInstance().drawText(3-11+40+110*j,185+60+110*i, s , new Font("Arial", Font.BOLD, 40), Color.WHITE);
                }
                
            }
        }
           
    }
    
    
    @Override
    public void someMusic(){
        try {
            File file=new File("BGM.wav");
            URL url =file.toURL();
            AudioClip clip = Applet.newAudioClip(url);
            clip.loop();  
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(ImplementBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void mergeMusic(){
        int play_or_not = 0;
        for(int i=0; i<4;i++){
            for (int j=0; j<4; j++){
                if (store_merge[i][j] != 0){
                    play_or_not ++;
                }
            }
        }
        
        if (play_or_not >0){
            try {
                File file=new File("mergeMusic.wav");
                URL url =file.toURL();
                AudioClip clip = Applet.newAudioClip(url);
                clip.play();
            } catch (MalformedURLException ex) {
                Logger.getLogger(ImplementBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    @Override
    public void winMusic(){
        try {
                File file=new File("weWin2.wav");
                URL url =file.toURL();
                AudioClip clip = Applet.newAudioClip(url);
                clip.play();
            } catch (MalformedURLException ex) {
                Logger.getLogger(ImplementBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    @Override
    public void loseMusic(){
        try {
                File file=new File("weLose.wav");
                URL url =file.toURL();
                AudioClip clip = Applet.newAudioClip(url);
                clip.play();
            } catch (MalformedURLException ex) {
                Logger.getLogger(ImplementBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
   
}
