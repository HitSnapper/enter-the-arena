package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import javafx.scene.input.KeyCode;

/**
 * Created by HitSnapper on 2016-05-16.
 */
public class Controls {
    private int up, down, right, left;
    private int hit;

    public Controls(int standardKeys){
        if (standardKeys == 0){
            up = Keys.UP;
            right = Keys.RIGHT;
            down = Keys.DOWN;
            left = Keys.LEFT;
            hit = Keys.SPACE;
        }
        else if (standardKeys == 1){
            up = Keys.W;
            right = Keys.D;
            down = Keys.S;
            left = Keys.A;
            hit = Keys.CTRL;
        }
    }

    public Controls(int up, int down, int right, int left, int hit) {
        this.up = up;
        this.down = down;
        this.right = right;
        this.left = left;
        this.hit = hit;
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }
}
