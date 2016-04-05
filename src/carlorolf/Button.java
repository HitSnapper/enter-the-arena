package carlorolf;

import javax.swing.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Button extends VisibleObject
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
    }

    public Button(String text, int x, int y, int width, int height){
	super(x,y,"");
	actionList = new ArrayList<Action>();
    }

    public void addAction(Action action){
	actionList.add(action);
    }

    public void click(){
	for (Action action : actionList) {
	    action.notify();
	}
    }

}
