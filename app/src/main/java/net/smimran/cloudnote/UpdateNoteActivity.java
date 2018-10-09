package net.smimran.cloudnote;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class UpdateNoteActivity extends AppCompatActivity {

    String noteid, createdate;

    AutoCompleteTextView category;
    EditText description;

    LinearLayout linearLayout;

    private FirebaseAuth auth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        noteid = getIntent().getStringExtra("NOTEID");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Update Note");

        auth = FirebaseAuth.getInstance();

        category = findViewById(R.id.category);
        description = findViewById(R.id.description);

        setLayout();

        linearLayout = (LinearLayout) findViewById(R.id.add_note_layout);

    }


    public void setLayout() {
        FirebaseUser user = auth.getCurrentUser();
        db.document(user.getUid() + "/" + noteid).
                get().addOnSuccessListener(new OnSuccessListener <DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Note note = documentSnapshot.toObject(Note.class);

                category.setText(note.getCategory());
                description.setText(note.getDescription());
                createdate = note.getCreated_at();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveNoteID:
                updateNote();
                return true;
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }


    public void updateNote() {
        String descriptionStr = description.getText().toString();
        String categoryStr = category.getText().toString();
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        if (categoryStr.trim().isEmpty() || descriptionStr.trim().isEmpty()) {
            Snackbar.make(linearLayout, "Please insert a category and note", Snackbar.LENGTH_LONG).show();
            return;
        }

        Map<String,Object> note = new HashMap <>();
        note.put("category",categoryStr);
        note.put("description",descriptionStr);
        note.put("update_at",date);


        FirebaseUser user = auth.getCurrentUser();
        db.document(user.getUid() + "/" + noteid).update(note);

        Toast.makeText(this,"Note updted",Toast.LENGTH_SHORT).show();
        finish();
    }

}


