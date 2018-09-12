package bibliotech4android.com.bibliotechforandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ConnectorForUser extends DbHelper{
    private Context mContext;
    private EncryptPass ep = new EncryptPass();
    public ConnectorForUser(Context context) {
        super(context);
        mContext = context;
    }

    protected boolean addUser(String login, String pass, String favaut) {
        SQLiteDatabase db = getDb(mContext);
        ContentValues contentValues = new ContentValues();
        String salt = ep.getRandomSALT();
        String password = ep.hashPassword(pass,salt);
        //there is only one user at the time, so user's id is hardcoded to be "1": rest of the query is prepared below
        contentValues.put(USER_COL_ID,1);
        contentValues.put(USER_COL_LOGIN,login);
        contentValues.put(USER_COL_SALT,salt);
        contentValues.put(USER_COL_PASS,password);
        contentValues.put(USER_COL_FAVAUT, favaut);
        long result = db.insert(USER_TABLE,null,contentValues);
        if (result==-1){
            return false;
        }
        return true;
    }

    protected boolean isSignedUp() {
        //method checks whether user account was created or not.
        //will it work?
        boolean isSignedIn = false;
        SQLiteDatabase db = getDb(mContext);
        Cursor cursor = db.rawQuery("SELECT "+USER_COL_ID+" FROM "+USER_TABLE,null);
        if(cursor.getCount()>0) isSignedIn = true;
        return isSignedIn;

    }

    protected boolean resetPass(String favaut) {
        //reset password if favourite author typed in checkbox is the same as author typed when account was created
        String favautFromTable;
        SQLiteDatabase db = getDb(mContext);
        Cursor cursor = db.rawQuery("SELECT "+USER_COL_FAVAUT+" FROM "+USER_TABLE+" WHERE "+ USER_COL_ID+" = 1",null);
        cursor.moveToFirst();
        favautFromTable = cursor.getString(0);

        if (favaut.equals(favautFromTable)) {
            db.execSQL("delete from "+USER_TABLE);
            return true;
        }
        return false;
    }

    protected boolean isPassOk(String login, String pass) {
        String rightLog;
        String rightPass;
        String encryptedPass = ep.hashPassword(pass,getSaltFromTable());
        SQLiteDatabase db = getDb(mContext);
        Cursor cursor = db.rawQuery("SELECT "+USER_COL_LOGIN+", "+USER_COL_PASS+" FROM "+USER_TABLE+" WHERE "+USER_COL_ID+"=?",
                new String[]{"1"});
        cursor.moveToFirst();
        rightLog = cursor.getString(0);
        rightPass = cursor.getString(1);

        return rightLog.equals(login) && rightPass.equals(encryptedPass);
    }

    protected String getSaltFromTable() {
        String salt;
        SQLiteDatabase db = getDb(mContext);
        Cursor cursor = db.rawQuery("SELECT "+USER_COL_SALT+" FROM "+USER_TABLE+" WHERE "+USER_COL_ID+"=?",new String[]{"1"});
        cursor.moveToFirst();
        salt = cursor.getString(0);

        return salt;
    }


}
