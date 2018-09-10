package bibliotech4android.com.bibliotechforandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class ConnectorForBooks extends DbHelper {

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

}
