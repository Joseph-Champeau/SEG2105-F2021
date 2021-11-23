package com.example.deliverable1fixed;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
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

public class MemberMain extends AppCompatActivity implements View.OnClickListener{
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
    referenceClassTypes = FirebaseDatabase.getInstance().getReference("ClassTypes");
    referenceClasses = FirebaseDatabase.getInstance().getReference("Classes");
    classesList = new ArrayList<>();
    DatabaseReference referenceUsers = FirebaseDatabase.getInstance().getReference("Users");
    referenceUsers.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            user = snapshot.getValue(User.class);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(MemberMain.this, "Database Error", Toast.LENGTH_LONG).show();
        }
    });


    reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            User userProfile = snapshot.getValue(User.class);
            if(userProfile != null){
                String username = userProfile.getUsername();
                String email = userProfile.getEmail();
                navUsername.setText(username);
                navEmail.setText(email);
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(MemberMain.this, "Database Error", Toast.LENGTH_LONG).show();
        }
    });
        setSupportActionBar(binding.appBarMemberMain.toolbar);
        binding.appBarMemberMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_member_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.member_main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_member_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    private static class NumericKeyBoardTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return source;
        }
    }

    //@Override
    public void onClick(View v) {
        Intent intentView;
        switch (v.getId()) {
            case R.id.addMoreClassesbtn:
                intentView = new Intent(MemberMain.this, HomeScreen.class);
                intentView.putExtra("arg", userID);
                startActivity(intentView);
                break;
            case R.id.homeBtn12:
                intentView = new Intent(MemberMain.this, FrontScreen.class);
                intentView.putExtra("arg", userID);
                startActivity(intentView);
                break;
            case R.id.MemberSearchClassesBtn:
                Intent intentSearch = new Intent(MemberMain.this, MemberSearchClasses.class);
                intentSearch.putExtra("arg", userID);
                startActivity(intentSearch);
                break;
        }
        /*switch (v.getId()){
            case R.id.MemberSearchClassesBtn:
                Intent intentSearch = new Intent(MemberMain.this, MemberSearchClasses.class);
                intentSearch.putExtra("arg", userID);
                startActivity(intentSearch);
                break;
            case R.id.instructorViewClassMembersBtn:
                Intent intentViewMembers = new Intent(MemberMain.this, InstructorViewClassMembers.class);
                intentViewMembers.putExtra("arg", userID);
                startActivity(intentViewMembers);
                break;
            case R.id.instructorTeachClassesBtn:
                Intent intentTeach = new Intent(MemberMain.this, InstructorTeachClass.class);
                intentTeach.putExtra("arg", userID);
                startActivity(intentTeach);
                break;
            case R.id.instructorEditClassesBtn:
                Intent intentEdit = new Intent(MemberMain.this, InstructorEditClasses.class);
                intentEdit.putExtra("arg", userID);
                startActivity(intentEdit);
                break;
            case R.id.instructorDeleteClassesBtn:
                Intent intentDelete = new Intent(MemberMain.this, InstructorDeleteClasses.class);
                intentDelete.putExtra("arg", userID);
                startActivity(intentDelete);
                break;
            case R.id.instructorBackMain:
                Intent intentBackMain = new Intent(MemberMain.this, HomeScreen.class);
                intentBackMain.putExtra("arg", userID);
                startActivity(intentBackMain);
                break;
        }*/

    }

    private void setupLayout() {
        sortButton = (Button) findViewById(R.id.sortButton);
        filterButton = (Button) findViewById(R.id.filterButton);
        row1 = (LinearLayout) findViewById(R.id.filterTabsLayout);
        row2 = (LinearLayout) findViewById(R.id.filterTabsLayout2);
        row3= (LinearLayout) findViewById(R.id.filterTabsLayout3);
        row4 = (LinearLayout) findViewById(R.id.filterTabsLayout4);
        sortView = (LinearLayout) findViewById(R.id.sortTabsLayout2);
    }

    private void searching() {
        searchView = (SearchView) findViewById(R.id.ClassListSearchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                currentSearchText = s;
                ArrayList<Class> filteredClass = new ArrayList<Class>();
                for(Class session: classesList) {
                    //Sort by instructor
                    if(session.instructor.getFullName().toLowerCase().contains(s.toLowerCase())||session.classType.getName().toLowerCase().contains(s.toLowerCase())){
                        if(filterSel.equals("all")) { filteredClass.add(session); }
                        else {
                            if(session.instructor.getFullName().toLowerCase().contains(filterSel.toLowerCase())||session.classType.getName().toLowerCase().contains(filterSel)){
                                //||session.classType.name.toLowerCase().contains(filterSel)
                                filteredClass.add(session);
                            }
                        }
                    }
                }
                setAdapter(filteredClass);

                return false;
            }
        });
    }
    private void setupData() {
        referenceClasses.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Class classObject = snapshot.getValue(Class.class);
                    if(classObject != null) {
                        classesList.add(classObject);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MemberMain.this, "Database Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setUpList() {
        listView = (ListView) findViewById(R.id.classListView);
        setAdapter(classesList);
    }


    private void setAdapter(ArrayList<Class> ClassList) {
        ClassAdapter adapter = new ClassAdapter(getApplicationContext(), 0, ClassList);
        listView.setAdapter(adapter);
    }
    public void allFilterTapped(View view) {
        filterSel = "all";
        setAdapter(classesList);
    }

    private void strainer(String status) {
        filterSel = status;
        ArrayList<Class> filteredsessions = new ArrayList<Class>();
        for(Class session: classesList) {
            if(session.instructor.getFullName().toLowerCase().contains(status.toLowerCase())|| session.classType.getName().toLowerCase().contains(status.toLowerCase())) {
                if(currentSearchText == "") { filteredsessions.add(session);
                } else {
                    if(session.instructor.getFullName().toLowerCase().contains(currentSearchText.toLowerCase())) { filteredsessions.add(session); }
                }
            }
        }

        setAdapter(filteredsessions);
    }
    /*-------Set of Classes Available at the Gym ---------*/
    public void yogaFilterTapped(View view) { strainer("Yoga"); }
    public void cyclingFilterTapped(View view) { strainer("Cycling"); }
    public void zumbaFilterTapped(View view) { strainer("Zumba"); }
    public void aquaFilterTapped(View view) { strainer("Aqua "); }
    public void hiitFilterTapped(View view) { strainer("HIIT N Athletics"); }
    public void danceFilterTapped(View view) { strainer("dance"); }
    public void cardioFilterTapped(View view) { strainer("cardio"); }
    public void pilatesFilterTapped(View view) {strainer("cardio");}


    public void showFilterClicked(View view) {
        if(filterHidden == true) {
            filterHidden = false;
            showFilter();
        }
        else {
            filterHidden = true;
            closeFilter();
        }
    }

    public void showSortClick(View view) {
        if(sortHidden == true) {
            sortHidden = false;
            showSort();
        }
        else {
            sortHidden = true;
            closeSort();
        }
    }
    /* Filter Button Checked or Unchecked Methods*/
    private void closeFilter() {
        searchView.setVisibility(View.GONE);
        row1.setVisibility(View.GONE);
        row2.setVisibility(View.GONE);
        row3.setVisibility(View.GONE);
        row4.setVisibility(View.VISIBLE);
        filterButton.setText("FILTER");
    }

    private void showFilter() {
        searchView.setVisibility(View.VISIBLE);
        row1.setVisibility(View.VISIBLE);
        row2.setVisibility(View.VISIBLE);
        row3.setVisibility(View.VISIBLE);
        row4.setVisibility(View.GONE);
        filterButton.setText("HIDE");
    }
    /* Sort Button Checked or Unchecked Mehtods*/
    private void closeSort() {
        sortView.setVisibility(View.GONE);
        sortButton.setText("SORT");
    }

    private void showSort() {
        sortView.setVisibility(View.VISIBLE);
        sortButton.setText("HIDE");
    }

    /*---------------------------Sorting Methods---------------------*/
    public void capacityTapped(View view) {
        Collections.sort(classesList, Class.capacityAscending);
        filterChecker();
    }

    public void capacityDESCTapped(View view) {
        Collections.sort(classesList, Class.capacityAscending);
        Collections.reverse(classesList);
        filterChecker();
    }

    public void InsensityTapped(View view) {
        Collections.sort(classesList, Class.levelAscending);
        filterChecker();
    }

    public void activtynameSort(View view) {
        Collections.sort(classesList, Class.activtyAscending);
        //Collections.reverse(classesList);
        filterChecker();
    }
    /*----------------Checker for any Filter Changes----------*/
    private void filterChecker() {
        if(filterSel.equals("all")) {
            if(currentSearchText.equals("")) {
                setAdapter(classesList);
            } else {
                ArrayList<Class> filteredsessions = new ArrayList<Class>();
                for(Class session: classesList) {
                    if(session.getName().toLowerCase().contains(currentSearchText)) { filteredsessions.add(session); }
                }
                setAdapter(filteredsessions);
            }
        } else { strainer(filterSel); }
    }
}