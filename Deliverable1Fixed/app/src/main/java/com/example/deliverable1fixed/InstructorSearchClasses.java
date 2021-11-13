package com.example.deliverable1fixed;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class InstructorSearchClasses extends AppCompatActivity implements View.OnClickListener {
    private static class NumericKeyBoardTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return source;
        }
    }

    private String userID;
    private User user;
    private ListView listView;
    private DatabaseReference referenceClassTypes;
    private DatabaseReference referenceClasses;

    public static ArrayList<Class> classesList;
    private Button sortButton;
    private Button filterButton;
    private LinearLayout filterView1;
    private LinearLayout filterView2;
    private LinearLayout filterView3;
    private LinearLayout filterView4;
    private LinearLayout sortView;

    boolean sortHidden = true;
    boolean filterHidden = true;


    private String selectedFilter = "all";
    private String currentSearchText = "";
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_instructor_search_classes);
        userID = getIntent().getExtras().getString("arg"); // passed from previous page
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
                Toast.makeText(InstructorSearchClasses.this, "Database Error", Toast.LENGTH_LONG).show();
            }
        });


        Resources res = getResources();

        Button home = (Button) findViewById(R.id.homeBtn);
        home.setOnClickListener(this);

        Button create = (Button) findViewById(R.id.createClassBtn);
        create.setOnClickListener(this);

        initSearchWidgets();
        initWidgets();
        setupData();
        setUpList();
        //setUpOnclickListener();
        hideFilter();
        hideSort();

    }
    @Override
    public void onClick(View v) {
        Intent intentView;
        switch (v.getId()) {
            case R.id.createClassBtn:
                intentView = new Intent(InstructorSearchClasses.this, InstructorTeachClass.class);
                intentView.putExtra("arg", userID);
                startActivity(intentView);
                break;
            case R.id.homeBtn:
                intentView = new Intent(InstructorSearchClasses.this, InstructorMain.class);
                intentView.putExtra("arg", userID);
                startActivity(intentView);
                break;
        }
    }

    private void initWidgets() {
        sortButton = (Button) findViewById(R.id.sortButton);
        filterButton = (Button) findViewById(R.id.filterButton);
        filterView1 = (LinearLayout) findViewById(R.id.filterTabsLayout);
        filterView2 = (LinearLayout) findViewById(R.id.filterTabsLayout2);
        filterView3= (LinearLayout) findViewById(R.id.filterTabsLayout3);
        filterView4 = (LinearLayout) findViewById(R.id.filterTabsLayout4);
        sortView = (LinearLayout) findViewById(R.id.sortTabsLayout2);
    }

    private void initSearchWidgets() {
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
                    if(session.instructor.getFullName().toLowerCase().contains(s.toLowerCase())||session.classType.name.toLowerCase().contains(s.toLowerCase())){
                        if(selectedFilter.equals("all")) { filteredClass.add(session); }
                        else {
                            if(session.instructor.getFullName().toLowerCase().contains(selectedFilter.toLowerCase())||session.classType.name.toLowerCase().contains(selectedFilter)){
                            //||session.classType.name.toLowerCase().contains(selectedFilter)
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

        //referenceClasses = FirebaseDatabase.getInstance().getReference("Classes");
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
                Toast.makeText(InstructorSearchClasses.this, "Database Error", Toast.LENGTH_LONG).show();
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
        selectedFilter = "all";
        setAdapter(classesList);
    }



    //Description of particular class
    /*private void setUpOnclickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                Class selectsession = (Class) (listView.getItemAtPosition(position));
                Intent showDetail = new Intent(getApplicationContext(), ClassInfo.class);
                showDetail.putExtra("id",selectsession.classType.name);
                startActivity(showDetail);
            }
        });

    }*/


    private void filterList(String status) {
        selectedFilter = status;
        ArrayList<Class> filteredsessions = new ArrayList<Class>();
        for(Class session: classesList) {
            if(session.instructor.getFullName().toLowerCase().contains(status.toLowerCase())|| session.classType.name.toLowerCase().contains(status.toLowerCase())) {
                if(currentSearchText == "") { filteredsessions.add(session);
                } else {
                    if(session.instructor.getFullName().toLowerCase().contains(currentSearchText.toLowerCase())) { filteredsessions.add(session); }
                }
            }
        }

        setAdapter(filteredsessions);
    }
    public void yogaFilterTapped(View view) { filterList("Yoga"); }
    public void cyclingFilterTapped(View view) { filterList("Cycling"); }
    public void zumbaFilterTapped(View view) { filterList("Zumba"); }
    public void aquaFilterTapped(View view) { filterList("Aqua "); }
    public void hiitFilterTapped(View view) { filterList("HIIT N Athletics"); }
    public void danceFilterTapped(View view) { filterList("dance"); }
    public void cardioFilterTapped(View view) { filterList("cardio"); }
    public void pilatesFilterTapped(View view) {filterList("cardio");}


    public void showFilterTapped(View view) {
        if(filterHidden == true) {
            filterHidden = false;
            showFilter();
        }
        else {
            filterHidden = true;
            hideFilter();
        }
    }

    public void showSortTapped(View view) {
        if(sortHidden == true) {
            sortHidden = false;
            showSort();
        }
        else {
            sortHidden = true;
            hideSort();
        }
    }

    private void hideFilter() {
        searchView.setVisibility(View.GONE);
        filterView1.setVisibility(View.GONE);
        filterView2.setVisibility(View.GONE);
        filterView3.setVisibility(View.GONE);
        filterView4.setVisibility(View.VISIBLE);
        filterButton.setText("FILTER");
    }

    private void showFilter() {
        searchView.setVisibility(View.VISIBLE);
        filterView1.setVisibility(View.VISIBLE);
        filterView2.setVisibility(View.VISIBLE);
        filterView3.setVisibility(View.VISIBLE);
        filterView4.setVisibility(View.GONE);
        filterButton.setText("HIDE");
    }

    private void hideSort() {
        sortView.setVisibility(View.GONE);
        sortButton.setText("SORT");
    }

    private void showSort() {
        sortView.setVisibility(View.VISIBLE);
        sortButton.setText("HIDE");
    }

    public void capacityTapped(View view) {
        Collections.sort(classesList, Class.capacityAscending);
        checkForFilter();
    }

    public void capacityDESCTapped(View view) {
        Collections.sort(classesList, Class.capacityAscending);
        Collections.reverse(classesList);
        checkForFilter();
    }

    public void InsensityTapped(View view) {
        Collections.sort(classesList, Class.levelAscending);
        checkForFilter();
    }

    public void activtynameSort(View view) {
        Collections.sort(classesList, Class.activtyAscending);
        //Collections.reverse(classesList);
        checkForFilter();
    }

    private void checkForFilter() {
        if(selectedFilter.equals("all")) {
            if(currentSearchText.equals("")) {
                setAdapter(classesList);
            } else {
                ArrayList<Class> filteredsessions = new ArrayList<Class>();
                for(Class session: classesList) {
                    if(session.getName().toLowerCase().contains(currentSearchText)) { filteredsessions.add(session); }
                }
                setAdapter(filteredsessions);
            }
        } else { filterList(selectedFilter); }
    }
}
