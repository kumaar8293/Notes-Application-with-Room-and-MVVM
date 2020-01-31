package mukesh.note.com.view.dashboard;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import mukesh.note.com.R;
import mukesh.note.com.database.Note;

public class DashboardAdapterWithAnimation extends ListAdapter<Note, DashboardAdapterWithAnimation.NoteHolder> {

    private CustomOnItemClickListener listener;

    DashboardAdapterWithAnimation(Context context, CustomOnItemClickListener listener) {
        //To see which item is adding or removing or updating
        super(DIFF_CALLBACK);
        this.listener = listener;
        initBackground(context);
    }

    //this method id static so that it will call before constructor calls
    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            //it just have to be same entry in our database
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            //oldItem.equals(newItem);
            // this is a wrong approach , because our live data always returns the whole list not any perticular object
            //so we need to check with all the elements which are changes
            return oldItem.getDescription().equals(newItem.getDescription())
                    && oldItem.getTitle().equals(newItem.getTitle())
                    && oldItem.getPriority() == newItem.getPriority();
        }
    };


    Note getNoteAtPosition(int position) {
        return getItem(position);
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

        Note currentNote = getItem(position);
        holder.noteTitle.setText(currentNote.getTitle());
        holder.noteDescription.setText(currentNote.getDescription());
        holder.parentLayout.setBackground(getBackground());
        holder.priorityNumber.setText(("" + currentNote.getPriority()));

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
                        listener.onItemClick(getItem(getAdapterPosition()), itemView);
                    }
                }
            });
        }
    }

    public interface CustomOnItemClickListener {
        void onItemClick(Note note, View itemView);
    }

    private void initBackground(Context context) {

        background = new Drawable[]{context.getDrawable(R.drawable.dashboard_item1_background),
                context.getDrawable(R.drawable.dashboard_item2_background),
                context.getDrawable(R.drawable.dashboard_item3_background),
                context.getDrawable(R.drawable.dashboard_item4_background)};
    }

    private Drawable[] background;
    private int lastPosition = -1;

    private Drawable getBackground() {
        lastPosition++;
        if (lastPosition < background.length) {
            return background[lastPosition];
        }
        lastPosition = 0;
        return background[0];
    }
}
