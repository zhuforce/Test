package main.java.net.zetetic.tests;

import android.database.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteDatabaseHook;
import main.java.net.zetetic.QueryHelper;
import main.java.net.zetetic.ZeteticApplication;

import java.io.File;
import java.io.IOException;

public class AttachExistingDatabaseTest extends SQLCipherTest {

    private int originalKdfLength;

    @Override
    protected SQLiteDatabase createDatabase(File databasePath) {
        SQLiteDatabaseHook hook = new SQLiteDatabaseHook() {
            public void preKey(SQLiteDatabase database) {}
            public void postKey(SQLiteDatabase database) {
                database.execSQL("PRAGMA cipher_default_kdf_iter = 4000;");
            }
        };
        return SQLiteDatabase.openOrCreateDatabase(databasePath,
                ZeteticApplication.DATABASE_PASSWORD, null, hook);
    }

    @Override
    public boolean execute(SQLiteDatabase database) {

        try {
            ZeteticApplication.getInstance().extractAssetToDatabaseDirectory(ZeteticApplication.ONE_X_DATABASE);
            File other = ZeteticApplication.getInstance().getDatabasePath(ZeteticApplication.ONE_X_DATABASE);
            String otherPath = other.getAbsolutePath();
            String attach = String.format("attach database ? as other key ?");
            database.rawExecSQL("pragma cipher_default_use_hmac = off");
            originalKdfLength = QueryHelper.singleIntegerValueFromQuery(database, "PRAGMA kdf_iter;");
            database.rawExecSQL("pragma cipher_default_kdf_iter = 4000;");
            database.execSQL(attach, new Object[]{otherPath, ZeteticApplication.DATABASE_PASSWORD});
            Cursor result = database.rawQuery("select * from other.t1", new String[]{});
            String a = "";
            String b = "";
            if(result != null){
                result.moveToFirst();
                a = result.getString(0);
                b = result.getString(1);
                result.close();
            }
            database.execSQL("detach database other");
            return a.length() > 0 && b.length() > 0;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    protected void tearDown(SQLiteDatabase database) {
        ZeteticApplication.getInstance().delete1xDatabase();
        database.rawExecSQL(String.format("PRAGMA cipher_default_kdf_iter = %s", originalKdfLength));
        database.rawExecSQL("pragma cipher_default_use_hmac = on");
    }

    @Override
    public String getName() {
        return "Attach Existing Database Test";
    }
}
