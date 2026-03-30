package com.example.ar_application.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ar_application.models.Task;
import com.example.ar_application.models.Topic;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "app_database.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_TOPICS = "topics";
    private static final String TABLE_TASKS = "tasks";

    private static final String COLUMN_ID = "id";

    // Topics Columns
    private static final String COLUMN_COMPONENT = "component";
    private static final String COLUMN_CONTENT = "content";
    private static final String COLUMN_IMAGE = "image";

    // Tasks Columns
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_IMAGE_URL = "image_url"; // ✅ Added image for tasks

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // ✅ Create Topics Table
        String CREATE_TOPICS_TABLE = "CREATE TABLE " + TABLE_TOPICS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_COMPONENT + " TEXT UNIQUE, " // ✅ Unique component name
                + COLUMN_CONTENT + " TEXT, "
                + COLUMN_IMAGE + " TEXT)";
        db.execSQL(CREATE_TOPICS_TABLE);

        // ✅ Create Tasks Table
        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT UNIQUE, " // ✅ Unique name to prevent duplicates
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_IMAGE_URL + " TEXT)";
        db.execSQL(CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOPICS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    // ✅ Insert a new Topic
    public boolean insertTopic(Topic topic) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COMPONENT, topic.getComponent());
        values.put(COLUMN_CONTENT, topic.getContent());
        values.put(COLUMN_IMAGE, topic.getImageUrl()); // ✅ Store image URL

        long result = db.insertWithOnConflict(TABLE_TOPICS, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
        return result != -1;
    }



    // ✅ Retrieve all Topics
    public List<Topic> getAllTopics() {
        List<Topic> topicsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TOPICS, null);

        if (cursor.moveToFirst()) {
            do {
                topicsList.add(new Topic(
                        cursor.getString(1), // Component
                        cursor.getString(2), // Content
                        cursor.getString(3)  // Image URL
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return topicsList;
    }



    // ✅ Insert a new Task (Prevents duplicates)
    public boolean insertTask(Task task) {
        if (isTaskAlreadyAdded(task.getTitle())) {
            return false; // ✅ Task already exists
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, task.getTitle());
        values.put(COLUMN_DESCRIPTION, task.getDescription());
        values.put(COLUMN_IMAGE_URL, task.getImageUrl()); // ✅ Store image

        long result = db.insert(TABLE_TASKS, null, values);
        db.close();
        return result != -1;
    }

    // ✅ Retrieve all Tasks
    public List<Task> getAllTasks() {
        List<Task> tasksList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TASKS, null);

        if (cursor.moveToFirst()) {
            do {
                tasksList.add(new Task(
                        cursor.getString(1), // Name
                        cursor.getString(2), // Description
                        cursor.getString(3)  // Image URL
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tasksList;
    }

    // ✅ Check if Task Already Exists
    public boolean isTaskAlreadyAdded(String taskName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT 1 FROM " + TABLE_TASKS + " WHERE " + COLUMN_NAME + " = ?", new String[]{taskName});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // ✅ Delete Task
    public boolean deleteTask(String taskName) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_TASKS, COLUMN_NAME + " = ?", new String[]{taskName});
        db.close();
        return result > 0;
    }
}
