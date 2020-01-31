package mukesh.note.com.repositories;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import mukesh.note.com.database.Note;
import mukesh.note.com.database.NoteDao;
import mukesh.note.com.database.NoteDatabase;

/*
 * Room doesn't allow database operation in main thread
 */

public class NoteRepositories {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepositories(Context application) {
        NoteDatabase noteDatabase = NoteDatabase.getNoteDatabase(application);
        //We can call the below method because room generate all the necessary codes
        noteDao = noteDatabase.noteDao();
        allNotes = noteDao.getAllDataFromTable();

    }

    public void insertData(Note note) {
        new InsertNoteTask(noteDao).execute(note);
    }

    public void updateData(Note note) {

        new UpdateNoteTask(noteDao).execute(note);


    }

    public void deleteData(Note note) {
        new DeleteNoteTask(noteDao).execute(note);

    }

    public void deleteAllData() {
        new DeleteAllNotesTask(noteDao).execute();

    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    private static class InsertNoteTask extends AsyncTask<Note, Void, Void> {
        NoteDao noteDao;

        InsertNoteTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {

            noteDao.insertData(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteTask extends AsyncTask<Note, Void, Void> {
        NoteDao noteDao;

        UpdateNoteTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {

            noteDao.updateData(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteTask extends AsyncTask<Note, Void, Void> {
        NoteDao noteDao;

        DeleteNoteTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {

            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesTask extends AsyncTask<Void, Void, Void> {
        NoteDao noteDao;

        DeleteAllNotesTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... notes) {

            noteDao.deleteAllData();
            return null;
        }
    }
}
