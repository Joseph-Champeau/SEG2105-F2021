package com.example.lab5junit;

import static java.lang.Math.pow;

public class Calculator {    public double add(String op1, String op2) {
    if (op1.equals("null")||op2.equals("null")){
        return -1;

    }

    return Double.parseDouble(op1) + Double.parseDouble(op2);
}

    public double diff(String op1, String op2) {
        if (op1.equals("null")||op2.equals("null")){
            return -1;

        }
        return Double.parseDouble(op1) - Double.parseDouble(op2);
    }

    public double div(String op1, String op2) {
        if (op1.equals("null")||op2.equals("null")){
            return -1;

        }
        // if (op2 == 0) return 0;
        return Double.parseDouble(op1) / Double.parseDouble(op2);
    }

    public double expon(String op1, String op2){
        if (op1.equals("null")||op2.equals("null")){
            return -1;

        }
        return pow(Double.parseDouble(op1), Double.parseDouble(op2));
    }

    public double log10(String op1){
        if (op1.equals("null")){
            return -1;

        }
        return Math.log10(Double.parseDouble(op1));
    }
    public double ceilingval(String op1){
        if (op1.equals("null")){
            return -1;

        }
        return Math.ceil(Double.parseDouble(op1));
    }

}
