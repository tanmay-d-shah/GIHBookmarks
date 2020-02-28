package com.tds.gihbookmarks;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tds.gihbookmarks.NavigationMenuFragments.FAQFragment;
import com.tds.gihbookmarks.NavigationMenuFragments.HomeFragment;
import com.tds.gihbookmarks.NavigationMenuFragments.KnowledgeFragment;
import com.tds.gihbookmarks.NavigationMenuFragments.ProfileFragment;
import com.tds.gihbookmarks.NavigationMenuFragments.ReportBugFragment;
import com.tds.gihbookmarks.NavigationMenuFragments.RequestedItemsFragment;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private DrawerLayout drawer;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user;

    /*private CollectionReference userCollectionReference = db.collection("Users");
    private CollectionReference collectionReference = db.collection("SaleItems");
    private CollectionReference bookcollectionReference = db.collection("Books");

    private List<String> bookIDB;
    private List<String> userIDB;
    List<String> temp;
    List<String> temp2;
    List<String> temp3;
    private List<SaleItems> saleItemsList;
    private StaggeredRecyclerViewAdapter staggeredRecyclerViewAdapter;*/

    public static String branch = "none";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        /*FloatingActionButton floatingActionButton=findViewById(R.id.floatBtn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SellFragment()).commit();

            }
        });*/

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Home");


        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //hamburger icon
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getColor(R.color.drawer));
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                break;

            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
                break;

            case R.id.nav_requestedItems:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new RequestedItemsFragment()).commit();
                break;


            case R.id.nav_bug:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ReportBugFragment()).commit();
                break;

            case R.id.nav_faq:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FAQFragment()).commit();
                break;

            case R.id.nav_logout:
                if (user != null && firebaseAuth != null) {
                    firebaseAuth.signOut();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }

                break;

            case R.id.nav_knowledge:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new KnowledgeFragment()).commit();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    //For adding search functionality
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.clear();

        getMenuInflater().inflate(R.menu.main_menu, menu);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);

        if (searchManager != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        } else {
            Log.d(TAG, "onCreateOptionsMenu (searchView) Ae: searchManager is NULL");
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.comp:
                branch = "computer";
                return true;
            case R.id.mech:
                branch = "mechanical";
                return true;
            case R.id.civil:
                branch = "civil";
                return true;
            case R.id.elec:
                branch = "electrical";
                return true;
            case R.id.it:
                branch = "IT";
                return true;
            default:
                branch = "none";
        }

        return super.onOptionsItemSelected(item);
    }

//    void doTask(String bname) {
//
//        Log.i(TAG, "checkadi doTask: dt bname: " + bname);
//        if (!bname.equals("none")) {
//            Log.i(TAG, "checkadi doTask: dt entered");
//            bookIDB = new ArrayList<>(getUserIdPerBranch(bname));
//            bid = new ArrayList<>();
//
//            collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                    if (task.isSuccessful()) {
//                        for (QueryDocumentSnapshot book : task.getResult()) {
//                            SaleItems item = book.toObject(SaleItems.class);
//                            for (String bID : bookIDB) {
//                                if (item.getItemCode().contains(bID)) {
//                                    Log.i(TAG, "checkadi onComplete: dt " + item.getItemCode());
//                                    bid.add(item);
//                                }
//                            }
//                        }
//                        *//*if (saleItemsList != null) {
//                            Log.i(TAG, "checkadi doTask: dt if entered");
//                            staggeredRecyclerViewAdapter = new StaggeredRecyclerViewAdapter(MainActivity.this, saleItemsList);
////                        toolsRecyclerView.setAdapter(staggeredRecyclerViewAdapter);
//                            staggeredRecyclerViewAdapter.notifyDataSetChanged();
//                        }*//*
//                    }
//                }
//            });
//        }
//    }
/*
    private List<String> getUserIdPerBranch(final String bn) {

        Log.i(TAG, "checkadi u getUserIdPerBranch: bn: " + bn);

        temp = new ArrayList<>();
        temp2 = new ArrayList<>();

        userCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot user : queryDocumentSnapshots) {
                    Log.i(TAG, "checkadi u onSuccess: branch: " + user.get("Branch"));
                    if (user.get("Branch") != null) {
                        if (user.get("Branch").toString().toLowerCase().contains(bn.toLowerCase())) {
                            Log.i(TAG, "checkadi u onSuccess: mainact userID match" + user.get("UserId"));
                            temp.add(Objects.requireNonNull(user.get("UserId")).toString());
                        }
                    }
                }
                temp2 = getBookIdPerUser(temp);
            }
        });

        return temp2;
    }

    private List<String> getBookIdPerUser(final List<String> uIDB) {

        temp3 = new ArrayList<>();
        Log.i(TAG, "getBookIdPerUser: checkadi entered book");
        bookcollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot book : queryDocumentSnapshots) {
                    Book item = book.toObject(Book.class);
                    for (String uID : uIDB) {
                        if (item.getUserId().equals(uID)) {
                            Log.i(TAG, "checkadi b onSuccess: userID match : " + book.get("userID"));
                            Log.i(TAG, "checkadi b onSuccess: bookID: " + book.getId());
                            temp3.add(book.getId());
                        }
                    }
                }
            }
        });

        return temp3;
    }
*/

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
