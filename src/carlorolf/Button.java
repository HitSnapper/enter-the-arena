
package carlorolf;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.MouseListener;
/*
public class Button extends VisibleObject implements MouseListener


{
    private int width, height;
    private String text;
    private List<Action> actionList;

    public int getWidth() {
	return width;
    }

    public int getHeight() {
	return height;
    }

    public String getText() {
	return text;
    }

    public void setWidth(final int width) {
	this.width = width;
    }

    public void setHeight(final int height) {
	this.height = height;
    }

    public void setText(final String text) {
	this.text = text;
    }

    public boolean underCursor(int cursorX, int cursorY){
	//check if inside button boundaries
	return true;
    }

    public Button(String text, int x, int y, int width, int height){
	super(x,y, Images.getImage("bajs.png"));
	actionList = new ArrayList<Action>();
    }

    public void addAction(Action action){
	actionList.add(action);
    }

    public void click(){
	if (underCursor(0, 0)) {
	    for (Action action : actionList) {
		action.notify();
	    }
	}
    }

    @Override public void mouseClicked(final MouseEvent e) {
	System.exit(0);
    }

    @Override public void mousePressed(final MouseEvent e) {

    }

    @Override public void mouseReleased(final MouseEvent e) {

    }

    @Override public void mouseEntered(final MouseEvent e) {

    }

    @Override public void mouseExited(final MouseEvent e) {

    }
}
*/