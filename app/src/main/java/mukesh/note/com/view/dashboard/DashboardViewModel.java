package mukesh.note.com.view.dashboard;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import mukesh.note.com.database.Note;
import mukesh.note.com.repositories.NoteRepositories;

public class DashboardViewModel extends ViewModel {

    private NoteRepositories noteRepositories;
    private LiveData<List<Note>> allNoteList;

    private void initializeComponents(Context context) {
        if (allNoteList == null) {
            noteRepositories = new NoteRepositories(context);
            allNoteList = noteRepositories.getAllNotes();
        }
    }

    void insertNote(Note note) {
        noteRepositories.insertData(note);
    }

    void updateNote(Note note) {
        noteRepositories.updateData(note);
    }

    void deleteNote(Note note) {
        noteRepositories.deleteData(note);
    }

    void deleteAllNote() {
        noteRepositories.deleteAllData();
    }

    LiveData<List<Note>> getAllNoteList(Context context) {
        if (allNoteList == null) {
            initializeComponents(context);
        }
        return allNoteList;
    }

}
