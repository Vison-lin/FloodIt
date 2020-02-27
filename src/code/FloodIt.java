package code;



/**
 * The class <b>FloodIt</b> launches the game
 *
 * author name : Junlin Luo (jluo006@uottawa.ca) student number : 7968201
 * Course: ITI 1121-D
 * Assingment: 3
 */
public class FloodIt {

    /**
     * <b>main</b> of the application. Creates the instance of GameController
     * and starts the game. If a game size (<12) is passed as parameter, it is
     * used as the board size. Otherwise, a default value is passed
     *
     * @param args command line parameters
     */
    public static void main(String[] args) {
        GameController control;

        int i = 12;
        if (args.length != 0) {
            if (i < 12) {
                i = 12;
            } else {
                i = Integer.parseInt(args[0]);
            }
        }

        control = new GameController(i);

// ADD YOUR CODE HERE
    }

}
