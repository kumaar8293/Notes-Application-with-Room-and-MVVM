package mukesh.note.com.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
/*
 *Room doesn't allow database operation in main thread
 */

@Database(entities = Note.class, version = 1 ,exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {
    private static NoteDatabase instance;

    //ROOM auto generates all the necessary code we need here
    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getNoteDatabase(Context context) {
        if (instance == null)
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_database")
                    .addCallback(databaseCallBack)
                    .fallbackToDestructiveMigration().build();
        return instance;
    }

    private static RoomDatabase.Callback databaseCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //inserting some predefinedData
            new PopulateDBAsyncTask(instance).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {
        NoteDao noteDao;

         PopulateDBAsyncTask(NoteDatabase db) {
            noteDao = db.noteDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {

            noteDao.insertData(new Note("Title1", "Description1", 1));
            noteDao.insertData(new Note("Title2", "Description2", 2));
            noteDao.insertData(new Note("Title3", "Description3", 3));
            noteDao.insertData(new Note("Title4", "Description4", 4));
            noteDao.insertData(new Note("Title5", "Description4", 5));
            noteDao.insertData(new Note("Title6", "Description4", 6));
            noteDao.insertData(new Note("Title7", "Description4", 7));
            noteDao.insertData(new Note("Title8", "Description4", 8));
            noteDao.insertData(new Note("Title9", "Description4", 9));
            noteDao.insertData(new Note("Title10", "Description4", 10));
            return null;
        }
    }
}
