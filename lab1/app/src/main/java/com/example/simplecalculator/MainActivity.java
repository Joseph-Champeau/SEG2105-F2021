package com.example.simplecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnPlus, btnMinus, btnDiv,
            btnMul, btnEqual, btnDec, btnClr, btnDel;

    TextView displayScreen;
    TextView computationTextView;

    private enum Operator {none, add, minus, multiply, divide}
    private double d1 = 0, d2 = 0;
    private Operator op = Operator.none;
    private boolean checkDec = true; // checks if a decimal is already in place
    private boolean checkEql = true; // check if equal button has been pressed already
    private String operationsSign = "+*-/"; //values of possibly operations

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
        btnDel = (Button)findViewById(R.id.btnDel);
        displayScreen= (TextView) findViewById(R.id.displayScreen);
        computationTextView= (TextView) findViewById(R.id.computationTextView);


        // Copy paste code below for each number.
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEql) {
                    displayScreen.setText(displayScreen.getText() + "1");
                    computationTextView.setText(computationTextView.getText() + "1");
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEql) {
                    displayScreen.setText(displayScreen.getText() + "2");
                    computationTextView.setText(computationTextView.getText() + "2");
                }
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEql) {
                    displayScreen.setText(displayScreen.getText() + "3");
                    computationTextView.setText(computationTextView.getText() + "3");
                }
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEql) {
                    displayScreen.setText(displayScreen.getText() + "4");
                    computationTextView.setText(computationTextView.getText() + "4");
                }
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEql) {
                    displayScreen.setText(displayScreen.getText() + "5");
                    computationTextView.setText(computationTextView.getText() + "5");
                }
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEql) {
                    displayScreen.setText(displayScreen.getText() + "6");
                    computationTextView.setText(computationTextView.getText() + "6");
                }
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEql) {
                    displayScreen.setText(displayScreen.getText() + "7");
                    computationTextView.setText(computationTextView.getText() + "7");
                }
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEql) {
                    displayScreen.setText(displayScreen.getText() + "8");
                    computationTextView.setText(computationTextView.getText() + "8");
                }
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEql) {
                    displayScreen.setText(displayScreen.getText() + "9");
                    computationTextView.setText(computationTextView.getText() + "9");
                }
            }

        });
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEql) {
                    displayScreen.setText(displayScreen.getText() +"0");
                    computationTextView.setText(computationTextView.getText() + "0");
                }
            }

        });


        btnEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(op != Operator.none) {
                    if (displayScreen.getText().toString().equals("")) {
                        d2 = 0.0; // defaults value to 0 if = is pressed while display screen = ""
                    } else {
                        d2 = Double.parseDouble(displayScreen.getText().toString());
                    }
                    double result;
                    result = 0;
                    if (op == Operator.add) {
                        result = d1 + d2;
                    } else if (op == Operator.minus) {
                        result = d1 - d2;
                    } else if (op == Operator.multiply) {
                        result = d1 * d2;
                    } else if (op == Operator.divide) {
                        result = d1 / d2;
                    }

                    op = Operator.none;
                    d1 = result;
                    if ((result - (int) result) != 0) {
                        result = Math.round(result * 1000d) / 1000d;
                        displayScreen.setText(String.valueOf(result));
                        computationTextView.setText(String.valueOf(result));
                    }
                    else {
                        result = Math.round(result * 1000d) / 1000d;
                        displayScreen.setText(String.valueOf((int) result));
                        computationTextView.setText(String.valueOf((int) result));
                    }
                    checkEql = false; // limits the use of any key but operators (+,-,/ and *)

                }
            }
        });
        btnMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String obj = computationTextView.getText().toString();
                char last= obj.charAt(obj.length()-1);
                if (operationsSign.contains(String.valueOf(last))){
                    obj = computationTextView.getText().toString(); //assign the display screen as a character sequence
                    computationTextView.setText(obj.subSequence(0 ,obj.length()-1)); //upon click, it will remove the last value of the displayscreen

                } else{
                    d1 = Double.parseDouble(displayScreen.getText().toString());
                }
                    op = Operator.multiply;
                    displayScreen.setText("");
                    computationTextView.setText(computationTextView.getText() + "*");
                    checkDec = true;
                    checkEql = true;
            }

        });
        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String obj = computationTextView.getText().toString();
                char last= obj.charAt(obj.length()-1);
                if (operationsSign.contains(String.valueOf(last))){
                    obj = computationTextView.getText().toString(); //assign the display screen as a character sequence
                    computationTextView.setText(obj.subSequence(0 ,obj.length()-1)); //upon click, it will remove the last value of the displayscreen

                } else{
                    d1 = Double.parseDouble(displayScreen.getText().toString());
                }
                    op = Operator.divide;
                    displayScreen.setText("");
                    computationTextView.setText(computationTextView.getText() + "/");
                    checkDec = true;
                    checkEql = true;
            }
        });
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String obj = computationTextView.getText().toString();
                char last= obj.charAt(obj.length()-1);
                if (operationsSign.contains(String.valueOf(last))){
                    obj = computationTextView.getText().toString(); //assign the display screen as a character sequence
                    computationTextView.setText(obj.subSequence(0 ,obj.length()-1)); //upon click, it will remove the last value of the displayscreen

                } else{
                    d1 = Double.parseDouble(displayScreen.getText().toString());
                }
                    op = Operator.add;
                    displayScreen.setText("");
                    computationTextView.setText(computationTextView.getText() + "+");
                    checkDec = true;
                    checkEql = true;
            }
        });
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String obj = computationTextView.getText().toString();
                char last= obj.charAt(obj.length()-1);
                if (operationsSign.contains(String.valueOf(last))){
                    obj = computationTextView.getText().toString(); //assign the display screen as a character sequence
                    computationTextView.setText(obj.subSequence(0 ,obj.length()-1)); //upon click, it will remove the last value of the displayscreen

                } else{
                    d1 = Double.parseDouble(displayScreen.getText().toString());
                }
                    op=Operator.minus;
                    displayScreen.setText("");
                    computationTextView.setText(computationTextView.getText() + "-");
                    checkDec = true;
                    checkEql = true;
            }
        });

        btnDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkDec && checkEql && (!(displayScreen.getText().toString().equals("")))) {
                    displayScreen.setText(displayScreen.getText() + ".");
                    computationTextView.setText(computationTextView.getText() + ".");
                    checkDec = false;
                } else if (checkDec && checkEql) {
                    displayScreen.setText(displayScreen.getText() + "0.");
                    computationTextView.setText(computationTextView.getText() + "0.");
                    checkDec = false;
                }
            }
        });

        btnClr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v ) {
                d1=Double.NaN;
                d2=Double.NaN;
                displayScreen.setText("");
                computationTextView.setText("");
                checkDec = true;
                checkEql = true;
                op = Operator.none;
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v ) {
                if (checkEql) {
                    if (displayScreen.getText().length() > 0) { //counts the number of digits/operations within the display screen
                        CharSequence obj = displayScreen.getText().toString(); //assign the display screen as a character sequence
                        if (obj.toString().substring(obj.toString().length() - 1).equals(".")) {
                            checkDec = true;
                        }
                        displayScreen.setText(obj.subSequence(0, obj.length() - 1)); // upon click, it will remove the last value of the displayScreen
                        CharSequence objComp = computationTextView.getText().toString(); // assign the computationTextView screen as a character sequenc
                        computationTextView.setText(objComp.subSequence(0, objComp.length() - 1)); //upon click, it will remove the last value of the computationTextView
                    } else {
                        displayScreen.setText("");
                        computationTextView.setText(computationTextView.getText().toString());
                    }
                }
            }
        });
    }
}