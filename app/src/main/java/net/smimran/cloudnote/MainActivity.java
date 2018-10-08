package net.smimran.cloudnote;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private DrawerLayout drawerLayout;
    NavigationView navigationView;

    private FirebaseAuth auth;


    public static ArrayList <String> categoriesList = new ArrayList <String>();

    @Override
    protected void onStart() {
        super.onStart();
        loadCategoryNameintoCategoriesList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCategoryNameintoCategoriesList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        auth = FirebaseAuth.getInstance();

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        setUpNavheader();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.signOut_nav:
                Toast.makeText(this, "Sign out", Toast.LENGTH_SHORT).show();
                break;
//            case R.id.signoutID:
//                Snackbar snackbar = Snackbar
//                        .make(drawerLayout, "Sign out completed", Snackbar.LENGTH_LONG);
//                snackbar.show();
//                AuthUI.getInstance()
//                        .signOut(MainActivity.this)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                startActivity(new Intent(MainActivity.this, LoginSActivity.class));
//                                finish();
//                            }
//                        });
//                break;
//
//            case R.id.addNoteID:
//                callAddActivity((View) item.getActionView());
//                break;
//
//            case R.id.allNoteID:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AllNotes()).commit();
//                navigationView.setCheckedItem(R.id.allNoteID);
//                break;
//
//            case R.id.categoriesID:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Categories()).commit();
//                navigationView.setCheckedItem(R.id.categoriesID);
//                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    public void setUpNavheader() {
        FirebaseUser user = auth.getCurrentUser();
        NavigationView navigationView = findViewById(R.id.nav_view);

        View hView = navigationView.getHeaderView(0);

        TextView nameNav = hView.findViewById(R.id.name_navHeader);
        TextView emailNav = hView.findViewById(R.id.email_navHeader);
        ImageView profile = hView.findViewById(R.id.image_navHeader);

        nameNav.setText(user.getDisplayName());
        emailNav.setText(user.getEmail());
        Glide.with(this).load(user.getPhotoUrl()).into(profile);
    }

    public void callAddActivity(View view) {
        FirebaseUser user = auth.getCurrentUser();
        Intent intent = new Intent(getApplicationContext(), AddNoteActivity.class);
        intent.putExtra("USERID", user.getUid());
        startActivity(intent);
    }

    public void loadCategoryNameintoCategoriesList() {
        FirebaseUser user = auth.getCurrentUser();
        db.collection(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            int dontAdd;

            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                    Note note = documentSnapshot.toObject(Note.class);

                    dontAdd = 0;

                    for (int i = 0; i < MainActivity.categoriesList.size(); i++) {
                        if (MainActivity.categoriesList.get(i).toLowerCase().trim().equals(note.getCategory().toLowerCase().trim())) {
                            dontAdd = 1;
                            break;
                        }
                    }

                    if (dontAdd == 0) {
                        MainActivity.categoriesList.add(note.getCategory().trim());
                    }
                }
            }
        });
    }
}
