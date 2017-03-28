package main.java.net.zetetic.tests;

import net.sqlcipher.database.SQLiteDatabase;
import main.java.net.zetetic.QueryHelper;

public class QueryFloatToStringTest extends SQLCipherTest {
    @Override
    public boolean execute(SQLiteDatabase database) {
        String value = QueryHelper.singleValueFromQuery(database, "SELECT 42.09;");
        return value.equals("42.09");
    }

    @Override
    public String getName() {
        return "Query Float to String";
    }
}
