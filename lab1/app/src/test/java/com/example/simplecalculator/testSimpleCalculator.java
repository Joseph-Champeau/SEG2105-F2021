package com.example.simplecalculator;
import static com.example.simplecalculator.R.id.displayScreen;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import android.widget.Button;
import android.widget.TextView;

import junit.framework.TestCase;
import static com.example.simplecalculator.R.id.*;

import android.widget.TextView;

import junit.framework.TestCase;

public class testSimpleCalculator {
    private static double grade=0;




    public class CalculatorTest extends TestCase {

        public void testOnCreate() {
            System.out.println(displayScreen);
            //TextView edit= findViewById(displayScreen);
            //assertEquals("",displayScreen.getText());
            //assertEquals("", btn7.getText());

        }
    }
    @BeforeClass
    public static void BeforeClass() {
        System.out.println("methods tested;\n testRover\n testGetStateFullName()");

    }

    @AfterClass
    public static void AfterClass() {
        System.out.println(" Grade for Test (out of a possible 114 ): " + grade);
    }
    @Test
    public void testAddition() {
       btn7.performClick();
       assertEquals(7, displayScreen);
       System.out.println("Great work");
    }


    /**lunar= new Rover();
        assertEquals("Should be Null","Null",lunar.getStateVehiculeOn().toString());
        grade+=1.0;
        assertNotNull(lunar.getState());
        grade+=1.0;



    }*/

    //@Test
    //void testGetStateFullName() {

    //}
}
