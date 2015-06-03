package info.androidhive.proclubDaiict;

/**
 * Created by NEHAL on 5/27/2015.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.proclubDaiict.service.Blog;

public class MySQLiteHelper extends SQLiteOpenHelper {

    /******** if debug is set true then it will show all Logcat message *******/
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ProgrammingClubDAIICT";

    // Table Names
    private static final String TABLE_BLOG = "blog";
    private static final String TABLE_ANNOUNCEMENT = "announcement";
    private static final String TABLE_CONTEST = "todo_tags";

    // Common column names
    private static final String USERNAME = "USERNAME";
    private static final String TITLE = "TITLE";
    private static final String ID = "ID";
    private static final String COMMENT = "COMMENT";
    private static final String CONTENT = "CONTENT";
    private static final String TAG = "TAG";

    private static final String KEY_CREATED_AT = "created_at";

    // NOTES Table - column nmaes
    private static final String KEY_TODO = "todo";
    private static final String KEY_STATUS = "status";

    // TAGS Table - column names
    private static final String KEY_TAG_NAME = "tag_name";

    // NOTE_TAGS Table - column names
    private static final String KEY_TODO_ID = "todo_id";
    private static final String KEY_TAG_ID = "tag_id";

    // Table Create Statements for blogs
    // Todo table create statement
    private static final String CREATE_TABLE_BLOG = "CREATE TABLE "
            + TABLE_BLOG + "(" + ID + " INTEGER PRIMARY KEY," + USERNAME
            + " TEXT," + TITLE + " TEXT," + COMMENT + " TEXT," + CONTENT
            + " TEXT, " + TAG + " TEXT)";

    // Tag table create statement for announcements
    private static final String CREATE_TABLE_ANNOUNCEMETS = "CREATE TABLE " + TABLE_ANNOUNCEMENT
            + "(" + ID + " INTEGER PRIMARY KEY," + USERNAME
            + " TEXT," + TITLE + " TEXT," + COMMENT + " TEXT," + CONTENT
            + " TEXT, " + TAG + " TEXT)";

    // todo_tag table create statement for contest
    private static final String CREATE_TABLE_CONTEST = "CREATE TABLE " + TABLE_CONTEST
            + "(" + ID + " INTEGER PRIMARY KEY," + USERNAME
            + " TEXT," + TITLE + " TEXT," + COMMENT + " TEXT," + CONTENT
            + " TEXT, " + TAG + " TEXT)";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_BLOG);
        db.execSQL(CREATE_TABLE_ANNOUNCEMETS);
        db.execSQL(CREATE_TABLE_CONTEST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BLOG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANNOUNCEMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTEST);

        // create new tables
        onCreate(db);
    }


    public void createBlog(Blog blog) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, blog.getId());
        values.put(USERNAME, blog.getUsrername());
        values.put(TITLE, blog.getTitle());
        values.put(COMMENT, blog.getComment());
        values.put(CONTENT, blog.getContent());
        values.put(TAG, blog.getTag());


        // insert row
        long todo_id = db.insert(TABLE_BLOG, null, values);
    }

    /*
 * getting all todos
 * */
    public List<Blog> getAllBlog() {
        List<Blog> todos = new ArrayList<Blog>();
        String selectQuery = "SELECT  * FROM " + TABLE_BLOG;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Blog blog = new Blog();
                blog.setId(c.getInt(c.getColumnIndex(ID)));
                blog.setComment(c.getString(c.getColumnIndex(COMMENT)));
                blog.setContent(c.getString(c.getColumnIndex(CONTENT)));
                blog.setUsrername(c.getString(c.getColumnIndex(USERNAME)));
                blog.setTitle(c.getString(c.getColumnIndex(TITLE)));
                blog.setTag(c.getString(c.getColumnIndex(TAG)));


                // adding to todo list
                todos.add(blog);
            } while (c.moveToNext());
        }

        return todos;
    }


    public void createEvent()
    {

    }
}