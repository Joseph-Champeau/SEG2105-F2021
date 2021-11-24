package com.example.deliverable1fixed;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.deliverable1fixed.databinding.ActivityMemberMainBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class MemberMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private String userID;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMemberMainBinding binding;
    private User user;
    private ListView listView;
    private DatabaseReference referenceClassTypes;
    private DatabaseReference referenceClasses;

    public static ArrayList<Class> classesList;
    private Button sortButton;
    private Button filterButton;
    private LinearLayout row1;
    private LinearLayout row2;
    private LinearLayout row3;
    private LinearLayout row4;
    private LinearLayout sortView;

    boolean sortHidden = true;
    boolean filterHidden = true;

    private String filterSel = "all";
    private String currentSearchText = "";
    private SearchView searchView;
    Toolbar tooly;
    private static final String StringSELECTED_ITEM_ID= "selected";
    DrawerLayout mDrawLayout;
    NavigationView mNavigationView;
final
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    userID = getIntent().getExtras().getString("arg"); // passed from previous page
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
    binding = ActivityMemberMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    NavigationView navigationView1 = (NavigationView) findViewById(R.id.nav_view);
    View headerView = navigationView1.getHeaderView(0);
    TextView navUsername = (TextView) headerView.findViewById(R.id.usernameProfile);
    TextView navEmail = (TextView) headerView.findViewById(R.id.emailProfile);
    mDrawLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    mNavigationView= (NavigationView) findViewById(R.id.nav_view);
    if(mNavigationView!=null){
        mNavigationView.setNavigationItemSelectedListener(this);
    }
}

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    item.setChecked(true);
    Intent intent=null;
    if(item.getItemId()==R.id.nav_gallery){
        mDrawLayout.closeDrawer(GravityCompat.START);
        intent = new Intent(this,NavClasses.class);
        intent.putExtra("arg", userID);
        startActivity(intent);

        return true;
    }

        return false;
    }
}