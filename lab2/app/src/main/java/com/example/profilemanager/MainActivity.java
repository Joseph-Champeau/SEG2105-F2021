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
                    EditText teamName= (EditText) findViewById(R.id.teamNameFieldTextView);
                    TextView teamName1= (TextView) findViewById(R.id.teamNametextView);
                    TextView teamHomeArena= (TextView) findViewById(R.id.teamArenatextView);
                    if (result.getResultCode() == Activity.RESULT_OK){

                        //There are no request codes
                        Intent data = result.getData();
                        //adding code here in next slide
                        ImageView avatarImage = (ImageView) findViewById(R.id.logoImage001imageView);
                        String drawableName= "default_empty";
                        switch (data.getIntExtra("imageID", R.id.logoImage001imageView)) {
                            case R.id.logoImage01:
                                drawableName = "fign01_hawks";
                                teamName1.setText("Team Name: Atlanta Hawks");
                                teamHomeArena.setText("Team Home Arena: 1 State Farm Drive, Atlanta, Georgia 30303");
                                break;
                            case R.id.logoImage02:
                                drawableName = "fign02_celtics";
                                teamName1.setText("Team Name: Boston Celtics");
                                teamHomeArena.setText("Team Home Arena: 100 Legends Way, Boston, Massachusetts 02114");
                                break;
                            case R.id.logoImage03:
                                drawableName = "fign03_nets";
                                teamName1.setText("Team Name: Brooklyn Nets");
                                teamHomeArena.setText("Team Home Arena: 620 Atlantic Avenue, Brooklyn, New York");
                                break;
                            case R.id.logoImage04:
                                drawableName = "fign04_hornets";
                                teamName1.setText("Team Name: Charlotte Hornets");
                                teamHomeArena.setText("Team Home Arena:  333 East Trade Street, Charlotte, North Carolina 28202");
                                break;
                            case R.id.logoImage05:
                                drawableName = "fign05_wizards";
                                teamName1.setText("Team Name: Washington Wizards");
                                teamHomeArena.setText("Team Home Arena: 601 F. Street Northwest, Washington D.C. 20004");
                                break;
                            case R.id.logoImage06:
                                drawableName = "fign06_raptors";
                                teamName1.setText("Team Name: Toronto Raptors");
                                teamHomeArena.setText("Team Home Arena: 40 Bay Street, Toronto, Ontario M5J 2Z8");
                                break;
                            case R.id.logoImage07:
                                drawableName = "fign07_spurs";
                                teamName1.setText("Team Name: San Antonio Spurs");
                                teamHomeArena.setText("Team Home Arena: 1 AT&T Center Parkway, San Antonio, Texas 78219");
                                break;
                            case R.id.logoImage08:
                                drawableName = "fign08_jazz";
                                teamName1.setText("Team Name: Utah Jazz");
                                teamHomeArena.setText("Team Home Arena: 301 S Temple, Salt Lake City, Utah 84101 ");
                                break;
                            case R.id.logoImage09:
                                drawableName = "fign09_kings";
                                teamName1.setText("Team Name: Sacramento Kings");
                                teamHomeArena.setText("Home Arena: 500 David J. Stern Walk, Sacramento, California");
                                break;
                            default:
                                drawableName = "default_empty";
                                teamHomeArena.setText("");
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