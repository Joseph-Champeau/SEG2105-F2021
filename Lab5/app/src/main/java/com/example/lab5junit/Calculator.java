package com.example.lab5junit;

import static java.lang.Math.pow;

/**
 * This class consists exclusively of static methods that operate to return
 * standard math calculations.  It contains algorithms that operate on
 * collections, which return a double.
 *
 * @author  Brunet, Simon
 * @author  Champeau, Joseph
 * @author  Haldane, Charlie
 * @author  Shiferaw, Michias
 * @version 1.0(08/22/04)
 * @since   0.5
 */
public class Calculator {

    /**
     * Simple method to add two doubles.
     *
     * @param  op1 double one to be added
     * @param  op2 double two to be added
     * @return  double result of op1 plus op2
     */
    public double add(String op1, String op2) {
        if (op1.equals("null")||op2.equals("null")){
            return -1;
        } else {
            return Double.parseDouble(op1) + Double.parseDouble(op2);
        }
    }

    /**
     * Simple method to subtract two doubles.
     *
     * @param  op1 double one to be subtracted
     * @param  op2 double two to be subtracted from op1
     * @return  double result of op1 subtract op2
     */
    public double diff(String op1, String op2) {
        if (op1.equals("null")||op2.equals("null")){
            return -1;
        } else {
            return Double.parseDouble(op1) - Double.parseDouble(op2);
        }
    }

    /**
     * Simple method to divide two doubles.
     *
     * @param  op1 double one to be divided
     * @param  op2 double 2 to be divided
     * @return  double result of op1 divide op2
     */
    public double div(String op1, String op2) {
        if (op1.equals("null")||op2.equals("null")){
            return -1;
        } else if (op2.equals("0")) {
            return -1.1;
        } else {
            return Double.parseDouble(op1) / Double.parseDouble(op2);
        }
    }

    /**
     * Simple method to multiply two doubles.
     *
     * @param  op1 double one to be multiplied
     * @param  op2 double 2 to be multiplied
     * @return  double result of op1 multiply op2
     */
    public double mul(String op1, String op2) {
        if (op1.equals("null")||op2.equals("null")){
            return -1;
        } else {
            return Double.parseDouble(op1) * Double.parseDouble(op2);
        }
    }

    /**
     * Simple method to calculate an exponent.
     *
     * @param  op1 double one to be base
     * @param  op2 double 2 to be exponent
     * @return  double result of op1 to the power of op2
     */
    public double expon(String op1, String op2) {
        if (op1.equals("null")||op2.equals("null")) {
            return -1;
        } else if (op1.equals("0") && op2.equals("0")) {
            return -1.1;
        } else if (op1.equals("0") && Double.parseDouble(op2) < Double.parseDouble("0")) {
            return -1.2;
        } else {
            return pow(Double.parseDouble(op1), Double.parseDouble(op2));
        }
    }

    /**
     * Simple method to calculate log base 10 of a double.
     *
     * @param  op1 double one to be base
     * @return  double result of op1 to the log base 10
     */
    public double log10(String op1){
        if (op1.equals("null")){
            return -1;
        } else if (Double.parseDouble(op1) < Double.parseDouble("0")) {
            return -1.1;
        } else if (op1.equals("0")) {
            return -1.2;
        } else {
            return Math.log10(Double.parseDouble(op1));
        }
    }

    /**
     * Simple method to round a double up to closest integer.
     *
     * @param  op1 double one to be rounded
     * @return  double result of op1 rounded up
     */
    public double ceilingval(String op1){
        if (op1.equals("null")){
            return -1;
        } else {
            return Math.ceil(Double.parseDouble(op1));
        }
    }

    /**
     * Simple method to calculate ln.
     *
     * @param  op1 double one to be base
     * @return  double result of ln(op1)
     */
    public double ln(String op1){
        if (op1.equals("null")){
            return -1;
        } else {
            return Math.log(Double.parseDouble(op1));
        }
    }

    /**
     * Simple method to calculate root given two doubles.
     *
     * @param  op1 double one to be base
     * @param  op2 double two to be root
     * @return  double result of op1^1/op2
     */
    public double root1(String op1, String op2) {
        if (op1.equals("null")||op2.equals("null")){
            return -1;
        } else {
            return Math.pow(Double.parseDouble(op1),1.0/Double.parseDouble(op2));
        }
    }

    /**
     * Simple method to calculate area of a circle.
     *
     * @param  op1 double one to be radius
     * @return  double result of the area
     */
    public double areaOfCircle(String op1){
        if (op1.equals("null")) {
            return -1;
        }
        if (Double.parseDouble(op1)<0){
            return -1;

        } else {
            return Math.PI*Math.pow(Double.parseDouble(op1),2);
        }
    }

    /**
     * Simple method to calculate tan of a double.
     *
     * @param  op1 double one to find tan
     * @return  double result of tan(op1)
     */
    public double tan(String op1) {
        if (op1.equals("null")) {
            return -1;
        }
        if (Double.parseDouble(op1) < 0) {
            return -1;

        } else {
            return Math.tan(Math.toRadians(Double.parseDouble(op1)));
        }
    }

    /**
     * Simple method to calculate sin of a double.
     *
     * @param  op1 double one to find sin
     * @return  double result of sin(op1)
     */
    public double sin(String op1) {
        if (op1.equals("null")) {
            return -1;
        }
        if (Double.parseDouble(op1) < 0) {
            return -1;

        } else {
            return Math.sin(Math.toRadians(Double.parseDouble(op1)));
        }
    }

    /**
     * Simple method to calculate cos of a double.
     *
     * @param  op1 double one to find cos
     * @return  double result of cos(op1)
     */
    public double cos(String op1) {
        if (op1.equals("null")) {
            return -1;
        }
        if (Double.parseDouble(op1) < 0) {
            return -1;

        } else {
            return Math.cos(Math.toRadians(Double.parseDouble(op1)));
        }
    }

    /**
     * Simple method to calculate inverse of a double.
     *
     * @param  op1 double one to find inverse
     * @return  double result of 1/op1
     */
    public double inverse(String op1){
        if (op1.equals("null")) {
            return -1;
        } else {
            return 1 / Double.parseDouble(op1);
        }
    }

    /**
     * Simple method to find the square root of the sum of squares of its arguments
     *
     * @param  op1 double one to find square
     * @param  op2 double one to find square
     * @return  double result of hypotenuse of a right angle triangle
     */
    public double hypotenuse(String op1,String op2){
        if (op1.equals("null") || op2.equals("null")) {
            return -1;
        }
            return Math.hypot(Double.parseDouble(op1), Double.parseDouble(op2));
        }
    }

