package tictactoe;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Square extends Canvas {

    /**
     * A Square is responsible for the display of a single place on the tictactoe grid. It extends a canvas and shows
     * a white square upon construction. It can draw a cross and circle on its place, and can cross itself out
     * vertically, horizontally and diagonally
     */

    private GraphicsContext gc;
    private int column;
    private int row;

    /**
     * New Square
     * @param width width of this square
     * @param height height of this square
     * @param column the column of the grid the square belongs on
     * @param row the row of the grid the square belongs on
     */
    public Square(int width, int height, int column, int row) {
        super(width, height);
        this.column = column;
        this.row = row;
        gc = getGraphicsContext2D();

        // Draw a square on the border of the canvas
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(10);
        gc.strokeRect(0, 0, width, height);
    }

    /**
     * Draws a cross on this square
     */
    public void drawCross() {
        gc.strokeLine(getWidth() * 0.1, getHeight() * 0.1, getWidth() * 0.9, getHeight() * 0.9);
        gc.strokeLine(getWidth() * 0.9, getHeight() * 0.1, getWidth() * 0.1, getHeight() * 0.9);
    }

    /**
     * Draws a circle on this square
     */
    public void drawCircle() {
        gc.strokeOval(getWidth() * 0.1, getHeight() * 0.1, getWidth() * 0.8, getHeight() * 0.8);
    }

    /**
     * Crosses out this square horizontally
     */
    public void crossOutHor() {
        gc.setStroke(Color.GREEN);
        gc.strokeLine(0, getHeight() * 0.5, getWidth(), getHeight() * 0.5);
        gc.setStroke(Color.WHITE);
    }

    /**
     * Crosses out this square vertically
     */
    public void crossOutVer() {
        gc.setStroke(Color.GREEN);
        gc.strokeLine(getWidth() * 0.5, 0, getWidth() * 0.5, getHeight());
        gc.setStroke(Color.WHITE);
    }

    /**
     * Crosses out this square diagonally
     */
    public void crossOutDia() {
        gc.setStroke(Color.GREEN);
        gc.strokeLine(0, 0, getWidth(), getHeight());
        gc.setStroke(Color.WHITE);
    }

    /**
     * Crosses out this square diagonally in the other direction
     */
    public void crossOutRevDia() {
        gc.setStroke(Color.GREEN);
        gc.strokeLine(getWidth(), 0, 0, getHeight());
        gc.setStroke(Color.WHITE);
    }

    /**
     * Get the column of this square
     * @return column index
     */
    public int getColumn() {
        return column;
    }

    /**
     * Get the row of this square
     * @return row index
     */
    public int getRow() {
        return row;
    }
}
