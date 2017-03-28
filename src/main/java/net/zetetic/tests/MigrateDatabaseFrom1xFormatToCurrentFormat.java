package main.java.net.zetetic.tests;

import android.database.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteDatabaseHook;
import main.java.net.zetetic.ZeteticApplication;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class MigrateDatabaseFrom1xFormatToCurrentFormat extends SQLCipherTest {

    @Override
    public boolean execute(SQLiteDatabase database) {

        try {
            String password = ZeteticApplication.DATABASE_PASSWORD;
            ZeteticApplication.getInstance().extractAssetToDatabaseDirectory(ZeteticApplication.ONE_X_DATABASE);
            File sourceDatabase = ZeteticApplication.getInstance().getDatabasePath(ZeteticApplication.ONE_X_DATABASE);
            SQLiteDatabaseHook hook = new SQLiteDatabaseHook() {
                public void preKey(SQLiteDatabase database) {}
                public void postKey(SQLiteDatabase database) {
                    database.rawExecSQL("PRAGMA cipher_migrate;");
                }
            };
            SQLiteDatabase source = SQLiteDatabase.openOrCreateDatabase(sourceDatabase.getPath(), password, null, hook);
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
            
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getName() {
        return "Migrate Database 1.x to Current Test";
    }
}
