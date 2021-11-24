package com.example.lab5;

import static java.lang.Math.pow;

public class Calculator {

    public Number add(String op1, String op2) {
        if (op1==null||op2==null){
            return null;

        }
        
        return Double.parseDouble(op1) + Double.parseDouble(op2);
    }

    public Number diff(String op1, String op2) {
        if (op1==null||op2==null){
            return null;

        }
        return Double.parseDouble(op1) - Double.parseDouble(op2);
    }

    public Number div(String op1, String op2) {
        if (op1==null||op2==null){
            return null;

        }
        // if (op2 == 0) return 0;
        return Double.parseDouble(op1) / Double.parseDouble(op2);
    }

    public Number expon(String op1, String op2){
        if (op1==null||op2==null){
            return null;

        }
        return pow(Double.parseDouble(op1), Double.parseDouble(op2));
    }

    public Number log10(String op1){
        if (op1==null){
            return null;

        }
        return Math.log10(Double.parseDouble(op1));
    }
    public Number ceilingval(String op1){
        if (op1==null){
            return null;

        }
        return Math.ceil(Double.parseDouble(op1));
    }

}
