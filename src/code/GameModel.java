package code;// ADD YOUR IMPORTS HERE

/**
 * The class <b>GameModel</b> holds the model, the state of the systems. It
 * stores the followiung information: - the state of all the ``dots'' on the
 * board (color, captured or not) - the size of the board - the number of steps
 * since the last reset - the current color of selection
 *
 * The model provides all of this informations to the other classes trough
 * appropriate Getters. The controller can also update the model through
 * Setters. Finally, the model is also in charge of initializing the game
 *
 * author name : Junlin Luo (jluo006@uottawa.ca) student number : 7968201
 * Course: ITI 1121-D
 * Assingment: 3
 */
import java.io.Serializable;
import java.util.Random;

public class GameModel implements Cloneable,Serializable {

    /**
     * predefined values to capture the color of a DotInfo
     */
    public static final int COLOR_0 = 0;
    public static final int COLOR_1 = 1;
    public static final int COLOR_2 = 2;
    public static final int COLOR_3 = 3;
    public static final int COLOR_4 = 4;
    public static final int COLOR_5 = 5;
    public static final int NUMBER_OF_COLORS = 6;
    protected int size;
    protected DotInfo[][] List;
    private Random generator;
    private int CurrentSelectedColor;
    private int steps;
    private int movetype;
    private int playtype;

// ADD YOUR INSTANCE VARIABLES HERE
    /**
     * Constructor to initialize the model to a given size of board.
     *
     * @param size the size of the board
     */
    public GameModel(int size) {
        this.size = size;
        List = new DotInfo[size][size];
        generator = new Random();
        reset();
// ADD YOUR CODE HERE

    }

    public Object clone() {
        GameModel save = null;
        try {
            save = (GameModel) super.clone();
            save.List = new DotInfo[size][size];
            for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                DotInfo temp = (DotInfo)List[row][column].clone();
                save.List[row][column]=temp;
            }
        }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return save;
    }

    /**
     * Resets the model to (re)start a game. The previous game (if there is one)
     * is cleared up .
     */
    public void reset() {
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                List[row][column] = new DotInfo(row, column, generator.nextInt(NUMBER_OF_COLORS));
            }
        }
        movetype =0;
        playtype =0;
        steps = -1;
// ADD YOUR CODE HERE

    }

    /**
     * Getter method for the size of the game
     *
     * @return the value of the attribute sizeOfGame
     */
    public int getSize() {
        return size;

// ADD YOUR CODE HERE
    }

    /**
     * returns the current color of a given dot in the game
     *
     * @param i the x coordinate of the dot
     * @param j the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */
    public int getColor(int i, int j) {
        return List[i][j].getColor();

// ADD YOUR CODE HERE
    }

    /**
     * returns true is the dot is captured, false otherwise
     *
     * @param i the x coordinate of the dot
     * @param j the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */
    public boolean isCaptured(int i, int j) {
        return List[i][j].isCaptured();

// ADD YOUR CODE HERE
    }

    /**
     * Sets the status of the dot at coordinate (i,j) to captured
     *
     * @param i the x coordinate of the dot
     * @param j the y coordinate of the dot
     */
    public void capture(int i, int j) {
        List[i][j].setCaptured(true);

        // ADD YOUR CODE HERE
    }

    /**
     * Getter method for the current number of steps
     *
     * @return the current number of steps
     */
    public int getNumberOfSteps() {
        return steps;

// ADD YOUR CODE HERE
    }

    /**
     * Setter method for currentSelectedColor
     *
     * @param val the new value for currentSelectedColor
     */
    public void setCurrentSelectedColor(int val) {
        CurrentSelectedColor = val;

        step();

// ADD YOUR CODE HERE
    }

    /**
     * Getter method for currentSelectedColor
     *
     * @return currentSelectedColor
     */
    public int getCurrentSelectedColor() {
        return CurrentSelectedColor;

// ADD YOUR CODE HERE
    }

    /**
     * Getter method for the model's dotInfo reference at location (i,j)
     *
     * @param i the x coordinate of the dot
     * @param j the y coordinate of the dot
     *
     * @return model[i][j]
     */
    public DotInfo get(int i, int j) {
        DotInfo a = List[i][j];
        return a;

// ADD YOUR CODE HERE
    }

    /**
     * The metod <b>step</b> updates the number of steps. It must be called once
     * the model has been updated after the payer selected a new color.
     */
    public void step() {
        steps++;
        //System.out.println("1");
// ADD YOUR CODE HERE

    }

    /**
     * The metod <b>isFinished</b> returns true iff the game is finished, that
     * is, all the dats are captured.
     *
     * @return true if the game is finished, false otherwise
     */
    public boolean isFinished() {
        boolean result = true;
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                if (List[row][column].isCaptured() == false) {
                    result = false;
                }
            }
        }
        return result;
// ADD YOUR CODE HERE

    }
    public void setMovetype(int i){
        movetype = i;
    }
    
    public void setPlaytype(int i){
        playtype = i;
    }
    
    public int Movetype(){
        return movetype;
    }
    public int Playtype(){
        return playtype;
    }
    
    
    
    
    
    
    
    

    /**
     * Builds a String representation of the model
     *
     * @return String representation of the model
     */
    public String toString() {
        return "You created a " + size + " * " + size + " board.";

// ADD YOUR CODE HERE
    }
}
