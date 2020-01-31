package mukesh.note.com.view.dashboard;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mukesh.note.com.R;
import mukesh.note.com.database.Note;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.NoteHolder> {

    private List<Note> notes = new ArrayList<>();
    private CustomOnItemClickListener listener;

    // create instance of Random class
    private Random rand = new Random();
    private Drawable[] background;

    DashboardAdapter(Context context, CustomOnItemClickListener listener) {
        this.listener = listener;
        initBackground(context);
    }

    private void initBackground(Context context) {

        background = new Drawable[]{context.getDrawable(R.drawable.dashboard_item1_background),
                context.getDrawable(R.drawable.dashboard_item2_background),
                context.getDrawable(R.drawable.dashboard_item3_background),
                context.getDrawable(R.drawable.dashboard_item4_background)};
    }

    Note getNoteAtPosition(int position) {
        return notes.get(position);
    }

    void setData(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dash_board_single_row, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {

        Note currentNote = notes.get(position);
        holder.noteTitle.setText(currentNote.getTitle());
        holder.noteDescription.setText(currentNote.getDescription());
        holder.parentLayout.setBackground(getRandomNumber());
        holder.priorityNumber.setText(("" + currentNote.getPriority()));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NoteHolder extends RecyclerView.ViewHolder {

        TextView noteTitle, noteDescription, priorityNumber;
        ConstraintLayout parentLayout;

        NoteHolder(@NonNull final View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.itemTitle);
            noteDescription = itemView.findViewById(R.id.itemDescription);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            priorityNumber = itemView.findViewById(R.id.priorityNumber);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        listener.onItemClick(notes.get(getAdapterPosition()), itemView);
                    }
                }
            });
        }
    }

    public interface CustomOnItemClickListener {
        void onItemClick(Note note, View itemView);
    }

    private Drawable getRandomNumber() {
        // Generate random integers in range 0 to length of array-1
        int randomNumber = rand.nextInt(background.length);
        return background[randomNumber];
    }

}
