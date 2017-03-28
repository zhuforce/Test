package main.java.net.zetetic.tests;

import android.database.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteDatabaseHook;
import main.java.net.zetetic.ZeteticApplication;

import java.io.File;

public class AES128CipherTest extends SQLCipherTest {
    @Override
    public boolean execute(SQLiteDatabase database) {

        String actual = "";
        String value = "hey";
        database.execSQL("create table t1(a)");
        database.execSQL("insert into t1(a) values (?)", new Object[]{value});
        Cursor result = database.rawQuery("select * from t1", new String[]{});
        if(result != null){
            result.moveToFirst();
            actual = result.getString(0);
            result.close();
        }
        return actual.equals(value);
    }

    @Override
    public String getName() {
        return "AES-128 Bit Cipher Test";
    }

    @Override
    protected SQLiteDatabase createDatabase(File databasePath) {

        String password = ZeteticApplication.DATABASE_PASSWORD;
        return SQLiteDatabase.openOrCreateDatabase(databasePath, password, null, new SQLiteDatabaseHook() {

            @Override
            public void preKey(SQLiteDatabase database) {}

            @Override
            public void postKey(SQLiteDatabase database) {
                database.rawExecSQL("PRAGMA cipher = 'aes-128-cbc'");
            }
        });
    }
}
