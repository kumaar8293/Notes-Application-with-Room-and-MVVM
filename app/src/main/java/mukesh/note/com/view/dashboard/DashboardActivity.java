package mukesh.note.com.view.dashboard;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import mukesh.note.com.R;
import mukesh.note.com.database.Note;
import mukesh.note.com.view.addEdit.AddOrEditNoteActivity;
import es.dmoral.toasty.Toasty;

public class DashboardActivity extends AppCompatActivity implements
        DashboardAdapterWithAnimation.CustomOnItemClickListener {
    public final int ADD_REQUEST_CODE = 1;
    public final int EDIT_REQUEST_CODE = 2;
    //    private DashboardAdapter adapter;
    private DashboardAdapterWithAnimation adapter;
    private DashboardViewModel viewModel;
    private LottieAnimationView no_notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        viewModel.getAllNoteList(this).observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                //Custom Datapass
                // adapter.setData(notes);
                //Adapter with animation
                adapter.submitList(notes);
                if (notes != null && notes.size() == 0) {
                    no_notes.setVisibility(View.VISIBLE);
                } else {
                    no_notes.setVisibility(View.GONE);

                }
            }
        });
    }

    private void initUI() {
        BottomAppBar appBar = findViewById(R.id.bottomAppBar);
        appBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Handle actions based on the menu item
                if (item.getItemId() == R.id.menuSettings) {
                    Toasty.info(DashboardActivity.this, "Do your work", Toast.LENGTH_SHORT, true).show();
                } else if (item.getItemId() == R.id.menuDelete) {
                    showDialog();
                }
                return true;
            }
        });
        appBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the navigation click by showing a BottomDrawer etc.
                Toasty.info(DashboardActivity.this, "Do your work", Toast.LENGTH_SHORT, true).show();

            }
        });
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        adapter = new DashboardAdapterWithAnimation(this, this);
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.deleteNote(adapter.getNoteAtPosition(viewHolder.getAdapterPosition()));

                Toasty.success(DashboardActivity.this, "Item deleted", Toast.LENGTH_SHORT, true).show();

            }
        }).attachToRecyclerView(recyclerView);

        findViewById(R.id.addNote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this,
                        AddOrEditNoteActivity.class);
                startActivityForResult(intent, ADD_REQUEST_CODE);
            }
        });

        no_notes = findViewById(R.id.no_notes);
    }

    @Override
    public void onItemClick(Note note, View itemView) {
        Intent intent = new Intent(this, AddOrEditNoteActivity.class);
        intent.putExtra("bundle", note);
// Now we provide a list of Pair items which contain the view we can transitioning
// from, and the name of the view it is transitioning to, in the launched activity
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,
                Pair.create(itemView.findViewById(R.id.itemTitle), this.getString(R.string.title_transition)),
                Pair.create(itemView.findViewById(R.id.itemDescription), this.getString(R.string.desc_transition)));
        startActivityForResult(intent, EDIT_REQUEST_CODE, options.toBundle());
    }

    private void showDialog() {

        new MaterialAlertDialogBuilder(this)
                .setTitle("Delete All Notes")
                .setMessage("Are you sure? You want to delete all the notes.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.deleteAllNote();
                        Toasty.success(DashboardActivity.this, "All notes deleted", Toast.LENGTH_SHORT, true).show();

                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Note note = (Note) data.getSerializableExtra("bundle");
            viewModel.insertNote(note);
            Toasty.success(this, "Note saved", Toast.LENGTH_SHORT, true).show();

        } else if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Note note = (Note) data.getSerializableExtra("bundle");
            viewModel.updateNote(note);
            Toasty.success(this, "Note updated", Toast.LENGTH_SHORT, true).show();

        } else {
            Toasty.error(this, "Note not saved", Toast.LENGTH_SHORT, true).show();
        }
    }
}
