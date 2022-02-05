package geoviz;

import javafx.scene.shape.Circle;

/**
 * This class represents circles in the coordinate system.
 * Middle point and one point on the circle is required
 */
public class MyCircle extends Circle {
    private final double radius;
    private MyPoint pointCenter, pointOnCircle;
    private boolean fillShape;
    /**
     * constructor calculating the radius
     * = euclidean distance between both points
     *
     * @param pointCenter   center point
     * @param pointOnCircle one point on the circle
     */
    public MyCircle(MyPoint pointCenter, MyPoint pointOnCircle) {
        this.pointCenter = pointCenter;
        this.pointOnCircle = pointOnCircle;
        radius = Utilities.getDistance(pointCenter, pointOnCircle);

        this.setCenterX(pointCenter.getX());
        this.setCenterY(pointCenter.getY());
        this.setRadius(this.radius);


    }

    /**
     * getter
     *
     * @return MyPoint pointCenter
     */
    public MyPoint getPointCenter() {
        return pointCenter;
    }

    /**
     * setter
     *
     * @param pointCenter MyPoint pointCenter
     */
    public void setPointCenter(MyPoint pointCenter) {
        this.pointCenter = pointCenter;
    }

    /**
     * getter
     *
     * @return MyPoint pointOnCircle
     */
    public MyPoint getPointOnCircle() {
        return pointOnCircle;
    }

    /**
     * setter
     *
     * @param pointOnCircle MyPoint pointOnCircle
     */
    public void setPointOnCircle(MyPoint pointOnCircle) {
        this.pointOnCircle = pointOnCircle;
    }

    /**
     * getter
     *
     * @return radius
     */
    public double getRadiusCustom() {
        return radius;
    }

    /**
     * overrides to string method
     *
     * @return pointCenter, pointOnCircle, radius (rounded)
     */
    @Override
    public String toString() {
        return "MyCircle{" +
                "pointCenter=" + pointCenter +
                ", pointOnCircle=" + pointOnCircle +
                ", radius=" + Utilities.round2(radius) +
                '}';
    }

    /**
     * setter
     * @return shape is Filled
     */
    public boolean getFillShape() {
        return fillShape;
    }

    /**
     * getter
     * @param fillShape shape is Filled
     */
    public void setFillShape(boolean fillShape) {
        this.fillShape = fillShape;
    }
}
