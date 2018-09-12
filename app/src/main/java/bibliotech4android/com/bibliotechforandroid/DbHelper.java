package bibliotech4android.com.bibliotechforandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "bibliotech.db";
    static final String AUTHORS_TABLE = "authors";
    static final String AUTHORS_COL_ID = "id";
    static final String AUTHORS_COL_NAME = "name";
    static final String AUTHORS_COL_LASTNAME = "lastname";
    static final String AUTHORS_COL_BIRTHYEAR = "birthyear";
    static final String AUTHORS_COL_DEATHYEAR = "deathyear";
    static final String BOOKS_TABLE = "books";
    static final String BOOKS_COL_ID = "id";
    static final String BOOKS_COL_TITLE ="title";
    static final String BOOKS_COL_PUBLISHER = "publisher";
    static final String BOOKS_COL_ISBN = "isbn";
    static final String BOOKS_COL_ISSUEYEAR ="issueyear";
    static final String BOOKS_COL_AUTHORID = "authorid";
    static final String BOOKS_COL_LOCALIZATION ="localization";
    static final String BOOKS_COL_ISLENT ="islent";
    static final String BOOKS_COL_WHOLENT ="wholent";
    static final String BOOKS_COL_TAGS ="tags";
    static final String BOOKS_COL_NOTES = "notes";
    static final String USER_TABLE = "user";
    static final String USER_COL_ID = "userid";
    static final String USER_COL_LOGIN = "login";
    static final String USER_COL_SALT = "salt";
    static final String USER_COL_PASS = "pass";
    static final String USER_COL_FAVAUT = "favaut";

    private static DbHelper dbInstance;
    private Context mctx;



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
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+USER_TABLE+" ("+USER_COL_ID+" INTEGER, "+USER_COL_LOGIN+" TEXT, "
                +USER_COL_SALT+" TEXT, "+USER_COL_PASS+" TEXT, "+USER_COL_FAVAUT+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+AUTHORS_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+BOOKS_TABLE);

        onCreate(sqLiteDatabase);
    }

    public static synchronized SQLiteDatabase getDb(Context context){
        if(dbInstance == null){
            dbInstance = new DbHelper(context);
        }
        return dbInstance.getWritableDatabase();
    }
}
