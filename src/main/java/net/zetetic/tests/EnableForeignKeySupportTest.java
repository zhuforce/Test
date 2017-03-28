package main.java.net.zetetic.tests;

import net.sqlcipher.database.SQLiteDatabase;
import main.java.net.zetetic.QueryHelper;

public class EnableForeignKeySupportTest extends SQLCipherTest {
    @Override
    public boolean execute(SQLiteDatabase database) {
        String defaultValue = QueryHelper.singleValueFromQuery(database, "PRAGMA foreign_keys");
        database.rawExecSQL("PRAGMA foreign_keys = ON;");
        String updatedValue = QueryHelper.singleValueFromQuery(database, "PRAGMA foreign_keys");
        return defaultValue.equals("0") && updatedValue.equals("1");
    }

    @Override
    public String getName() {
        return "Enable Foreign Key Support Test";
    }
}
