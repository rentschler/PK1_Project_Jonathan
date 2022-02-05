package geoviz;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * static Class DataReader that can read from a Datafile;
 */
public class DataReader {

    /**
     * tries to read the File DataPoints and convert each line into a MyPoint
     * @return Array of MyPoints
     */
    public static MyPoint[] bufferedReader() {
        File file = new File("src" + File.separator + "geoviz" + File.separator + "DataPoints.txt"); //DataPoints File
        return bufferedReader(file);
    }

    /**
     * tries to read the File DataPoints and convert each line into a MyPoint
     * @param file - file to read the data from
     * @return Array of MyPoints
     */
    public static MyPoint[] bufferedReader(File file) {
//        File file = new File("src" + File.separator + "geoviz" + File.separator + "DataPoints.txt"); //DataPoints File

        BufferedReader in;
        ArrayList<MyPoint> points = new ArrayList<>();
        try {
            in = new BufferedReader(new FileReader(file));
            String line;
            //read the input line by line
            while ((line = in.readLine()) != null) {
                String[] numberArray = line.split(",");
                if (numberArray.length == 2 && stringANumber(numberArray[0].trim()) && stringANumber(numberArray[1].trim())) {

                    String[] halfArrayX = numberArray[0].split("\\.");
                    String[] halfArrayY = numberArray[1].split("\\.");
                    double xValue = getValue(halfArrayX);
                    double yValue = getValue(halfArrayY);
                    System.out.println("value: "+ xValue +", "+ yValue);
                    points.add(new MyPoint(xValue,yValue));
                    //System.out.println(xValue);
                }
                System.out.println(Arrays.toString(numberArray));
            }
            return Utilities.convertToArray(points);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        System.out.println("invalid read");
        return null;
    }

    /**
     *this method gets either an Array with a single String  b --> returns (b) this string as double
     * or gets two Strings. First string corresponds to the decimal value before the comma.
     * The second string a corresponds to the decimal value after the comma.
     * --> returns (b.a) both strings as a double value
     * @param halfArray Array containing numbers
     * @return double value
     */
    private static double getValue(String[] halfArray) {
        double localValue=0;
        if (halfArray.length == 1) {
            localValue = Double.parseDouble(halfArray[0].trim());
        } else if (halfArray.length == 2) {
            localValue = Double.parseDouble(halfArray[0].trim());
            //Zahl der Nachkommastelle x zu 0.x umwandeln. BSP 420 --> 0.42
            double temp = Double.parseDouble(halfArray[1].trim())/Math.pow(10, halfArray[1].length());
            localValue +=temp;
        }
        return localValue;
    }

    /**
     * check weither a char has a number value or not
     *
     * @param character char to check
     * @return true if the char has a number value
     */
    private static boolean isNumber(char character) {
        return '0' <= character && character <= '9';
    }

    /**
     * check weither every char of a string has a number value or not
     *
     * @param string string to check
     * @return true if any char hasn't a number value
     */
    private static boolean stringANumber(String string) {
        if (string.length() == 0) {
            return false;
        }
        for (int i = 0; i < string.length(); i++) {
            char character = string.charAt(i);
            //if a char isn't a number return false
            if (!isNumber(character)&&character!='.') {
                return false;
            }
        }
        return true;
    }


}
