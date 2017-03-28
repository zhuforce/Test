package main.java.net.zetetic.tests;

import android.database.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteDatabaseHook;
import main.java.net.zetetic.ZeteticApplication;

import java.io.File;

public class ExportToUnencryptedDatabase extends SQLCipherTest {

    File unencryptedFile;

    @Override
    public boolean execute(SQLiteDatabase database) {

        database.close();
        ZeteticApplication.getInstance().deleteDatabase(ZeteticApplication.DATABASE_NAME);
        File databaseFile = ZeteticApplication.getInstance().getDatabasePath(ZeteticApplication.DATABASE_NAME);
        database = SQLiteDatabase.openOrCreateDatabase(databaseFile, ZeteticApplication.DATABASE_PASSWORD, null);
        database.rawExecSQL("create table t1(a,b);");
        database.execSQL("insert into t1(a,b) values(?, ?);", new Object[]{"one for the money", "two for the show"});
        unencryptedFile = ZeteticApplication.getInstance().getDatabasePath("plaintext.db");
        ZeteticApplication.getInstance().deleteDatabase("plaintext.db");
        database.rawExecSQL(String.format("ATTACH DATABASE '%s' as plaintext KEY '';",
                unencryptedFile.getAbsolutePath()));
        database.rawExecSQL("SELECT sqlcipher_export('plaintext');");
        database.rawExecSQL("DETACH DATABASE plaintext;");

        SQLiteDatabase unencryptedDatabase = SQLiteDatabase.openOrCreateDatabase(unencryptedFile, "", null, null);
        Cursor cursor = unencryptedDatabase.rawQuery("select * from t1;", new String[]{});
        String a = "";
        String b = "";
        while(cursor.moveToNext()){
            a = cursor.getString(0);
            b = cursor.getString(1);
        }
        cursor.close();
        return a.equals("one for the money") &&
               b.equals("two for the show");
    }

    @Override
    protected void tearDown(SQLiteDatabase database) {
        unencryptedFile.delete();
    }

    @Override
    public String getName() {
        return "Export to Unencrypted Database";
    }
}
