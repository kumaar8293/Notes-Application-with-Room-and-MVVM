package mukesh.note.com.database;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/*This is a room entity
 *Room Annotation (Entity) at compile time it will create all the necessary code  to create an SQLite table in this object
 *https://developer.android.com/training/data-storage/room/defining-data#java
 */
@Entity(tableName = "note_table")
public class Note implements Serializable {
    //Id will be auto-generated
    @PrimaryKey(autoGenerate = true)
    private int id;
    // @ColumnInfo(name = "title")  we can change column name like this
    private String title, description;
    private int priority;

    public Note(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    @Ignore
    public Note() {
    }

    //Because we are not passing id with constructor
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                '}';
    }
}
