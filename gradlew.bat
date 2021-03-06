package net.smimran.savepost;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class Categories extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FirebaseAuth auth;

    CategoriesAdapter categoriesAdapter;

    RecyclerView recyclerView;

    public static ArrayList<String> tmpcategoriesList = new ArrayList<String>();

    @Override
    public void onStart() {
        super.onStart();
        categoriesAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        categoriesAdapter.stopListening();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        auth = FirebaseAuth.getInstance();

        View view = inflater.inflate(R.layout.fragment_categories,container,false);

        recyclerView = view.findViewById(R.id.recyclerID);
        setUpRecycleView();

        return view;
    }

    public void setUpRecycleView() {
        FirebaseUser user = auth.getCurrentUser();
        Query query = db.collection(user.getUid());

    }

}
                                                                                                                                                                                                                                                                                                                 