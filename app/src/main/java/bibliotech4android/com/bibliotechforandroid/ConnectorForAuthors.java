package bibliotech4android.com.bibliotechforandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ConnectorForAuthors extends DbHelper {

    public ConnectorForAuthors(Context context) {
        super(context);
    }

    public boolean addAuthor(Author aut){
        SQLiteDatabase db = getDb();
        ContentValues contentValues = new ContentValues();
        contentValues.put(AUTHORS_COL_NAME, aut.getName());
        contentValues.put(AUTHORS_COL_LASTNAME,aut.getLastName());
        contentValues.put(AUTHORS_COL_BIRTHYEAR,aut.getYearOfBirth());
        contentValues.put(AUTHORS_COL_DEATHYEAR, aut.getYearOfDeath());
        long result = db.insert(AUTHORS_TABLE,null,contentValues);
        //if result == -1, then insert was unsuccessful
        return result!=-1;
    }

    public Cursor showAllAuthors(){
        SQLiteDatabase db = getDb();
        Cursor cursor = db.rawQuery("SELECT "+AUTHORS_COL_ID+", "+AUTHORS_COL_NAME+", "+AUTHORS_COL_LASTNAME+", "+AUTHORS_COL_BIRTHYEAR+", "+AUTHORS_COL_DEATHYEAR+
                " FROM "+AUTHORS_TABLE,null);
        return cursor;
    }

    public ArrayList<String> searchResults(){
        ArrayList<String> searchResults = new ArrayList<>();
        Cursor c = showAllAuthors();
        while(c.moveToNext()) {
            String s = c.getString(2) + ", " + c.getString(1) + " (" + c.getInt(3) + " - " + c.getInt(4) + ")";
            searchResults.add(s);
        }
        return searchResults;
    }
}
