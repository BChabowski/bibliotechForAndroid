package bibliotech4android.com.bibliotechforandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "bibliotech.db";
    public static final String AUTHORS_TABLE = "authors";
    public static final String AUTHORS_COL_ID = "id";
    public static final String AUTHORS_COL_NAME = "name";
    public static final String AUTHORS_COL_LASTNAME = "lastname";
    public static final String AUTHORS_COL_BIRTHYEAR = "birthyear";
    public static final String AUTHORS_COL_DEATHYEAR = "deathyear";
    public static final String BOOKS_TABLE = "books";
    public static final String BOOKS_COL_ID = "id";
    public static final String BOOKS_COL_TITLE ="title";
    public static final String BOOKS_COL_PUBLISHER = "publisher";
    public static final String BOOKS_COL_ISBN = "isbn";
    public static final String BOOKS_COL_ISSUEYEAR ="issueyear";
    public static final String BOOKS_COL_AUTHORID = "authorid";
    public static final String BOOKS_COL_LOCALIZATION ="localization";
    public static final String BOOKS_COL_ISLENT ="islent";
    public static final String BOOKS_COL_WHOLENT ="wholent";
    public static final String BOOKS_COL_TAGS ="tags";
    public static final String BOOKS_COL_NOTES = "notes";
    private static DbHelper dbInstance;
    private static Context mctx;



    public DbHelper(Context context) {
        super(context,DATABASE_NAME,null,1);
        mctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+AUTHORS_TABLE+" ("+AUTHORS_COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                AUTHORS_COL_NAME+" TEXT, "+AUTHORS_COL_LASTNAME+" TEXT, "+AUTHORS_COL_BIRTHYEAR+" INTEGER, "+AUTHORS_COL_DEATHYEAR+
                " INTEGER)");
        sqLiteDatabase.execSQL("create table if not exists "+BOOKS_TABLE+" ("+BOOKS_COL_ID+" integer primary key autoincrement, "+
                BOOKS_COL_TITLE+ " text, "+BOOKS_COL_PUBLISHER+" text, "+BOOKS_COL_ISBN+ " text, "+BOOKS_COL_ISSUEYEAR+
                " integer, "+BOOKS_COL_AUTHORID+" integer, "+BOOKS_COL_LOCALIZATION+" text, "+BOOKS_COL_ISLENT+" text, "+BOOKS_COL_WHOLENT+
                " text, "+BOOKS_COL_TAGS+" text, "+BOOKS_COL_NOTES+" text, foreign key("+BOOKS_COL_AUTHORID+") references "
                +AUTHORS_TABLE+"("+AUTHORS_COL_ID+"))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+AUTHORS_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+BOOKS_TABLE);

        onCreate(sqLiteDatabase);
    }

    public static synchronized SQLiteDatabase getDb(){
        if(dbInstance == null){
            dbInstance = new DbHelper(mctx);
        }
        return dbInstance.getWritableDatabase();
    }
}
