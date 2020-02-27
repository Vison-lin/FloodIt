package code;// ADD YOUR IMPORTS HERE

/**
 * The class <b>GameView</b> provides the current view of the entire Game. It
 * extends
 * <b>JFrame</b> and lays out the actual game and two instances of JButton. The
 * action listener for the buttons is the controller.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameView extends JFrame implements ActionListener {

// ADD YOUR INSTANCE VARIABLES HERE
    private GameModel model;
    private GameController gameController;
    private int size;
    private int iconSize;
    public static final int ICON_SIZE_S = 0;
    public static final int ICON_SIZE_M = 1;
    public static final int ICON_SIZE_L = 2;
    private DotButton[][] cell;
    private JLabel input;
    private JPanel board;
    private Dialog d;
    private ButtonGroup buttongroup1 = new ButtonGroup();
    private ButtonGroup buttongroup2 = new ButtonGroup();
    protected JRadioButton plane;
    protected JRadioButton torus;
    protected JRadioButton Orthogonal;
    protected JRadioButton Diagonal;

    /**
     * Constructor used for initializing the Frame
     *
     * @param model the model of the game (already initialized)
     * @param gameController the controller
     */
    public GameView(GameModel model, GameController gameController) {
        super("FloodIt!");
        this.gameController = gameController;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(500, 100, 0, 0);
        size = model.getSize();
        //setSize(400,450);
        if (size <= 25) {
            iconSize = ICON_SIZE_M;
            //setSize(size*40,(size+1)*50);
        } else if (size > 25) {
            iconSize = ICON_SIZE_S;
            //setSize(400,450);
        }
        board = new JPanel();
        //board.setPreferredSize(new Dimension(size*40,size*40));
        board.setBackground(Color.WHITE);
        board.setLayout(new GridLayout(size, size));
        cell = new DotButton[size][size];
        //board.setBorder(BorderFactory.createEmptyBorder(1,1,0,1));
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                cell[row][column] = new DotButton(row, column, model.getColor(row, column), iconSize);
                board.add(cell[row][column]);
                cell[row][column].addActionListener(gameController);
            }
        }
        add(board, BorderLayout.CENTER);

        JPanel board1 = new JPanel();
        JButton Set = new JButton("Setting");
        JButton Undo = new JButton("Undo");
        JButton Redo = new JButton("Redo");
        board1.add(Undo);
        board1.add(Redo);
        board1.add(Set);
        Undo.addActionListener(gameController);
        Redo.addActionListener(gameController);
        Set.addActionListener(this);
        add(board1, BorderLayout.NORTH);

        JButton Reset = new JButton("Reset");
        Reset.addActionListener(gameController);
        JButton Quit = new JButton("Quit");
        Quit.addActionListener(gameController);

        input = new JLabel();
        JPanel board2 = new JPanel();

        board2.add(input);
        board2.add(Reset);
        board2.add(Quit);
        add(board2, BorderLayout.SOUTH);

        JLabel lab1 = new JLabel();
        lab1.setText(" Play on plane or torus ?");
        JLabel lab = new JLabel();
        lab.setText(" Diagonal moves ?");
        JButton okBut = new JButton("OK");
        d = new Dialog(this, "Message", true);
        d.setBounds(600, 200, 300, 300);
        plane = new JRadioButton("Plane");
        plane.setSelected(true);
        torus = new JRadioButton("Torus");
        Orthogonal = new JRadioButton("Orthogonal");
        Orthogonal.setSelected(true);
        Diagonal = new JRadioButton("Diagonal");
        JPanel a = new JPanel(new GridLayout(3, 1));
        JPanel b = new JPanel(new GridLayout(3, 1));
        JPanel c = new JPanel();
        a.add(lab1);
        a.add(plane);
        a.add(torus);
        buttongroup1.add(plane);
        buttongroup1.add(torus);
        b.add(lab);
        b.add(Orthogonal);
        b.add(Diagonal);
        c.add(okBut);
        buttongroup2.add(Orthogonal);
        buttongroup2.add(Diagonal);
        d.setLayout(new GridLayout(3, 1));
        d.add(a);
        d.add(b);
        d.add(c);
        okBut.addActionListener(this);
        plane.addActionListener(this);
        torus.addActionListener(this);
        Orthogonal.addActionListener(this);
        Diagonal.addActionListener(this);

        pack();
        setResizable(false);
        setVisible(true);

// ADD YOUR CODE HERE
    }

    /**
     * update the status of the board's DotButton instances based on the current
     * game model
     */
    public void update(GameModel a) {
        this.model = a;
        input.setText("Number of steps : " + Integer.toString(model.getNumberOfSteps()));
        if (model.getNumberOfSteps() == -1) {
            input.setText("Select initial dot");
        }
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                cell[row][column].setColor(model.getColor(row, column));
            }
        }
// ADD YOUR CODE HERE

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Setting")) {
            d.setVisible(true);
        } else if (e.getActionCommand().equals("OK")) {
            d.setVisible(false);
        } else if (e.getSource() instanceof JRadioButton) {
            JRadioButton temp = (JRadioButton) e.getSource();
            if (temp.isSelected()) {
                if (temp.getText() == "Torus") {
                    model.setPlaytype(1);
                } else if (temp.getText() == "Plane") {
                    model.setPlaytype(0);
                }else if (temp.getText() == "Orthogonal") {
                    model.setMovetype(0);
                } else if (temp.getText() == "Diagonal") {
                    model.setMovetype(1);
                }
            }
        }
    }

}
