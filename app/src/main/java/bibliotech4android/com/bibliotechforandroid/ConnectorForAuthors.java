package bibliotech4android.com.bibliotechforandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Vector;

public class ConnectorForAuthors extends DbHelper {
    private Context context;
    public ConnectorForAuthors(Context context) {
        super(context);
        this.context = context;
    }

    public boolean addAuthor(Author aut){
        SQLiteDatabase db = getDb(context);
        ContentValues contentValues = new ContentValues();
        contentValues.put(AUTHORS_COL_NAME, aut.getName());
        contentValues.put(AUTHORS_COL_LASTNAME,aut.getLastName());

        if(aut.getYearOfBirth()==null) contentValues.putNull(AUTHORS_COL_BIRTHYEAR);
        else contentValues.put(AUTHORS_COL_BIRTHYEAR,aut.getYearOfBirth());

        if(aut.getYearOfDeath()==null) contentValues.putNull(AUTHORS_COL_DEATHYEAR);
        else contentValues.put(AUTHORS_COL_DEATHYEAR, aut.getYearOfDeath());

        long result = db.insert(AUTHORS_TABLE,null,contentValues);
        //if result == -1, then insert was unsuccessful
        return result!=-1;
    }

    public boolean updateAuthor(Author aut){
        SQLiteDatabase db = getDb(context);
        ContentValues contentValues = new ContentValues();
        contentValues.put(AUTHORS_COL_NAME, aut.getName());
        contentValues.put(AUTHORS_COL_LASTNAME,aut.getLastName());

        if(aut.getYearOfBirth()==null) contentValues.putNull(AUTHORS_COL_BIRTHYEAR);
        else contentValues.put(AUTHORS_COL_BIRTHYEAR,aut.getYearOfBirth());

        if(aut.getYearOfDeath()==null) contentValues.putNull(AUTHORS_COL_DEATHYEAR);
        else contentValues.put(AUTHORS_COL_DEATHYEAR, aut.getYearOfDeath());

        long result = db.update(AUTHORS_TABLE,contentValues,AUTHORS_COL_ID+"=?",new String[]{aut.getId().toString()});
        return (result!=-1);

    }

    public boolean deleteAuthor(int id){
        SQLiteDatabase db = getDb(context);
        long rowsAffected = db.delete(AUTHORS_TABLE,AUTHORS_COL_ID+"=?",new String[]{String.valueOf(id)});
        return (rowsAffected>0);
    }

    public Vector<Author> selectAuthors(String query){
        Vector<Author> authorVector = new Vector<>();
        String[] searched;

            searched = query.split("\\s+");

        String searchQuery = "SELECT "+AUTHORS_COL_ID+", "+AUTHORS_COL_NAME+", "+AUTHORS_COL_LASTNAME+", "+AUTHORS_COL_BIRTHYEAR+", "
                +AUTHORS_COL_DEATHYEAR+" FROM "+AUTHORS_TABLE;

        for(int i = 0; i<searched.length;i++){
            if (i==0) searchQuery += " WHERE "+AUTHORS_COL_LASTNAME + " LIKE ? ";
            else searchQuery += " OR " + AUTHORS_COL_LASTNAME + " LIKE ? ";
        }
        searchQuery += " ORDER BY "+AUTHORS_COL_LASTNAME;
        Cursor cursor = getDb(context).rawQuery(searchQuery,searched);
        while(cursor.moveToNext()){
            Integer bday = null;
            Integer dday = null;
            if(!cursor.isNull(3)) bday = cursor.getInt(3);
            if(!cursor.isNull(4)) dday = cursor.getInt(4);

            Author a = new Author(cursor.getInt(0),cursor.getString(1),cursor.getString(2),bday,dday);
            authorVector.add(a);
        }
        return authorVector;
    }

    public Vector<Author> showAllAuthors(){
        Vector<Author> authorVector = new Vector<>();
        SQLiteDatabase db = getDb(context);
        Cursor cursor = db.rawQuery("SELECT "+AUTHORS_COL_ID+", "+AUTHORS_COL_NAME+", "+AUTHORS_COL_LASTNAME+", "+AUTHORS_COL_BIRTHYEAR+", "
                +AUTHORS_COL_DEATHYEAR+" FROM "+AUTHORS_TABLE+" ORDER BY "+AUTHORS_COL_LASTNAME,null);
        while(cursor.moveToNext()){
            Integer bday = null;
            Integer dday = null;
            if(!cursor.isNull(3)) bday = cursor.getInt(3);
            if(!cursor.isNull(4)) dday = cursor.getInt(4);
            Author a = new Author(cursor.getInt(0),cursor.getString(1),cursor.getString(2),bday,dday);
            authorVector.add(a);
        }
        return authorVector;
    }

    public ArrayList<String> toArrayOfStrings(Vector<Author> av){
        //takes Vector<Author> as an argument and converts it into ArrayList<String>
        ArrayList<String> authorsAsStringsArray = new ArrayList<>();
        for (Author a: av) {
            String s = a.toString();
            authorsAsStringsArray.add(s);
        }
        return authorsAsStringsArray;
    }



    public Author searchAuthorById(int id) {
        Author author = new Author();
        SQLiteDatabase db = getDb(context);
        Cursor cursor = db.rawQuery("SELECT "+AUTHORS_COL_ID+", "+AUTHORS_COL_NAME+", "+AUTHORS_COL_LASTNAME+", "+AUTHORS_COL_BIRTHYEAR+", "
                +AUTHORS_COL_DEATHYEAR+" FROM "+AUTHORS_TABLE+" WHERE "+AUTHORS_COL_ID+ " = "+id,null);
        while(cursor.moveToNext()){
            Integer bday = null;
            Integer dday = null;
            if(!cursor.isNull(3)) bday = cursor.getInt(3);
            if(!cursor.isNull(4)) dday = cursor.getInt(4);
            author = new Author(cursor.getInt(0),cursor.getString(1),cursor.getString(2),bday,dday);

        }
        return author;
    }
}
