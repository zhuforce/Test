package main.java.net.zetetic.tests;

import android.app.Activity;
import android.net.Uri;
import net.sqlcipher.database.SQLiteDatabase;
import main.java.net.zetetic.ZeteticApplication;
import main.java.net.zetetic.ZeteticContentProvider;

public class CrossProcessCursorQueryTest extends SQLCipherTest {
    
    @Override
    public boolean execute(SQLiteDatabase database) {

        Activity activity = ZeteticApplication.getInstance().getCurrentActivity();
        Uri providerUri = ZeteticContentProvider.CONTENT_URI;
        android.database.Cursor cursor = activity.managedQuery(providerUri, null, null, null, null);
        StringBuilder buffer = new StringBuilder();
        while (cursor.moveToNext()) {
            buffer.append(cursor.getString(0));
            buffer.append(cursor.getString(1));
        }
        cursor.close();
        return buffer.toString().length() > 0;
    }



    @Override
    public String getName() {
        return "Custom Cross Process Cursor Test";
    }
}
