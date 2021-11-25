package com.example.lab5junit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class CalculatorTest {
    private static double grade=0;

    @BeforeClass
    public static void BeforeClass() {
        // ???
        System.out.println("methods tested;\n testRover\n testGetStateFullName()");
    }

    @AfterClass
    public static void AfterClass() {
        System.out.println(" Grade for Test (out of a possible 35.0 ): " + grade);
    }

    @Test // weight: 7
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
        actual = calc.mul(op1,op2);
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

    @Test // weight: 3
    public void addTest() {
        Calculator calc=new Calculator();

        String op1="3";
        String op2 ="5";
        double expected=8;
        double actual = calc.add(op1,op2);
        assertEquals(expected,actual,0.001);
        grade+=1;

        op1="-123";
        op2 ="-531";
        expected=-654;
        actual = calc.add(op1,op2);
        assertEquals(expected,actual,0.001);
        grade+=1;

        op1="123";
        op2 ="-531";
        expected=-408;
        actual = calc.add(op1,op2);
        assertEquals(expected,actual,0.001);
        grade+=1;
    }

    @Test // weight: 4
    public void diffTest() {
        Calculator calc=new Calculator();

        String op1="20";
        String op2 ="14";
        double expected=6;
        double actual = calc.diff(op1,op2);
        assertEquals(expected,actual,0.001);
        grade+=1;

        op1="-20";
        op2 ="5";
        expected=-25;
        actual = calc.diff(op1,op2);
        assertEquals(expected,actual,0.001);
        grade+=1;

        op1="20";
        op2 ="-5";
        expected=25;
        actual = calc.diff(op1,op2);
        assertEquals(expected,actual,0.001);
        grade+=1;

        op1="-25";
        op2 ="-5";
        expected=-20;
        actual = calc.diff(op1,op2);
        assertEquals(expected,actual,0.001);
        grade+=1;
    }

    @Test // weight: 4
    public void divTest() {
        Calculator calc=new Calculator();

        String op1="100";
        String op2 ="100";
        double expected=1;
        double actual = calc.div(op1,op2);
        assertEquals(expected,actual,0.001);
        grade+=1;

        op1="100";
        op2 ="-100";
        expected=-1;
        actual = calc.div(op1,op2);
        assertEquals(expected,actual,0.001);
        grade+=1;

        op1="0";
        op2 ="-100";
        expected=0;
        actual = calc.div(op1,op2);
        assertEquals(expected,actual,0.001);
        grade+=1;

        op1="-100";
        op2 ="-100";
        expected=1;
        actual = calc.div(op1,op2);
        assertEquals(expected,actual,0.001);
        grade+=1;
    }

    @Test // weight: 4
    public void mulTest() {
        Calculator calc=new Calculator();

        String op1="2";
        String op2 ="100";
        double expected=200;
        double actual = calc.mul(op1,op2);
        assertEquals(expected,actual,0.001);
        grade+=1;

        op1="0";
        op2 ="100";
        expected=0;
        actual = calc.mul(op1,op2);
        assertEquals(expected,actual,0.001);
        grade+=1;

        op1="-100";
        op2 ="-1";
        expected=100;
        actual = calc.mul(op1,op2);
        assertEquals(expected,actual,0.001);
        grade+=1;

        op1="-100";
        op2 ="1";
        expected=-100;
        actual = calc.mul(op1,op2);
        assertEquals(expected,actual,0.001);
        grade+=1;
    }

    @Test // weight: 4
    public void exponTest() {
        Calculator calc=new Calculator();

        String op1="100";
        String op2 ="3";
        double expected=1000000;
        double actual = calc.expon(op1,op2);
        assertEquals(expected,actual,0.001);
        grade+=1;

        op1="100";
        op2 ="-3";
        expected=0.000001;
        actual = calc.expon(op1,op2);
        assertEquals(expected,actual,0.001);
        grade+=1;

        op1="-100";
        op2 ="-2";
        expected=0.0001;
        actual = calc.expon(op1,op2);
        assertEquals(expected,actual,0.001);
        grade+=1;

        op1="100";
        op2 ="0";
        expected=1;
        actual = calc.expon(op1,op2);
        assertEquals(expected,actual,0.001);
        grade+=1;
    }

    @Test // weight: 2
    public void log10Test() {
        Calculator calc=new Calculator();

        String op1="100";
        double expected=2;
        double actual = calc.log10(op1);
        assertEquals(expected,actual,0.001);
        grade+=1;

        op1="1";
        expected=0;
        actual = calc.log10(op1);
        assertEquals(expected,actual,0.001);
        grade+=1;
    }

    @Test // weight: 2
    public void ceilingvalTest() {
        Calculator calc=new Calculator();

        String op1="0.7";
        double expected=1;
        double actual = calc.ceilingval(op1);
        assertEquals(expected,actual,0.001);
        grade+=1;

        op1="-0.12";
        expected=0;
        actual = calc.ceilingval(op1);
        assertEquals(expected,actual,0.001);
        grade+=1;
    }

    @Test // weight: 5
    public void illegalCalculationErrorTest() {
        Calculator calc=new Calculator();
        // Division by 0
        String op1="1";
        String op2="0";
        double expected=-1.1;
        double actual = calc.div(op1, op2);
        assertEquals(expected,actual,0.001);
        grade+=1;

        // Exponentiation where op1 && op2 == 0
        op1="0";
        op2="0";
        expected=-1.1;
        actual = calc.expon(op1, op2);
        assertEquals(expected,actual,0.001);
        grade+=1;

        // Exponentiation where either op1 == 0 && op2 < 0
        op1="0";
        op2="-8";
        expected=-1.2;
        actual = calc.expon(op1, op2);
        assertEquals(expected,actual,0.001);
        grade+=1;

        // Log10 where either op1 < 0
        op1="-1";
        expected=-1.1;
        actual = calc.log10(op1);
        assertEquals(expected,actual,0.001);
        grade+=1;

        // Log10 where either op1 == 0
        op1="0";
        expected=-1.2;
        actual = calc.log10(op1);
        assertEquals(expected,actual,0.001);
        grade+=1;
    }


    /*-----------------------------------Add more test methods for the rest of the Calculator.java Class--------------------------------------*/


}
