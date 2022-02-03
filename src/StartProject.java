import geoviz.DataReader;
import gui.Draw;
import javafx.application.Application;

import java.util.Arrays;


/**
 * start of the project
 */
public class StartProject {
    public static void main(String[] args) {
        // TODO: 26.01.2022 Koordinaten system größe dynamisch anpassen
        // TODO: 31.01.2022 Label bleiben auf Kreisen "stuck" 
        // TODO: 31.01.2022 data laoder 
        // TODO: 31.01.2022 dürfen intersection punkte exakt auf MyPoints liegen?
        // TODO: 02.02.2022 bei vertikal übereinander Liegenden Punkten einen um 0.1 px verschieben?
        // TODO: 02.02.2022  primary stage event handler bei Fenster Größen Änderungen
        // TODO: 02.02.2022 mouse click on a intersection should add this point 

/*
        MyPoint[] points = {new MyPoint(3, 3), new MyPoint(4, 3),
                new MyPoint(0, 1), new MyPoint(0, 0)};
        MyLine line1 = new MyLine(points[0], points[1]);
        MyLine line2 = new MyLine(points[2], points[3]);

        MyPoint intersection1 = Utilities.getPointOfIntersection(line1, line1);
        System.out.println("Intersection: " + intersection1.getX() + " " + intersection1.getY());

        MyPoint intersection2 = Utilities.getPointOfIntersection(line1, line2);

        System.out.println("Intersection: " + intersection2.getX() + " " + intersection2.getY());
        System.out.println("Slope of line1: " + line1.getSlope() + " intercept: " + line1.getIntercept());
        System.out.println("Slope of line2: " + line2.getSlope() + " intercept: " + line2.getIntercept());

        //MyLine line3 = new MyLine(new MyPoint(0,0),new MyPoint(1,1));
        //MyCircle circle = new MyCircle(new MyPoint(0,0),new MyPoint(1,1));
        //double distance = Utilities.getDistance(new MyPoint(2,2),line3);

        MyCircle circle = new MyCircle(new MyPoint(4, 4), new MyPoint(1, 4));
        MyLine line = new MyLine(new MyPoint(0, -5), new MyPoint(2.5, 0));

        //System.out.println(Arrays.toString(Utilities.abcFormula(5, -30, 20)));
        double[]lol = Utilities.abcFormula(5, -30, 20);
        System.out.println(Arrays.toString(Utilities.getPointOfIntersection(line, circle)));


        MyPoint[] points = new MyPoint[]{new MyPoint(1, 1), new MyPoint(2, 2), new MyPoint(1, 1), new MyPoint(1, 2)};
        MyLine line1 = new MyLine(points[0], points[1]);
        MyCircle circle = new MyCircle(points[2], points[3]);
        MyCircle circle2 = new MyCircle(points[3], points[1]);
        MyCircle circle3 = new MyCircle(new MyPoint(2, 1), points[1]);
        MyCircle circle4 = new MyCircle(new MyPoint(0, 0), new MyPoint(0, 1));

        MyCircle circle5 = new MyCircle(new MyPoint(0, 2), new MyPoint(0, 1));


        System.out.println("\nintercept of " + line1 + circle);
        System.out.println(Arrays.toString(Utilities.getPointOfIntersection(line1, circle)));

        System.out.println("\nintercept of " + circle + circle2);
        System.out.println(Arrays.toString(Utilities.getPointOfIntersection(circle, circle2)));

        System.out.println("\nintercept of " + circle + circle3);
        System.out.println(Arrays.toString(Utilities.getPointOfIntersection(circle, circle3)));
        //secant is vertical --> mathematical not a function


        System.out.println("\nintercept of " + circle4 + circle5);
        System.out.println(Arrays.toString(Utilities.getPointOfIntersection(circle4, circle5)));
        //secant is horizontal
*/
        //System.out.println(Arrays.toString(DataReader.readPoints()));
        //DataReader.bufferedReader();
        System.out.println(Arrays.toString(DataReader.bufferedReader()));

        Application.launch(Draw.class);// nur der Klassen name
    }
}
