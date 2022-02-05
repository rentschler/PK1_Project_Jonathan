package geoviz;

import java.util.ArrayList;
import java.util.Arrays;

public class Utilities {


    /**
     * This method compares both doubles
     *
     * @param a - double a
     * @param b - double b
     * @return - true if both doubles are the equal
     */
    public static boolean doubleComparison(double a, double b) {
        double epsilon = 0.00001;
        return Math.abs(a - b) < epsilon;
    }

    /**
     * This Method calculates slope of the line connecting both points
     * slope m=delta y/ delta x
     *
     * @param x1 x coordinate of the first point
     * @param y1 y coordinate of the first point
     * @param x2 x coordinate of the second point
     * @param y2 y coordinate of the second point
     * @return the slope
     */
    public static double getSlope(double x1, double y1, double x2, double y2) {//throw error if delta x or delta y ist 0--> return Infinity
        return getSlope(new MyPoint(x1, y1), new MyPoint(x2, y2));
    }


    /**
     * This Method calculates slope of the line connecting both points
     *
     * @param pointOne point
     * @param pointTwo point
     * @return the slope
     */
    public static double getSlope(MyPoint pointOne, MyPoint pointTwo) {
        if (doubleComparison(pointTwo.getX() - pointOne.getX(), 0)) {
            System.out.println("warning, division with zero");
            //todo throw error if delta x or delta y ist 0--> return Infinity
        }
        return (pointTwo.getY() - pointOne.getY()) / (pointTwo.getX() - pointOne.getX());
    }

    /**
     * this method cuts all numbers after the second decimal place
     *
     * @param value double value to round
     * @return rounded number
     */
    public static double round2(double value) {
        value = value * 100;
        double temp = Math.round(value);
        value = temp / 100.0;
        return value;
    }

    /**
     * The method calculates the intercept of the line (Gerade) connecting the two data points with the y-axis
     * y=m*x+c; y_1=m*x_1 +c <=> c =y_1-m*x_1
     *
     * @param x1 x coordinate of the first point
     * @param y1 y coordinate of the first point
     * @param x2 x coordinate of the second point
     * @param y2 y coordinate of the second point
     * @return the coordinates of the interception point
     */
    public static double getIntercept(double x1, double y1, double x2, double y2) {
        return getIntercept(new MyPoint(x1, y1), new MyPoint(x2, y2));
    }

    /**
     * overloaded getIntercept using myPoints instead of arrays
     *
     * @param pointOne first point
     * @param pointTwo second point
     * @return the coordinates of the interception point
     */
    public static double getIntercept(geoviz.MyPoint pointOne, geoviz.MyPoint pointTwo) {
        double slope = getSlope(pointOne, pointTwo);
        //y-achsenabschnitt
        //double y_intercept = y1- slope * x1;
        //return y_intercept;
        return pointOne.getY() - slope * pointOne.getX();
    }

    /**
     * compares two slopes and returns whether they are parallel or not
     *
     * @param slope1 slope of the first line
     * @param slope2 slope of the second line
     * @return true if they are parallel
     */
    public static boolean isParallel(double slope1, double slope2) {
        boolean isParallel;
        isParallel = doubleComparison(slope1, slope2);
        return isParallel;
    }

    /**
     * compares two slopes and returns whether they are orthogonal or not
     *
     * @param slope1 slope of the first line
     * @param slope2 slope of the second line
     * @return isOrthogonal
     */
    public static boolean isOrthogonal(double slope1, double slope2) {
        return doubleComparison(slope1 * slope2, -1.0);
    }

    /**
     * Implement a method “getPointOfIntersection”, which takes four arrays as input.
     * Each array corresponds to one data point with the first entry of each array being the x-coordinate
     * and the second entry representing the y-coordinate. Given a line between point1
     * and point2 and another line between point3 and point4 calculate the point of intersection and
     * return this point as an array (first entry is the x-coordinate, second entry is the y-coordinate).
     * The method should have the following signature:
     * Added handling of vertical lines.
     *
     * @param lineOne Line1
     * @param lineTwo Line2
     * @return the point of intersection as MyPoint
     */
    public static MyPoint getPointOfIntersection(geoviz.MyLine lineOne, geoviz.MyLine lineTwo) {
        //check for different lines
        if (lineOne.equals(lineTwo)) {
            System.out.println("similar lines!!");
            // TODO: 09.01.2022 throw exception similar lines
            return lineOne.getPointOne();
        }

        //Condition 1: both lines have to have different slopes
        double slopeLineOne = lineOne.getSlope();// m1
        double slopeLineTwo = lineTwo.getSlope();//m2
        double interceptOne = lineOne.getIntercept(); //c1
        double interceptTwo = lineTwo.getIntercept(); //c2

        if (isParallel(slopeLineOne, slopeLineTwo)) {
            //todo throw exception
            return null;
        }

        //check for vertical lines
        boolean verticalOne = lineOne.isVertical();
        boolean verticalTwo = lineTwo.isVertical();

        double xIntercept;
        double yIntercept;

        if (verticalOne && !verticalTwo) {// line one is vertical, line two is normal
            double s = lineOne.getPointOne().getX();
            yIntercept = slopeLineTwo * s + interceptTwo;
            xIntercept = s;
        } else if (!verticalOne && verticalTwo) {// line one is normal, line two is vertical
            double s = lineTwo.getPointOne().getX();
            yIntercept = slopeLineOne * s + interceptOne;
            xIntercept = s;
        } else if (!verticalOne && !verticalTwo) {
            // yIntercept: y = m1* xIntercept +c1
            xIntercept = (interceptTwo - interceptOne) / (slopeLineOne - slopeLineTwo);
            yIntercept = lineOne.getYKoordinate(xIntercept);
        } else {// line one and line two are vertical --> no intercept or infinite intercept
            return null;
        }
        return new MyPoint(xIntercept, yIntercept);
    }

    /**
     * overloaded getPointOfIntersection
     *
     * @param point1 point
     * @param point2 point
     * @param point3 point
     * @param point4 point
     * @return the point of intersection as MyPoint
     */
    public static MyPoint getPointOfIntersection(MyPoint point1, MyPoint point2, MyPoint point3, MyPoint point4) {
        return getPointOfIntersection(new MyLine(point1, point2), new MyLine(point3, point4));
    }

    /**
     * calculates the length of the connecting line
     * L = sqrt(powerOf(Xa-xb)+powerOf(Ya+Yb))
     *
     * @param pointA one point
     * @param pointB another point
     * @return length
     */
    public static double getDistance(MyPoint pointA, MyPoint pointB) {
        double PowerOfDeltaY = pointA.getY() - pointB.getY();
        PowerOfDeltaY *= PowerOfDeltaY;

        double PowerOfDeltaX = pointA.getX() - pointB.getX();
        PowerOfDeltaX *= PowerOfDeltaX;
        return Math.sqrt(PowerOfDeltaX + PowerOfDeltaY);
    }

    /**
     * formula line: y = m * x +c
     * formula verticalLine: x = s
     * formula circle (x-a)^2 + (y-b)^2-d = 0
     * line in circle einsetzen
     * <p>
     * --> x^2 * (1+m^2) + x * (-2a + 2m * (c - b)) +  x^0 * (a^2 + (c - b)^2 -d) = 0
     * --> abc - formula intercept --> x1 and x2 or x1 or null
     * <p>
     * or
     * -->y^2 * (1) + y^1 * (-2 * b + y^0 * (sOne * sOne + -2 * a * sOne + a * a + b * b - d) = 0
     * --> abc - formula intercept --> y1 and y2 or y1 or null
     *
     * @param line1  line
     * @param circle circle
     * @return Array of either two, one or zero points
     */
    public static MyPoint[] getPointOfIntersection(MyLine line1, MyCircle circle) {
        if (line1 == null) {
            System.out.println("no intersection with a line that is null");
            return null;
        }
        double sOne = line1.getPointOne().getX();
        boolean verticalLine = line1.isVertical();

        double a = circle.getPointCenter().getX(); // x value of center
        double b = circle.getPointCenter().getY();// y value of center
        double d = circle.getRadiusCustom() * circle.getRadiusCustom();//radius^2
        double c = line1.getIntercept(); // y intercept
        double m = line1.getSlope();// slope


        double[] points;

        if (verticalLine) {
            //vertical line: x = s
            double YPower2 = 1;
            double YPower1 = -2 * b;
            double YPower0 = sOne * sOne + -2 * a * sOne + powTwo(a) + powTwo(b) - d;
            points = abcFormula(YPower2, YPower1, YPower0);
        } else {
            double XPower2 = 1 + powTwo(m);
            double XPower1 = (-2 * a) + ((2 * m) * (c - b));
            double XPower0 = powTwo(a) + ((c - b) * (c - b)) - d;
            points = abcFormula(XPower2, XPower1, XPower0);
        }

        if (points == null) {
            return null;
        }

        MyPoint[] array = new MyPoint[points.length];
        for (int i = 0; i < points.length; i++) {
            double x = points[i];
            MyPoint point;
            if (verticalLine) {
                point = new MyPoint(sOne, x);
            } else {
                point = new MyPoint(x, line1.getYKoordinate(x));
            }
            array[i] = point;
        }
        return array;
    }

    /**
     * calculates the line of the intercepts --> then the intercept of one circle1 and the line gets calculated.
     * sometimes the line is vertical --> slope = infinite.
     * This case is handled in getPointFromSecant().
     *
     * @param circle1 first circle
     * @param circle2 second circle
     */
    public static MyPoint[] getPointOfIntersection(MyCircle circle1, MyCircle circle2) {
        if (circle1.equals(circle2)) {
            System.out.println("similar circles!!");
            // TODO: 09.01.2022 throw exception similar lines
            return null;
        }
        MyPoint centerOne = circle1.getPointCenter();
        double a = circle1.getPointCenter().getX();
        double b = circle1.getPointCenter().getY();
        double d = powTwo(circle1.getRadiusCustom());
        //parameters of circle one

        MyPoint centerTwo = circle2.getPointCenter();
        double e = circle2.getPointCenter().getX();
        double f = circle2.getPointCenter().getY();
        double g = powTwo(circle2.getRadiusCustom());
        //parameters of circle two


        //check weither the distance of the circles is greater than the sum of the radix
        if (getDistance(centerOne, centerTwo) > (circle1.getRadiusCustom() + circle2.getRadiusCustom())) {
            System.out.println("distance = " + getDistance(centerOne, centerTwo));
            System.out.println("sum of radix =" + (circle1.getRadiusCustom() + circle2.getRadiusCustom()));
            //throw exception don't intercept
            System.out.println("no u");
            return null;
        }

        //calculating two points on the secant of both circles
        //sometimes only a tangent or a passant
        //x = 0 and x = a (or y = 0 and y = 1 in case of a vertical line) is inserted in the formula
        MyPoint pointOne = getPointFromSecant(a, b, d, e, f, g, 0);

        MyPoint pointTwo = getPointFromSecant(a, b, d, e, f, g, 1);

        MyLine secant = new MyLine(pointOne, pointTwo);
        System.out.println(secant);
        return getPointOfIntersection(secant, circle1);
    }

    /**
     * get a Point on the secant connecting the intercept points (Point1, Point2) of the two circles
     * the formula for circle 2 gets subtracted from the formula of circle one.
     * simplify towards y (or x)
     * -->you get the formula (y-value) for the line intercepting both circles
     * -->sometimes the normal formula divides by zero, then you need a formula for the x-value
     * <p>
     * formula circle1 (x-a)^2 + (y-b)^2-d = 0
     * formula circle2 (x-e)^2 + (y-f)^2-g = 0
     *
     * @param a x value of center1
     * @param b y value of center2
     * @param d radius1 ^2
     * @param e x value of center
     * @param f y value of center
     * @param g radius2 ^2
     * @param x function value for the line
     * @return this method returns a MyPoint on the line connecting both circles
     */
    private static MyPoint getPointFromSecant(double a, double b, double d, double e, double f, double g, double x) {

        if (doubleComparison(f - b, 0)) {
            //System.out.println("divide by 0");
            double y = x;
            double XValue = (-2 * y * (f - b) + powTwo(e) + powTwo(f) + d - powTwo(a) - powTwo(b) - g) / (2 * (e - a));
            System.out.println("vertical line: x = " + XValue);
            System.out.println("point on the secant " + new MyPoint(x, XValue));
            return new MyPoint(XValue, x);

        }
        double yValueForX = (-2 * x * (e - a) + powTwo(e) + powTwo(f) + d - powTwo(a) - powTwo(b) - g) / (2 * (f - b));
        System.out.println("point on the secant " + new MyPoint(x, yValueForX));
        return new MyPoint(x, yValueForX);
    }

    /**
     * simple abc formula
     *
     * @param a a * x^2
     * @param b b * x^1
     * @param c c * x^0
     * @return either x1 and x2 or x1 or nothing
     */
    public static double[] abcFormula(double a, double b, double c) {
        double x = powTwo(b) - 4 * a * c;
        if (x < 0) {
            return null;
        } else if (x > 0) {
            double x1 = (-1 * b + Math.sqrt(x)) / (2 * a);
            double x2 = (-1 * b - Math.sqrt(x)) / (2 * a);
            return new double[]{x1, x2};
        } else {
            double x1 = (-1 * b) / (2 * a);

            return new double[]{x1};
        }
    }

    /**
     * calculates value * value
     *
     * @param value value
     * @return value * value
     */
    public static double powTwo(double value) {
        return value * value;
    }

    public static MyPoint[] convertToArray(ArrayList<MyPoint> list) {
        MyPoint[] array = new MyPoint[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    public static ArrayList<MyPoint[]> calculateIntersections(ArrayList<MyLine> myLineArrayList,
                                                              ArrayList<MyCircle> myCircleArrayList) {
        System.out.println("calculating intersections...");
        ArrayList<MyPoint[]> intersections = new ArrayList<>();
        for (MyLine line1 : myLineArrayList) {
            for (MyLine line2 : myLineArrayList) {
                if (!line1.equals(line2)) {
                    intersections.add(new MyPoint[]{getPointOfIntersection(line1, line2)});
                }
            }
            for (MyCircle circle : myCircleArrayList) {
                intersections.add(getPointOfIntersection(line1, circle));
            }
        }
        for (MyCircle circle1 : myCircleArrayList) {
            for (MyCircle circle2 : myCircleArrayList) {
                if (!circle1.equals(circle2)) {
                    intersections.add(getPointOfIntersection(circle1, circle2));
                }
            }
        }
        for (MyPoint[] point : intersections) {
            System.out.println(Arrays.toString(point));
        }
        return intersections;
    }
}
