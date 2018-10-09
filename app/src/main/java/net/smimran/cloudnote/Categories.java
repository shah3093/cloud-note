package net.smimran.cloudnote;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Categories extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FirebaseAuth auth;

    CategoriesAdapter categoryAdapter;

    RecyclerView recyclerView;

    private String numberofnote;

    public Map <String, Object> categoryDatatmp;

    public ArrayList <Map <String, Object>> categoryData = new ArrayList <Map <String, Object>>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();

        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        recyclerView = view.findViewById(R.id.recyclerID);
        setUpRecycleView();

        return view;
    }


    public void setUpRecycleView() {

        FirebaseUser user = auth.getCurrentUser();

        for (int i = 0; i < MainActivity.categoriesList.size(); i++) {

            categoryDatatmp = new HashMap <>();
            int yourCounter;


            Task <QuerySnapshot> query = db.collection(user.getUid()).
                    whereEqualTo("category", MainActivity.categoriesList.get(i)).get()
                    .addOnSuccessListener(
                            new OnSuccessListener <QuerySnapshot>() {
                        String yourCounter;

                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            yourCounter = Integer.toString(queryDocumentSnapshots.size());
                        }
            });

            categoryDatatmp.put("numberofnote", "5");

            categoryDatatmp.put("category", MainActivity.categoriesList.get(i));

            categoryData.add(categoryDatatmp);

        }


        categoryAdapter = new CategoriesAdapter(categoryData);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(categoryAdapter);

    }
}
