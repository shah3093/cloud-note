package net.smimran.cloudnote;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CategoriesAdapter extends RecyclerView.Adapter <CategoriesAdapter.CategoryHolder> {

    private Map <String, Object> categoryDatatmpA;

    private ArrayList <Map <String, Object>> categoryDataA;

    public CategoriesAdapter(ArrayList <Map <String, Object>> categoryDataA) {
        this.categoryDataA = categoryDataA;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_category, viewGroup, false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder categoryHolder, int i) {
        categoryDatatmpA = categoryDataA.get(i);
        categoryHolder.category.setText(categoryDatatmpA.get("category").toString());
        categoryHolder.noteItem.setText(categoryDatatmpA.get("numberofnote").toString());
    }

    @Override
    public int getItemCount() {
        return categoryDataA.size();
    }

    class CategoryHolder extends RecyclerView.ViewHolder {

        TextView category, noteItem;
        ImageButton viewDetails;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.categoriesTitle_ID);
            noteItem = itemView.findViewById(R.id.noteNumberID);
            viewDetails = itemView.findViewById(R.id.viewDetailsID);
        }
    }
}
