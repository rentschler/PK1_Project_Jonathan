package geoviz;

import javafx.scene.shape.Line;

/**
 * This class represents lines in the coordinate system.
 * Two MyPont's for the first and the second point of the line are required.
 */
public class MyLine extends Line {
    private final boolean isVertical;//true if the line is a vertical line. Then the formula is x = s instead of y= m * x +c
    private MyPoint pointOne, pointTwo;
    private double slope, intercept;

    /**
     * constructor
     *
     * @param pointOne first point on the line
     * @param pointTwo second point on the line
     */
    public MyLine(MyPoint pointOne, MyPoint pointTwo) {
        // TODO: 27.12.2021 handle vertical lines 
        this.pointOne = pointOne;
        this.pointTwo = pointTwo;

        isVertical = Utilities.doubleComparison(pointOne.getX() - pointTwo.getX(), 0);
        if (isVertical) {
            System.out.println("warning: vertical line");
        }

        slope = Utilities.getSlope(pointOne, pointTwo);
        intercept = Utilities.getIntercept(pointOne, pointTwo);

    }

    /**
     * constructor
     * @param pointOne first point on the line
     * @param pointTwo second point on the line
     * @param screenWidth - end position of the line
     */
    public MyLine(MyPoint pointOne, MyPoint pointTwo, double screenWidth) {
        // TODO: 27.12.2021 handle vertical lines
        this.pointOne = pointOne;
        this.pointTwo = pointTwo;

        isVertical = Utilities.doubleComparison(pointOne.getX() - pointTwo.getX(), 0);
        if (isVertical) {
            System.out.println("warning: vertical line");
        }

        slope = Utilities.getSlope(pointOne, pointTwo);
        intercept = Utilities.getIntercept(pointOne, pointTwo);

        this.setStartX(0);
        this.setStartY(getYKoordinate(0));
        this.setEndX(screenWidth*2);
        this.setEndY(getYKoordinate(screenWidth*2));
    }

    /**
     * getter
     *
     * @return pointOne
     */
    public MyPoint getPointOne() {
        return pointOne;
    }

    /**
     * setter
     *
     * @param pointOne point one
     */
    public void setPointOne(MyPoint pointOne) {
        this.pointOne = pointOne;
    }

    /**
     * getter
     *
     * @return pointTwo
     */
    public MyPoint getPointTwo() {
        return pointTwo;
    }

    /**
     * setter
     *
     * @param pointTwo point two
     */
    public void setPointTwo(MyPoint pointTwo) {
        this.pointTwo = pointTwo;
    }

    /**
     * getter
     *
     * @return slope
     */
    public double getSlope() {
        return slope;
    }

    /**
     * setter
     *
     * @param slope slope
     */
    public void setSlope(double slope) {
        this.slope = slope;
    }

    /**
     * setter
     *
     * @return intercept
     */
    public double getIntercept() {
        return intercept;
    }

    /**
     * setter
     *
     * @param intercept intercept
     */
    public void setIntercept(double intercept) {
        this.intercept = intercept;
    }

    /**
     * overrides to string method
     *
     * @return MyLine{ pointOne, pointTwo, slope and intercept}
     */
    @Override
    public String toString() {
        return "MyLine{" +
                "pointOne=" + pointOne +
                ", pointTwo=" + pointTwo +
                ", slope=" + slope +
                ", intercept=" + intercept +
                '}';
    }

    /**
     * calculate function value corresponding to a x value
     * @param x -value
     * @return y-value
     */
    public double getYKoordinate(double x) {
        return slope * x + intercept;
    }

    /**
     * getter
     *
     * @return true if line is vertical
     */
    public boolean isVertical() {
        return isVertical;
    }
}
