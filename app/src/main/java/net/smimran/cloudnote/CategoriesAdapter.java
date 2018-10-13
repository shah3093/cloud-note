package net.smimran.cloudnote;

import android.content.Context;
import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CategoriesAdapter extends RecyclerView.Adapter <CategoriesAdapter.CategoryHolder> {

    private Map <String, Object> categoryDatatmpA;

    private ArrayList <Map <String, Object>> categoryDataA;

    Context context;

    public CategoriesAdapter(Context context, ArrayList <Map <String, Object>> categoryDataA) {
        this.categoryDataA = categoryDataA;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_category, viewGroup, false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryHolder categoryHolder, int i) {
        categoryDatatmpA = categoryDataA.get(i);
        categoryHolder.category.setText(categoryDatatmpA.get("category").toString());
        categoryHolder.noteNumber.setText(categoryDatatmpA.get("numberofnote").toString());

        ViewGroup.MarginLayoutParams marginLayoutParams =
                (ViewGroup.MarginLayoutParams) categoryHolder.itemView.getLayoutParams();

        if(((i+1) % 2) == 0){
            marginLayoutParams.setMargins(35,35,35,0);
        }else{
            marginLayoutParams.setMargins(35,35,0,0);
        }

        categoryHolder.itemView.setLayoutParams(marginLayoutParams);
        categoryHolder.itemView.requestLayout();

        categoryHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = categoryHolder.category.getText().toString();
                ((MainActivity) context).callCategoryFragment(data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryDataA.size();
    }

    class CategoryHolder extends RecyclerView.ViewHolder {

        TextView category,noteNumber;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.categoriesTitle_ID);
            noteNumber = itemView.findViewById(R.id.noteNumberID);
        }
    }
}
