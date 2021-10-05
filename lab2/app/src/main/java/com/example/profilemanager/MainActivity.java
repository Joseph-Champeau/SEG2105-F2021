package com.example.profilemanager;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button opengooglebtn;
    private TextView teamAddress;
    //TextView computationTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        opengooglebtn = (Button) findViewById(R.id.opengooglebtn);
    }
    public void OnOpenInGoogleMaps(View view){
            EditText teamAddress= (EditText) findViewById(R.id.teamAddressTextView);

            //Create a Uri from a string. Use the result to create an intent

            Uri gmmIntentUri = Uri.parse("http://maps.google.co.in/maps?q=" +teamAddress.getText());

            //Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

            //Make the Intent explicit by setting the Google Maps package
            mapIntent.setPackage ("com.google.android.apps.maps");

            //Attempt to start the acitivty that can handle the Intent
            startActivity(mapIntent);

    }

    ActivityResultLauncher<Intent> profileActivityResultLauncher= registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){

                        //There are no request codes
                        Intent data = result.getData();
                        //adding code here in next slide
                        ImageView avatarImage = (ImageView) findViewById(R.id.logoImage001imageView);
                        String drawableName= "default_empty";
                        switch (data.getIntExtra("imageID", R.id.logoImage001imageView)) {
                            case R.id.logoImage01:
                                drawableName = "fign01_hawks";
                                break;
                            case R.id.logoImage02:
                                drawableName = "fign02_celtics";
                                break;
                            case R.id.logoImage03:
                                drawableName = "fign03_nets";
                                break;
                            case R.id.logoImage04:
                                drawableName = "fign04_hornets";
                                break;
                            case R.id.logoImage05:
                                drawableName = "fign05_wizards";
                                break;
                            case R.id.logoImage06:
                                drawableName = "fign06_raptors";
                                break;
                            case R.id.logoImage07:
                                drawableName = "fign07_spurs";
                                break;
                            case R.id.logoImage08:
                                drawableName = "fign08_jazz";
                                break;
                            case R.id.logoImage09:
                                drawableName = "fign09_kings";
                                break;
                            default:
                                drawableName = "default_empty";
                                break;
                        }
                        int resID = getResources().getIdentifier(drawableName, "drawable",
                                getPackageName());
                        avatarImage.setImageResource(resID);
                    }
                }
            });
    public void OnSetAvatarButton(View view) {

        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        profileActivityResultLauncher.launch(intent);
    }

}