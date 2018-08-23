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
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+AUTHORS_TABLE);
        onCreate(sqLiteDatabase);
    }

    public static synchronized SQLiteDatabase getDb(){
        if(dbInstance == null){
            dbInstance = new DbHelper(mctx);
        }
        return dbInstance.getWritableDatabase();
    }
}
