package com.example.lab5junit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class CalculatorTest {
    private static double grade=0;

    @BeforeClass
    public static void BeforeClass() {
        System.out.println("methods tested;\n testRover\n testGetStateFullName()");

    }

    @AfterClass
    public static void AfterClass() {
        System.out.println(" Grade for Test (out of a possible 8 ): " + grade);
    }
    @Test
    public void nullTest() {
        Calculator calc=new Calculator();
        String op1="null";
        String op2 ="null";
        double expected=-1;
        double actual = calc.add(op1,op2);
        assertEquals(expected,actual,0.001);
        grade+=1;
        actual = calc.diff(op1,op2);
        assertEquals(expected,actual,0.001);
        grade+=1;
        actual = calc.div(op1,op2);
        assertEquals(expected,actual,0.001);
        grade+=1;
        actual = calc.expon(op1,op2);
        assertEquals(expected,actual,0.001);
        grade+=1;
        actual = calc.log10(op1);
        assertEquals(expected,actual,0.001);
        grade+=1;
        actual = calc.ceilingval(op1);
        assertEquals(expected,actual,0.001);
        grade+=1;

    }
    @Test
    public void addTest() {
        Calculator calc=new Calculator();
        String op1="3";
        String op2 ="5";
        double expected=8;
        double actual = calc.add(op1,op2);
        assertEquals(expected,actual,0.001);
        grade+=1;

    }

    @Test
    public void addTest2() {
        Calculator calc=new Calculator();
        String op1="-123";
        String op2 ="-531";
        double expected=-654;
        double actual = calc.add(op1,op2);
        assertEquals(expected,actual,0.001);
        grade+=1;
    }

    /*-----------------------------------Add more test methods for the rest of the Calculator.java Class--------------------------------------*/



}
