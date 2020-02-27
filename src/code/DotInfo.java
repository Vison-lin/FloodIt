package code;

import java.io.Serializable;

/**
 * The class <b>DotInfo</b> is a simple helper class to store the initial color and state
 * (captured or not) at the dot position (x,y)
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */

public class DotInfo implements Cloneable,Serializable {


// ADD YOUR INSTANCE VARIABLES HERE
    protected int x;
    protected int y;
    protected int color;
    protected boolean captured;


    /**
     * Constructor 
     * 
     * @param x
     *            the x coordinate
     * @param y
     *            the y coordinate
     * @param color
     *            the initial color
     */
    public DotInfo(int x, int y, int color){
        this.x= x;
        this.y= y;
        this.color = color;
        

// ADD YOUR CODE HERE

    }

    /**
     * Getter method for the attribute x.
     * 
     * @return the value of the attribute x
     */
    public int getX(){
        return x;
// ADD YOUR CODE HERE

    }
    
    /**
     * Getter method for the attribute y.
     * 
     * @return the value of the attribute y
     */
    public int getY(){
        return y;

// ADD YOUR CODE HERE

    }
    
 
    /**
     * Setter for captured
     * @param captured
     *            the new value for captured
     */
    public void setCaptured(boolean captured) {
        this.captured = captured;

// ADD YOUR CODE HERE

    }

    /**
     * Get for captured
     *
     * @return captured
     */
    public boolean isCaptured(){
        return captured;

// ADD YOUR CODE HERE

    }

    /**
     * Get for color
     *
     * @return color
     */
    public int getColor() {
        return color;

// ADD YOUR CODE HERE

    }
    public void setColor(int value){
        this.color = value;
    }
    public String toString(){
        return ""+x+","+y+","+color;
    }
    public Object clone() {
        
        DotInfo save =null;
        try{
        save=(DotInfo) super.clone();
        }catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return save;
    }
 }