/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

/**
 * Abstraction of the 2048 game board.
 *
 * @author Van
 */
public interface Board {
    
    
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
    boolean hasLegalMove();

    /**
     * Take a move of the tiles in the chosen direction. This call should lead
     * to a resultant grid of the merged tiles. No new tile is generated at this
     * call.
     *
     * @param dir the chosen direction (UP, DOWN, LEFT or RIGHT) of a move
     */
    void move(Direction dir);

    /**
     * Check if the game is won. The game is won when a tile with a value of
     * 2048 appears on the board.
     *
     * @return true if the game is won; otherwise false
     */
    boolean isWon();

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
    void setNewTileValue(int n);

    /**
     * Generate a new tile of the set value at a random empty position of the
     * grid.
     */
    void newTile();

    /**
     * Draw this board to game console. This call should further invoke the
     * console's drawing methods to display the current state of itself on
     * screen, including the 4x4 grid, scoreboard and game messages.
     */
    void draw();

    /**
     * Convert this board to a 4x4 integer 2D-array. The upper left corner of
     * the board is at array position [0,0]. Each tile is represented by an
     * integer value at the corresponding position of the array. An empty cell
     * on the board is represented by a zero value in the array.
     *
     * @return a 2D array representation of the 4x4 grid
     */
    int[][] toArray();
    void InitialArray();

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
    int getScore();

    /**
     * Set player's current score.
     *
     * @param score value of current score
     */
    void setScore();

    /**
     * Get player's best score.
     *
     * @return best score
     */
    int getBestScore();

    /**
     * Set player's best score.
     *
     * @param bestScore value of best score
     */
    void setBestScore(int bestScore);
    
     void addTo(int i, int j, int inew, int jnew);
     void goRight(int i, int j);
     void goLeft(int i, int j);
     void goDown(int i, int j);
     void goUp(int i, int j);
     boolean start();
     void score_added();
     void score_added_clear();
     void howToPlay();   
     void instructionToPlay();
     void setHistory();
     void readHistory();
     void drawLarger();
     void someMusic();
     void mergeMusic();
     void winMusic();
     void loseMusic();
     void musicButton();
}
