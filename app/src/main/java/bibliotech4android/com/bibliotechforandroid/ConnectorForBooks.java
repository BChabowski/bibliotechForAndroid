package bibliotech4android.com.bibliotechforandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Vector;

public class ConnectorForBooks extends DbHelper {
    private String[] bookMisc = new String[]
            {BOOKS_COL_TITLE,"author",BOOKS_COL_TAGS,BOOKS_COL_ISBN,BOOKS_COL_PUBLISHER,BOOKS_COL_LOCALIZATION,BOOKS_COL_ISSUEYEAR,BOOKS_COL_ID};
    public ConnectorForBooks(Context context) {
        super(context);
    }

    public boolean addBook(Book book) {
        SQLiteDatabase db = getDb();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BOOKS_COL_TITLE, book.getTitle());
        contentValues.put(BOOKS_COL_PUBLISHER, book.getPublisher());
        contentValues.put(BOOKS_COL_ISBN, book.getIsbnNo());
        contentValues.put(BOOKS_COL_ISSUEYEAR, book.getIssueYear());
        contentValues.put(BOOKS_COL_AUTHORID, book.getAuthorid());
        contentValues.put(BOOKS_COL_LOCALIZATION, book.getLocalization());
        contentValues.put(BOOKS_COL_ISLENT, book.getIsLent());
        contentValues.put(BOOKS_COL_WHOLENT, book.getWhoLent());
        contentValues.put(BOOKS_COL_TAGS, book.getTags());
        contentValues.put(BOOKS_COL_NOTES, book.getNotes());
        long result = db.insert(BOOKS_TABLE,null,contentValues);
        return result!=-1;
    }
    public boolean updateBook(Book book) {
        SQLiteDatabase db = getDb();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BOOKS_COL_TITLE, book.getTitle());
        contentValues.put(BOOKS_COL_PUBLISHER, book.getPublisher());
        contentValues.put(BOOKS_COL_ISBN, book.getIsbnNo());
        contentValues.put(BOOKS_COL_ISSUEYEAR, book.getIssueYear());
        contentValues.put(BOOKS_COL_AUTHORID, book.getAuthorid());
        contentValues.put(BOOKS_COL_LOCALIZATION, book.getLocalization());
        contentValues.put(BOOKS_COL_ISLENT, book.getIsLent());
        contentValues.put(BOOKS_COL_WHOLENT, book.getWhoLent());
        contentValues.put(BOOKS_COL_TAGS, book.getTags());
        contentValues.put(BOOKS_COL_NOTES, book.getNotes());
        long result = db.update(BOOKS_TABLE,contentValues,BOOKS_COL_ID+"=?",new String[]{book.getId().toString()});
        return result!=-1;
    }
    public boolean deleteBook(int id){
        SQLiteDatabase db = getDb();
        long rowsAffected = db.delete(BOOKS_TABLE,BOOKS_COL_ID+"=?",new String[]{String.valueOf(id)});
        return (rowsAffected>0);
    }

    public Vector<Book> selectBooks(String what, Integer where){
        Vector<Book> bookVector = new Vector<>();
        SQLiteDatabase db = getDb();
        String[] searched = {what};
        String column = bookMisc[where];
        String query = "SELECT "+BOOKS_COL_ID+", "+BOOKS_COL_TITLE+", "+BOOKS_COL_PUBLISHER+", "+BOOKS_COL_ISSUEYEAR+", "+BOOKS_COL_ISBN+", "
                +BOOKS_COL_AUTHORID+", "+BOOKS_COL_TAGS+", "+BOOKS_COL_NOTES+", "+BOOKS_COL_ISLENT+", "
                +BOOKS_COL_WHOLENT+", "+BOOKS_COL_LOCALIZATION+" FROM "+BOOKS_TABLE+" WHERE "+column+" LIKE ? ORDER BY "+column;
        Cursor cursor = db.rawQuery(query,searched);
        while(cursor.moveToNext()){
            Book b = new Book(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),
                    cursor.getString(4),cursor.getInt(5),cursor.getString(6),cursor.getString(7),
                    cursor.getString(8),cursor.getString(9),cursor.getString(10));
            bookVector.add(b);
        }
        return bookVector;
    }
    public Vector<Book> allBooksByAuthor(String what){

        return new Vector<>();
    }

    public ArrayList<String> toArrayOfStrings(Vector<Book> bv){
        ArrayList<String> booksAsStringsArray = new ArrayList<>();
        for(Book b : bv){
            String s = b.toString(mctx);
            booksAsStringsArray.add(s);
        }
        return booksAsStringsArray;
    }
}
