package com.example.simplecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnPlus, btnMinus, btnDiv,
            btnMul, btnEqual, btnDec, btnClr;

    TextView displayScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        btnDiv = findViewById(R.id.btnDiv);
        btnMul = findViewById(R.id.btnMult);
        btnEqual = findViewById(R.id.btnEqual);
        btnDec = findViewById(R.id.btnDec);
        btnClr = findViewById(R.id.btnClr);


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
        // +, -, *, / need a similar but different approach.
    }
}