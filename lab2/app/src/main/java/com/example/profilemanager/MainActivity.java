package com.example.profilemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button opengooglebtn;
    TextView teamAddress;
    //TextView computationTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        opengooglebtn = (Button)findViewById(R.id.opengooglebtn);

        opengooglebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEql) {
                    displayScreen.setText(displayScreen.getText() + "1");
                    computationTextView.setText(computationTextView.getText() + "1");
                }
            }
        });

        public void OnOpenInGoogleMaps(View view){
            EditText teamAddress= (EditText) findViewById(R.id.teamAddressTextView);

            //Create a Uri from a string. Use the result to create an intent

            Uri gmmIntentUri= Uri.parse("http://maps.google.co.on/maps?g="+teamAddress.getText());

        }
    }
}