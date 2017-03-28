package main.java.net.zetetic.tests;

import net.sqlcipher.database.SQLiteDatabase;
import main.java.net.zetetic.QueryHelper;

import main.java.net.zetetic.ZeteticApplication;

import java.io.File;

public class AttachDatabaseTest extends SQLCipherTest {
    @Override
    public boolean execute(SQLiteDatabase database) {
        boolean status;
        String password = "test123";
        File fooDatabase = ZeteticApplication.getInstance().getDatabasePath("foo.db");
        if(fooDatabase.exists()){
            fooDatabase.delete();
        }
        database.execSQL("ATTACH database ? AS encrypted KEY ?", new Object[]{fooDatabase.getAbsolutePath(), password});
        database.execSQL("create table encrypted.t1(a,b);");
        database.execSQL("insert into encrypted.t1(a,b) values(?,?);", new Object[]{"one for the money", "two for the show"});
        int rowCount = QueryHelper.singleIntegerValueFromQuery(database, "select count(*) from encrypted.t1;");
        status = rowCount == 1;
        database.close();
        return status;
    }

    @Override
    public String getName() {
        return "Attach database test";
    }
}
