package mukesh.note.com.view.addEdit;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import mukesh.note.com.R;
import mukesh.note.com.database.Note;
import es.dmoral.toasty.Toasty;

public class AddOrEditNoteActivity extends AppCompatActivity {
    private TextInputEditText editTitle, editDescription;
    private NumberPicker priorityNumber;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        //in manifest need to declare android:parentActivityName=".view.MainActivity" to get X button visible
        if (getSupportActionBar() != null)
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        initView();
    }

    private void initView() {

        editDescription = findViewById(R.id.editDescription);
        editTitle = findViewById(R.id.editTitle);
        priorityNumber = findViewById(R.id.priorityNumber);
        priorityNumber.setMinValue(1);
        priorityNumber.setMaxValue(20);

        Intent intent = getIntent();
        note = (Note) intent.getSerializableExtra("bundle");
        if (note != null) {
            setTitle("Edit Note");
            editTitle.setText(note.getTitle());
            editDescription.setText(note.getDescription());
            priorityNumber.setValue(note.getPriority());
        } else {
            setTitle("Add Note");
            note = new Note();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.note_menu, menu);
        //Return false if you don't want to show the menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Respond to the action bar's Up/Home button
        if (item.getItemId() == android.R.id.home) {
            supportFinishAfterTransition();
            return true;
        } else if (item.getItemId() == R.id.saveNote) {
            saveNote();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveNote() {
        if (editTitle.getText() == null || editDescription.getText() == null)
            return;
        String title = editTitle.getText().toString().trim();
        String description = editDescription.getText().toString().trim();
        int priorityNo = priorityNumber.getValue();

        if (title.trim().isEmpty() || description.isEmpty()) {
            Toasty.error(this, "Please enter title and description", Toast.LENGTH_SHORT, true).show();

            return;
        }
        note.setTitle(title);
        note.setPriority(priorityNo);
        note.setDescription(description);

        Intent intent = new Intent();
        intent.putExtra("bundle", note);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
