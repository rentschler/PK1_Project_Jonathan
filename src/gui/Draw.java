package gui;

import geoviz.MyCircle;
import geoviz.MyLine;
import geoviz.MyPoint;
import geoviz.Utilities;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

/**
 * (*2*)
 * This class is the starting point for building a user interface (UI).
 */
public class Draw extends Application {
    private final ArrayList<MyPoint> myPointArrayList = new ArrayList<>();
    private final ArrayList<MyPoint> myPointIntersectionArrayList = new ArrayList<>();
    private final ArrayList<MyLine> myLineArrayList = new ArrayList<>();
    private final ArrayList<MyCircle> myCircleArrayList = new ArrayList<>();
    public ColorPicker colorPicker;
    public double coordinateSystemWidth, coordinateSystemHeight;
    private Pane coordinateSystem;
    private MyPoint recentMyPoint = null;
    private boolean drawLine = true;
    private boolean fill = false;
    private Label temporaryLabel;
    private boolean hoveringMyPoint = false;
    private boolean drawIntersections = true;

    /**
     * This method is optional. However, it is called before running the start
     * method. Therefore, in case you have to do any precalculations or setting
     * up data structures etc. you can do this in the init() method.
     */
    public void init() {
        System.out.println("1. Called before running start(Stage primaryStage).");
        coordinateSystemWidth = 1280;
        coordinateSystemHeight = 630;
    }

    /**
     * The start() method is the starting point of constructing a JavaFX application,
     * therefore, we need to first override the start method of the
     * javafx.application.Application class. An object of the class javafx.stage.Stage
     * is passed into the start() method, therefore, we have to import this
     * class and pass its object into the start method. JavaFX.application.Application
     * needs to be imported in order to override the start method.
     */
    public void start(Stage primaryStage) throws Exception {
        System.out.println("2. This is the start method for setting up the screen.");
        BorderPane root = new BorderPane(); //this is the layout manager
        Scene scene = new Scene(root, 1280, 720);

        primaryStage.setScene(scene);
        primaryStage.setTitle("PK1 Project Jonathan Rentschler");
        primaryStage.show();

//        coordinateSystemWidth = primaryStage.getWidth();
//        coordinateSystemHeight = primaryStage.getHeight();
//        System.out.println("height: " + this.coordinateSystemHeight);
//        System.out.println("width: " + this.coordinateSystemWidth);

        //top layout
        CheckBox btnFill = new CheckBox("Fill");

        RadioButton rdBtnLine = new RadioButton("line");
        RadioButton rdBtnCircle = new RadioButton("circle");
        colorPicker = new ColorPicker();

        ToggleGroup toggleGroup = new ToggleGroup();
        rdBtnLine.setToggleGroup(toggleGroup);
        rdBtnLine.setSelected(true);
        rdBtnCircle.setToggleGroup(toggleGroup);

        Button btnIntersection = new Button("Show intersection");

        ToolBar toolBarTop = new ToolBar();
        toolBarTop.getItems().addAll(rdBtnLine, rdBtnCircle, btnFill, btnIntersection, colorPicker);

        //middle

        coordinateSystem = new Pane();

        //bottom
        ToolBar toolBarBottom = new ToolBar();
        Button btnLoad = new Button("Load Data");
        Button btnClear = new Button("Clear Screen");
        toolBarBottom.getItems().setAll(btnLoad, btnClear);

        //borderLayout
        root.setCenter(coordinateSystem);
        root.setTop(toolBarTop);
        root.setBottom(toolBarBottom);


        //Button click event handler
        rdBtnLine.setOnAction(event -> drawLine = true);

        rdBtnCircle.setOnAction(event -> drawLine = false);
        btnFill.setOnAction(event -> {
            if (btnFill.isSelected()) {
                this.fill = true;
                System.out.println("selected fill");
            } else {
                this.fill = false;
                System.out.println("unselected fill");
            }
        });
        btnIntersection.setOnAction(event -> {
            ArrayList<MyPoint[]> pointList = Utilities.calculateIntersections(this.myLineArrayList, this.myCircleArrayList);
            if (drawIntersections) {

                for (MyPoint[] points : pointList) {
                    if (points != null) {
                        for (MyPoint point : points) {
                            maybeDrawPoint(point);


                        }
                    }
                }
            } else {
                for (MyPoint pointTmp : myPointIntersectionArrayList
                ) {
                    this.coordinateSystem.getChildren().remove(pointTmp);
                }
            }
            drawIntersections = !drawIntersections;

        });
        btnLoad.setOnAction(event -> {
            //File chooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                MyPoint[] points = geoviz.DataReader.bufferedReader(file);
                if (points != null) {
                    for (MyPoint point : points) {
                        point.setCenterX(point.getX());
                        double y = point.getY();
                        point.setY(this.coordinateSystemHeight-y);
                        point.setCenterY(this.coordinateSystemHeight - y);
                        System.out.println(point);
                        drawMyPoint(point);
                    }
                }
                else {
                    System.out.println("invalid File");
                }
            }
            else {
                System.out.println("invalid read");
            }
//            else {
//                file = new File("src" + File.separator + "geoviz" + File.separator + "DataPoints.txt");
//                geoviz.DataReader.bufferedReader(file);
//            }
        });
        btnClear.setOnAction(event -> {
            System.out.println("remove everything");
            coordinateSystem.getChildren().removeIf(node -> coordinateSystem.getChildren().contains(node));
            myCircleArrayList.clear();
            myLineArrayList.clear();
            myPointArrayList.clear();
            removeTemporaryLabel();
            drawCoordinateSystem();
        });

        //Mouse click event handler
        coordinateSystem.setOnMouseClicked(event -> {
            removeTemporaryLabel();//to prevent errors: remove tha label that shows up when hovering over a shape.
            if (this.hoveringMyPoint) {
                //make sure not to create a point when we already hover over a Point.
                System.out.println("clicked existing point");
            } else {
                //create a new Point on the coordinate system
                this.drawMyPoint(event);
            }
        });

        //height resize listener
        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("width resize");
            this.coordinateSystemWidth = primaryStage.getWidth();
            coordinateSystem.getChildren().removeIf(node -> coordinateSystem.getChildren().contains(node));
            drawCoordinateSystem();
            redrawMyPoints();
        });
        //width resize listener
        //coordinateSystem.getHeight() is sometimes inconsistent
        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("height resize");
            double centerHeightOffSet = 50;// 50 px layout border
            centerHeightOffSet += toolBarTop.getHeight() + toolBarBottom.getHeight();
            System.out.println("offset" + centerHeightOffSet);
            double height = primaryStage.getHeight() - centerHeightOffSet;
            if (Math.abs(height - coordinateSystem.getHeight()) > 10) {
                this.coordinateSystemHeight = primaryStage.getHeight() - centerHeightOffSet;
                System.out.println("coordinateSystem height " + (primaryStage.getHeight() - centerHeightOffSet));
            } else {
                this.coordinateSystemHeight = coordinateSystem.getHeight();
                System.out.println("coordinateSystem height " + coordinateSystem.getHeight());
            }
            this.coordinateSystemWidth = primaryStage.getWidth();
//            this.coordinateSystemHeight = primaryStage.getHeight(); // --> inconsistent

            System.out.println("coordinateSystem height " + coordinateSystem.getHeight());

            coordinateSystem.getChildren().removeIf(node -> coordinateSystem.getChildren().contains(node));
            drawCoordinateSystem();
            redrawMyPoints();
        });
        drawCoordinateSystem();
    }

    private void drawCoordinateSystem() {
        double width = this.coordinateSystemWidth;
        double height = this.coordinateSystemHeight +50;
        int distance = 15;
        for (int i = 0; i < width / distance; i++) {
            //vertical lines
            Line line = new Line();
            line.setStartX(distance * i);
            line.setStartY(0);
            line.setEndX(distance * i);
            line.setEndY(height);
            line.setFill(Color.GREY);
            coordinateSystem.getChildren().add(line);
//            add vertical and horizontal lines to draw a coordinate system
        }
        for (int i = 0; i < width / distance; i++) {
            //horizontal lines
            Line line = new Line();
            line.setStartX(0);
            line.setStartY(distance * i);
            line.setEndX(width);
            line.setEndY(distance * i);
            line.setFill(Color.GREY);
            coordinateSystem.getChildren().add(line);
        }
    }

    /**
     * draw Point
     * @param point
     */
    private void maybeDrawPoint(MyPoint point) {
        System.out.println("try");
        if (point == null) {
            return;
        }
//        point.setCenterX(point.getX());
//        point.setCenterY(point.getY());
        if (this.coordinateSystem.getChildren().contains(point)
                || this.getClosestPoint(point, 0.01) != null) {
            System.out.println("duplicate intersection");
            double x = point.getX() + 0.01;
            double y = point.getY() + 0.01;
            MyPoint point2 = new MyPoint(x, y);
            point2.setCenterX(x);
            point2.setCenterY(y);
//            addEventListener(point2);
            point2.setRadius(5);
            point2.setFill(Color.BLUE);
            point2.setColor(Color.BLUE);
            if (!this.coordinateSystem.getChildren().contains(point2)) {
                this.coordinateSystem.getChildren().add(point2);//draw point
                this.myPointIntersectionArrayList.add(point2);//save point in Array List

            }
        } else {
            addEventListener(point);
            point.setRadius(7.5);
            point.setFill(Color.BLUE);
            point.setColor(Color.BLUE);
            this.coordinateSystem.getChildren().add(point);//draw point
            this.myPointIntersectionArrayList.add(point);

        }


    }

    private void addEventListener(MyPoint point) {
        //Add a Mouse entered(hovering over Point) event listener
        point.setOnMouseEntered(eventEvent -> {
            removeTemporaryLabel();
            System.out.println("entered area");
            point.setFill(Color.RED);//change color
            this.hoveringMyPoint = true;
            addTemporaryLabel("x:" + Utilities.round2(point.getX()) + "\ny:" +
                            Utilities.round2(this.coordinateSystemHeight - point.getY()),
                    point.getX(), point.getY());//create Label
        });

        //Add a Mouse exited(stopped hovering over Point) event listener
        point.setOnMouseExited(eventEvent -> {
            System.out.println("exited area");
            point.setFill(point.getColor());//change color
            this.hoveringMyPoint = false;
            removeTemporaryLabel();//remove label
        });

        //Add a Mouse click listener
        point.setOnMouseClicked(event1 -> {
            if (this.recentMyPoint == null) {
                //when we click on a MyPoint the first tine --> remember that point
                this.recentMyPoint = point;
                point.setStrokeWidth(3);//line width
                point.setStroke(Color.BLUE);//line color
                //add blue stroke when selecting a point
            } else if (!point.equals(this.recentMyPoint)) {
                //when we click at a (different) myPoint the second time --> draw a line/circle
                if (this.drawLine) {
                    //btn line is chosen
                    this.drawLine(point);
                } else {
                    this.drawCircle(point);
                }
                if (this.recentMyPoint != null) {
                    this.recentMyPoint.setStrokeWidth(0);
                }// reset stroke that is created when selecting a point
                this.recentMyPoint = null;//reset recent point variable
            }
            //redraw the points on top of the shapes
            this.redrawMyPoints();
        });
    }

    /**
     * This method is called when the UI is closed using the respective button
     * in the window.
     */
    public void stop() {
        System.out.println("3. Application is closed.");
    }

    /**
     * add a new MyPoint
     *
     * @param event mouse Event (mouse klick)
     */
    public void drawMyPoint(MouseEvent event) {
        //create My Point
        MyPoint point = new MyPoint(event.getX(), event.getY());
       drawMyPoint(point);
    }

    /**
     * add a new MyPoint
     * @param point - point
     */
    public void drawMyPoint(MyPoint point){
        point.setRadius(7.5);
        if (this.coordinateSystem.getChildren().contains(point)
                || this.getClosestPoint(point, 15) != null) {
            System.out.println("warning: duplicate MyPoint");
            return;
        }

        //Add a Mouse entered(hovering over Point) event listener
        addEventListener(point);

        //Finally, Draw the point
        this.coordinateSystem.getChildren().add(point);//draw point
        this.addMyPointToArrayList(point);//save point in Array List

        if (this.recentMyPoint != null) {
            this.recentMyPoint.setStrokeWidth(0);// width
        }
        this.recentMyPoint = null;//reset recent point
        //when creating a new Point the remembered point is rested.
    }

    /**
     * draw a new Point to the coordinate System.
     * First coordinate is hit (mouse klick), second coordinate is recent click
     *
     * @param hit myPoint
     */
    public void drawLine(MyPoint hit) {
        MyLine line = new MyLine(hit, this.recentMyPoint, this.coordinateSystemWidth);
        line.setStrokeWidth(4);//line width
        line.setStroke(this.colorPicker.getValue());//line color
        //add a mouse entered event Listener
        line.setOnMouseEntered(eventEvent -> {
            System.out.println("entered line");
            addTemporaryLabel("Slope:" + Utilities.round2(line.getSlope() * -1)
                            + "\nIntercept:" + Utilities.round2(this.coordinateSystemHeight - line.getIntercept()),
                    eventEvent.getX(), eventEvent.getY());
        });
        //add a mouse exited event listener
        line.setOnMouseExited(eventEvent -> {
            System.out.println("exited line");
            removeTemporaryLabel();
        });

        this.coordinateSystem.getChildren().add(line);//Draw line
        this.addMyLineToArrayList(line);//save line
    }

    /**
     * draw a new Circle to the coordinate System.
     * Center Point is the hit myPoint. Radius is calculated using the recent clicked point.
     *
     * @param hit myPoint
     */
    public void drawCircle(MyPoint hit) {
        //btn circle is chosen
        MyCircle circle = new MyCircle(this.recentMyPoint, hit);
        circle.setFillShape(this.fill);
        if (this.fill) {//btn Fill is chosen
            System.out.println("fill mode");
            circle.setFill(this.colorPicker.getValue());//set Fill color
        } else {//btn Fill is not chosen
            System.out.println("Not fill mode");
            circle.setFill(new Color(0, 0, 0, 0));//fill with color transparent
            circle.setStrokeWidth(4);//line width
            circle.setStroke(this.colorPicker.getValue());//line color
        }
        //add a mouse entered event listener
        circle.setOnMouseEntered(eventEvent -> {
            System.out.println("entered circle");
            addTemporaryLabel("x:" + Utilities.round2(circle.getCenterX())
                            + "\ny:" + Utilities.round2(this.coordinateSystemHeight - circle.getCenterY())
                            + "\nRadius:" + Utilities.round2(circle.getRadius()),
                    eventEvent.getX(), eventEvent.getY());
        });
        //add a mouse exited event listener
        circle.setOnMouseExited(eventEvent -> {
            System.out.println("exited circle");
            removeTemporaryLabel();
        });
        this.coordinateSystem.getChildren().add(circle);//draw circle
        this.addMyCircleToArrayList(circle);//save circle
    }

    /**
     * redraw the MyPoints on top of the other shapes
     */
    public void redrawMyPoints() {
        System.out.println("redrawing...");
        removeTemporaryLabel();
        ArrayList<MyCircle> currentCircles = this.myCircleArrayList;
        //first redraw filled circles
        for (MyCircle circle : currentCircles
        ) {
            if (circle.getFillShape()) {
                this.coordinateSystem.getChildren().remove(circle);
                this.coordinateSystem.getChildren().add(circle);
            }
        }
        //second redraw bordered circles
        for (MyCircle circle : currentCircles
        ) {
            if (!circle.getFillShape()) {
                this.coordinateSystem.getChildren().remove(circle);
                this.coordinateSystem.getChildren().add(circle);
            }
        }
        //third redraw myLines
        for (MyLine line : this.myLineArrayList
        ) {
            this.coordinateSystem.getChildren().remove(line);
            this.coordinateSystem.getChildren().add(line);
        }
        //last redraw myPoints on top of everything
        for (MyPoint point : this.myPointArrayList
        ) {
            System.out.println("count");
            this.coordinateSystem.getChildren().remove(point);
            this.coordinateSystem.getChildren().add(point);
        }
        System.out.println("updated MyPoints drawing");
    }

    /**
     * add a MyPoint to the array List
     *
     * @param myPoint - point
     */
    public void addMyPointToArrayList(MyPoint myPoint) {
        this.myPointArrayList.add(myPoint);
        System.out.println("added Point " + myPoint);
    }

    /**
     * add a MyLine to the array List
     *
     * @param myLine - line
     */
    public void addMyLineToArrayList(MyLine myLine) {
        this.myLineArrayList.add(myLine);
        System.out.println("added Line " + myLine);
    }

    /**
     * add a MyCircle to the array List
     *
     * @param myCircle - circle
     */
    public void addMyCircleToArrayList(MyCircle myCircle) {
        this.myCircleArrayList.add(myCircle);
        System.out.println("added Circle " + myCircle);
    }

    /**
     * find the closest point to the mouse
     * when a point is closer than 15px, this point is returned
     * if all points are not close enough return null
     *
     * @param pointTemp - mouse position as a point
     * @return last myPoint with the distance <= 15
     */

    public MyPoint getClosestPoint(MyPoint pointTemp, double maxDistance) {
        MyPoint hit = null;
        for (MyPoint singlePoint : this.myPointArrayList) {
            double dist = Utilities.getDistance(singlePoint, pointTemp);
            if (dist <= maxDistance) {
                System.out.println("distance: " + dist);
                System.out.println("found a close Point");
                hit = singlePoint;
            }
        }
        return hit;
    }

    /**
     * add a temporary label displaying a String
     *
     * @param text - String text of the label
     * @param x    - x-coordinate (offset 10)
     * @param y    - y- coordinate (offset 10)
     */
    public void addTemporaryLabel(String text, double x, double y) {
        temporaryLabel = new Label(text);
        temporaryLabel.setLayoutX(x + 10);
        temporaryLabel.setLayoutY(y + 10);
        temporaryLabel.setStyle("-fx-border-color: black;-fx-background-color: white;");
        this.coordinateSystem.getChildren().add(temporaryLabel);
        System.out.println("creating label");
    }

    /**
     * remove the temporary label
     */
    private void removeTemporaryLabel() {
        if (temporaryLabel != null) {
            coordinateSystem.getChildren().remove(temporaryLabel);
        }
    }

}
