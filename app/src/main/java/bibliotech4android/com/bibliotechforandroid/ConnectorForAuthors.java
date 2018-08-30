package bibliotech4android.com.bibliotechforandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Vector;

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

    public boolean updateAuthor(Author aut){
        SQLiteDatabase db = getDb();
        ContentValues contentValues = new ContentValues();
        contentValues.put(AUTHORS_COL_NAME, aut.getName());
        contentValues.put(AUTHORS_COL_LASTNAME,aut.getLastName());
        contentValues.put(AUTHORS_COL_BIRTHYEAR,aut.getYearOfBirth());
        contentValues.put(AUTHORS_COL_DEATHYEAR, aut.getYearOfDeath());
        long result = db.update(AUTHORS_TABLE,contentValues,AUTHORS_COL_ID+"=?",new String[]{aut.getId().toString()});
        return (result!=-1);

    }

    public boolean deleteAuthor(int id){
        SQLiteDatabase db = getDb();
        long rowsAffected = db.delete(AUTHORS_TABLE,AUTHORS_COL_ID+"=?",new String[]{String.valueOf(id)});
        return (rowsAffected>0);
    }

    public Vector<Author> showAllAuthors(){
        Vector<Author> authorVector = new Vector<>();
        SQLiteDatabase db = getDb();
        Cursor cursor = db.rawQuery("SELECT "+AUTHORS_COL_ID+", "+AUTHORS_COL_NAME+", "+AUTHORS_COL_LASTNAME+", "+AUTHORS_COL_BIRTHYEAR+", "
                +AUTHORS_COL_DEATHYEAR+" FROM "+AUTHORS_TABLE+" ORDER BY "+AUTHORS_COL_LASTNAME,null);
        while(cursor.moveToNext()){
            Author a = new Author(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getInt(4));
            authorVector.add(a);
        }
        return authorVector;
    }

    public ArrayList<String> toArrayOfStrings(Vector<Author> av){
        //niech przyjmuje jako argument Vector<Author> i zamienia go na wersję arraylist<string>
        ArrayList<String> searchResults = new ArrayList<>();
        for (Author a: av) {
            String s = a.toString();
            searchResults.add(s);
        }
        return searchResults;
    }



    public Vector<Author> searchAuthorById(int id) {
        Vector<Author> authorVector = new Vector<>();
        SQLiteDatabase db = getDb();
        Cursor cursor = db.rawQuery("SELECT "+AUTHORS_COL_ID+", "+AUTHORS_COL_NAME+", "+AUTHORS_COL_LASTNAME+", "+AUTHORS_COL_BIRTHYEAR+", "
                +AUTHORS_COL_DEATHYEAR+" FROM "+AUTHORS_TABLE+" WHERE "+AUTHORS_COL_ID+ " = "+id,null);
        while(cursor.moveToNext()){
            Author a = new Author(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getInt(4));
            authorVector.add(a);
        }
        return authorVector;
    }
}
