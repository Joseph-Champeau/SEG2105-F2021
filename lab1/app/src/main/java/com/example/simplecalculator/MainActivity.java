package com.example.simplecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnPlus, btnMinus, btnDiv,
            btnMul, btnEqual, btnDec, btnClr;


    TextView displayScreen ;
    TextView computationTextView;
    private final char DIV = '/';
    private final char MUL = '*';
    private final char MINUS= '-';
    private final char PLU = '+';
    private char OPERATION;
    private double val1= Double.NaN;
    private double val2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn0 = (Button)findViewById(R.id.btn0);
        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        btn3 = (Button)findViewById(R.id.btn3);
        btn4 = (Button)findViewById(R.id.btn4);
        btn5 = (Button)findViewById(R.id.btn5);
        btn6 = (Button)findViewById(R.id.btn6);
        btn7 = (Button)findViewById(R.id.btn7);
        btn8 = (Button)findViewById(R.id.btn8);
        btn9 = (Button)findViewById(R.id.btn9);
        btnPlus = (Button)findViewById(R.id.btnPlus);
        btnMinus = (Button)findViewById(R.id.btnMinus);
        btnDiv = (Button)findViewById(R.id.btnDiv);
        btnMul = (Button)findViewById(R.id.btnMult);
        btnEqual = (Button)findViewById(R.id.btnEqual);
        btnDec = (Button)findViewById(R.id.btnDec);
        btnClr = (Button)findViewById(R.id.btnClr);
        displayScreen= (TextView) findViewById(R.id.displayScreen);
        computationTextView= (TextView) findViewById(R.id.computationTextView);


        // Copy paste code below for each number.
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayScreen.setText(displayScreen.getText() + "1");
            }

        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayScreen.setText(displayScreen.getText() + "2");
            }

        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayScreen.setText(displayScreen.getText() + "3");
            }

        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayScreen.setText(displayScreen.getText() + "4");
            }

        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(displayScreen);
                displayScreen.setText(displayScreen.getText() + "5");
            }

        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayScreen.setText(displayScreen.getText() + "6");
            }

        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayScreen.setText(displayScreen.getText() + "7");
            }

        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayScreen.setText(displayScreen.getText() + "8");
            }

        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayScreen.setText(displayScreen.getText() + "9");
            }

        });


        btnEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
                displayScreen.setText(displayScreen.getText() + "9");
                //displayScreen.setText(null);

            }

            // +, -, *, need a similar to above but different approach. This is wrong.
        });
        btnMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayScreen.setText(displayScreen.getText() + "*");
            }

        });
        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayScreen.setText(displayScreen.getText() + "/");
            }

        });
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayScreen.setText(displayScreen.getText() + "+");
            }

        });
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayScreen.setText(displayScreen.getText() + "-");
            }

        });

        btnDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayScreen.setText(displayScreen.getText() + ".");
            }

        });

        btnClr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v ) {
                if ( displayScreen.getText().length()>0){ //counts the number of digits/operations within the display screen
                    CharSequence obj = displayScreen.getText().toString(); //assign the display screen as a character sequence
                    displayScreen.setText(obj.subSequence(0 ,obj.length()-1)); //upon click, it will remove the last value of the displayscreen
                } else {
                    val1=Double.NaN;
                    val2=Double.NaN;
                    displayScreen.setText(null);

                }
            }
        });
    }
}