package geoviz;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.Objects;

/**
 * This class represents points in the coordinate system.
 * Two double values for the x- and y- coordinate are required.
 */
public class MyPoint extends Circle {
    private double x, y;
    private Paint color= Color.BLACK;

    /**
     * constructor
     *
     * @param x coordinate
     * @param y coordinate
     */
    public MyPoint(double x, double y) {
        this.x = x;
        this.setCenterX(x);
        this.y = y;
        this.setCenterY(y);
        this.setFill(color);
    }

    /**
     * getter
     *
     * @return x
     */
    public double getX() {
        return x;
    }

    /**
     * setter
     *
     * @param x x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * getter
     *
     * @return y
     */
    public double getY() {
        return y;
    }

    /**
     * setter
     *
     * @param y y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * overrides to string method
     * rounds the Coordinates
     *
     * @return MyPoint{rounded x, rounded y}
     */
    @Override
    public String toString() {
        return "MyPoint{" +
                "x=" + Utilities.round2(x) +
                ", y=" + Utilities.round2(y) +
                '}';
    }

    /**
     * override the equals method
     * @param o object
     * @return true this == o or x and y value are the same
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyPoint point = (MyPoint) o;
        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0;
    }

    /**
     * hashcode method
     * @return hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }


    public Paint getColor() {
        return color;
    }

    public void setColor(Paint color) {
        this.color = color;
    }
}
