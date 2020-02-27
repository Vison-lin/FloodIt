package code;
/**
 * The class <b>GameController</b> is the controller of the game. It has a
 * method
 * <b>selectColor</b> which is called by the view when the player selects the
 * next color. It then computesthe next step of the game, and updates model and
 * view.
 *
 * author name : Junlin Luo (jluo006@uottawa.ca) student number : 7968201
 * Course: ITI 1121-D
 * Assingment: 3
 */
import java.io.*;
import javax.swing.*;
import java.awt.event.*;

public class GameController implements ActionListener, Serializable {

    // ADD YOUR INSTANCE VARIABLES HERE
    /**
     * Constructor used for initializing the controller. It creates the game's
     * view and the game's model instances
     *
     * @param size the size of the board on which the game will be played
     */
    private GameModel model;
    private int size;
    //private Elem<DotInfo> top;
    private int StackSize = 0;
    private GameView view;
    private int result = 3;
    private Stack<GameModel> SaveData = new GenericLinkedStack<GameModel>();
    private Stack<GameModel> LoadData = new GenericLinkedStack<GameModel>();
    private File SaveGame = new File("SaveGame.ser");

    public GameController(int size) {
        this.size = size;
        if (SaveGame.exists()) {
            try {
                ObjectInputStream o = new ObjectInputStream(new FileInputStream("SaveGame.ser"));
                model = (GameModel) o.readObject();
                o.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            view = new GameView(model, this);
            SaveGame.delete();
        } else {
            model = new GameModel(size);
            view = new GameView(model, this);
        }
        update();

// ADD YOUR CODE HERE
    }

    /**
     * resets the game
     */
    public void reset() {
        model.reset();
        checkSelect();

// ADD YOUR CODE HERE
    }

    /**
     * Callback used when the user clicks a button (reset or quit)
     *
     * @param e the ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Reset")) {
            reset();
            update();
        } else if (e.getActionCommand().equals("Quit")) {
            //System.exit(0);
            try {
                FileOutputStream f = new FileOutputStream("SaveGame.ser");
                ObjectOutputStream o = new ObjectOutputStream(f);
                o.writeObject(model);
                o.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            view.dispose();

        } else if (e.getActionCommand().equals("Undo")) {
            if (!SaveData.isEmpty()) {
                Undo();
                update();
            }

        } else if (e.getActionCommand().equals("Redo")) {
            if (!LoadData.isEmpty()) {
                Redo();
                update();
            }
        } else {
            DotButton cell;
            cell = (DotButton) e.getSource();
            if (model.getNumberOfSteps() == -1) {
                model.capture(cell.getRow(), cell.getColumn());
                restore();
                selectColor(cell.getColor());
            } else {
                restore();
                selectColor(cell.getColor());
            }
            update();

            if (model.isFinished() == true) {
                Icon icon = new ImageIcon(getClass().getResource("/data/java.png"));

                Object[] option = {"Quit", "Play Again"};
                result = JOptionPane.showOptionDialog(null, "Congruation! You win in " + model.getNumberOfSteps()
                        + " steps!\n Would you like to play again ?",
                        "                  YOU  WON ~~", JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE, icon, option, option[0]);
            }
            if (result == 0) {
                System.exit(0);
            } else if (result == 1) {
                reset();
                update();
                result = 3;
            }
        }

    }

    /**
     * <b>selectColor</b> is the method called when the user selects a new
     * color. If that color is not the currently selected one, then it applies
     * the logic of the game to capture possible locations. It then checks if
     * the game is finished, and if so, congratulates the player, showing the
     * number of moves, and gives two options: start a new game, or exit
     *
     * @param color the newly selected color
     */
    public void selectColor(int color) {
        model.step();
        Stack<DotInfo> dot = new GenericLinkedStack<DotInfo>();
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                if (model.isCaptured(row, column)) {;
                    dot.push(model.get(row, column));

                }
            }
        }
        while (!dot.isEmpty()) {
            DotInfo a, check;
            a = dot.pop();

            int x = a.getX();
            int y = a.getY();
            if (model.Movetype() == 0 && model.Playtype() == 0) {
                if (y - 1 >= 0) {

                    check = model.get(x, y - 1);
                    if (check.isCaptured() == false && check.getColor() == color) {
                        check.setCaptured(true);
                        dot.push(check);
                    }
                }
                if (y + 1 <= size - 1) {

                    check = model.get(x, y + 1);
                    if (check.isCaptured() == false && check.getColor() == color) {
                        check.setCaptured(true);
                        dot.push(check);
                    }
                }

                if (x - 1 >= 0) {

                    check = model.get(x - 1, y);
                    if (check.isCaptured() == false && check.getColor() == color) {
                        check.setCaptured(true);
                        dot.push(check);
                    }
                }
                if (x + 1 <= size - 1) {

                    check = model.get(x + 1, y);
                    if (check.isCaptured() == false && check.getColor() == color) {
                        check.setCaptured(true);
                        dot.push(check);
                    }
                }

            } else if (model.Movetype() == 1 && model.Playtype() == 0) {

                if (y - 1 >= 0) {

                    check = model.get(x, y - 1);
                    if (check.isCaptured() == false && check.getColor() == color) {
                        check.setCaptured(true);
                        dot.push(check);
                    }
                }
                if (y + 1 <= size - 1) {

                    check = model.get(x, y + 1);
                    if (check.isCaptured() == false && check.getColor() == color) {
                        check.setCaptured(true);
                        dot.push(check);
                    }
                }

                if (x - 1 >= 0) {

                    check = model.get(x - 1, y);
                    if (check.isCaptured() == false && check.getColor() == color) {
                        check.setCaptured(true);
                        dot.push(check);
                    }
                    if (y + 1 <= size - 1) {
                        check = model.get(x - 1, y + 1);
                        if (check.isCaptured() == false && check.getColor() == color) {
                            check.setCaptured(true);
                            dot.push(check);
                        }
                    }
                    if (y - 1 >= 0) {
                        check = model.get(x - 1, y - 1);
                        if (check.isCaptured() == false && check.getColor() == color) {
                            check.setCaptured(true);
                            dot.push(check);
                        }
                    }
                }
                if (x + 1 <= size - 1) {

                    check = model.get(x + 1, y);
                    if (check.isCaptured() == false && check.getColor() == color) {
                        check.setCaptured(true);
                        dot.push(check);
                    }
                    if (y + 1 <= size - 1) {
                        check = model.get(x + 1, y + 1);
                        if (check.isCaptured() == false && check.getColor() == color) {
                            check.setCaptured(true);
                            dot.push(check);
                        }
                    }
                    if (y - 1 >= 0) {
                        check = model.get(x + 1, y - 1);
                        if (check.isCaptured() == false && check.getColor() == color) {
                            check.setCaptured(true);
                            dot.push(check);
                        }
                    }
                }
            } else if (model.Movetype() == 0 && model.Playtype() == 1) {
                if (y - 1 >= 0) {
                    check = model.get(x, y - 1);
                    if (check.isCaptured() == false && check.getColor() == color) {
                        check.setCaptured(true);
                        dot.push(check);
                    }
                } else {
                    check = model.get(x, size - 1);
                    if (check.isCaptured() == false && check.getColor() == color) {
                        check.setCaptured(true);
                        dot.push(check);
                    }

                }

                if (y + 1 <= size - 1) {
                    check = model.get(x, y + 1);
                    if (check.isCaptured() == false && check.getColor() == color) {
                        check.setCaptured(true);
                        dot.push(check);
                    }
                } else {
                    check = model.get(x, 0);
                    if (check.isCaptured() == false && check.getColor() == color) {
                        check.setCaptured(true);
                        dot.push(check);
                    }
                }

                if (x - 1 >= 0) {

                    check = model.get(x - 1, y);
                    if (check.isCaptured() == false && check.getColor() == color) {
                        check.setCaptured(true);
                        dot.push(check);
                    }
                } else {
                    check = model.get(size - 1, y);
                    if (check.isCaptured() == false && check.getColor() == color) {
                        check.setCaptured(true);
                        dot.push(check);
                    }
                }
                if (x + 1 <= size - 1) {
                    check = model.get(x + 1, y);
                    if (check.isCaptured() == false && check.getColor() == color) {
                        check.setCaptured(true);
                        dot.push(check);
                    }
                } else {
                    check = model.get(0, y);
                    if (check.isCaptured() == false && check.getColor() == color) {
                        check.setCaptured(true);
                        dot.push(check);
                    }
                }
            } else if (model.Movetype() == 1 && model.Playtype() == 1) {
                if (y - 1 >= 0) {

                    check = model.get(x, y - 1);
                    if (check.isCaptured() == false && check.getColor() == color) {
                        check.setCaptured(true);
                        dot.push(check);
                    }
                } else {
                    check = model.get(x, size - 1);
                    if (check.isCaptured() == false && check.getColor() == color) {
                        check.setCaptured(true);
                        dot.push(check);
                    }

                }
                if (y + 1 <= size - 1) {

                    check = model.get(x, y + 1);
                    if (check.isCaptured() == false && check.getColor() == color) {
                        check.setCaptured(true);
                        dot.push(check);
                    }
                } else {
                    check = model.get(x, 0);
                    if (check.isCaptured() == false && check.getColor() == color) {
                        check.setCaptured(true);
                        dot.push(check);
                    }

                }

                if (x - 1 >= 0) {

                    check = model.get(x - 1, y);
                    if (check.isCaptured() == false && check.getColor() == color) {
                        check.setCaptured(true);
                        dot.push(check);
                    }
                    if (y + 1 <= size - 1) {
                        check = model.get(x - 1, y + 1);
                        if (check.isCaptured() == false && check.getColor() == color) {
                            check.setCaptured(true);
                            dot.push(check);
                        }
                    }
                    if (y - 1 >= 0) {
                        check = model.get(x - 1, y - 1);
                        if (check.isCaptured() == false && check.getColor() == color) {
                            check.setCaptured(true);
                            dot.push(check);
                        }
                    }
                } else {
                    check = model.get(size - 1, y);
                    if (check.isCaptured() == false && check.getColor() == color) {
                        check.setCaptured(true);
                        dot.push(check);
                    }

                }
                if (x + 1 <= size - 1) {

                    check = model.get(x + 1, y);
                    if (check.isCaptured() == false && check.getColor() == color) {
                        check.setCaptured(true);
                        dot.push(check);
                    }
                    if (y + 1 <= size - 1) {
                        check = model.get(x + 1, y + 1);
                        if (check.isCaptured() == false && check.getColor() == color) {
                            check.setCaptured(true);
                            dot.push(check);
                        }
                    }
                    if (y - 1 >= 0) {
                        check = model.get(x + 1, y - 1);
                        if (check.isCaptured() == false && check.getColor() == color) {
                            check.setCaptured(true);
                            dot.push(check);
                        }
                    }
                } else {
                    check = model.get(0, y);
                    if (check.isCaptured() == false && check.getColor() == color) {
                        check.setCaptured(true);
                        dot.push(check);
                    }

                }

            }
            for (int row = 0; row < size; row++) {
                for (int column = 0; column < size; column++) {
                    if (model.isCaptured(row, column)) {;
                        model.get(row, column).setColor(color);
                    }
                }
            }

        }

    }

    // ADD YOUR PRIVATE METHODS HERE
    private void update() {

        view.update(model);
    }

    private void restore() {
        GameModel saved = null;
        saved = (GameModel) model.clone();
        SaveData.push(saved);

    }

    private void Undo() {
        LoadData.push((GameModel) model.clone());
        GameModel saved = null;
        saved = (GameModel) SaveData.pop().clone();
        model = saved;
        checkSelect();
    }

    private void Redo() {
        SaveData.push((GameModel) model.clone());
        GameModel saved = null;
        saved = (GameModel) LoadData.pop().clone();
        model = saved;
        checkSelect();

    }

    private void checkSelect() {
        if (model.Movetype() == 0) {
            view.plane.setSelected(true);
        } else if (model.Movetype() == 1) {
            view.torus.setSelected(true);
        }
        if (model.Playtype() == 0) {
            view.Orthogonal.setSelected(true);
        } else if (model.Playtype() == 1) {
            view.Diagonal.setSelected(true);
        }
    }

}
