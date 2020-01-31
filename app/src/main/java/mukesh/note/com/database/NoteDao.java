package mukesh.note.com.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/*
 *This is a Room DAO (Data Access Object)
 *https://developer.android.com/training/data-storage/room/
 */
@Dao
public interface NoteDao {

    @Insert
    void insertData(Note note);

    @Update
    void updateData(Note note);

    @Delete
    void delete(Note note);

    @Query("SELECT * FROM note_table ORDER BY priority ASC")
    LiveData<List<Note>> getAllDataFromTable();

    @Query("DELETE FROM note_table")
    void deleteAllData();

}
