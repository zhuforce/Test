package main.java.net.zetetic.tests;

import android.database.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteDatabaseHook;
import net.sqlcipher.database.SQLiteException;
import main.java.net.zetetic.ZeteticApplication;

import java.io.File;

public class VerifyUTF8EncodingForKeyTest extends SQLCipherTest {

    @Override
    public boolean execute(SQLiteDatabase database) {

        try {
            String password = "hello";
            String invalidPassword = "ŨťŬŬů";
            SQLiteDatabase sourceDatabase;
            ZeteticApplication.getInstance().extractAssetToDatabaseDirectory("hello.db");
            File sourceDatabaseFile = ZeteticApplication.getInstance().getDatabasePath("hello.db");
            database.close();
            try {
                sourceDatabase = SQLiteDatabase.openDatabase(sourceDatabaseFile.getPath(), invalidPassword, null, SQLiteDatabase.OPEN_READWRITE);
                if(queryContent(sourceDatabase)){
                    sourceDatabase.close();
                    setMessage(String.format("Database should not open with password:%s", invalidPassword));
                    return false;
                }
            } catch (SQLiteException ex){}
            SQLiteDatabaseHook hook = new SQLiteDatabaseHook() {
                public void preKey(SQLiteDatabase database) {}
                public void postKey(SQLiteDatabase database) {
                    database.rawExecSQL("PRAGMA cipher_migrate;");
                }
            };
            sourceDatabase = SQLiteDatabase.openDatabase(sourceDatabaseFile.getPath(),
                    password, null, SQLiteDatabase.OPEN_READWRITE, hook);
            return queryContent(sourceDatabase);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean queryContent(SQLiteDatabase source){
        Cursor result = source.rawQuery("select * from t1", new String[]{});
        if(result != null){
            result.moveToFirst();
            String a = result.getString(0);
            String b = result.getString(1);
            result.close();
            source.close();
            return a.equals("one for the money") &&
                    b.equals("two for the show");
        }
        return false;
    }

    @Override
    public String getName() {
        return "Verify Only UTF-8 Key Test";
    }
}
