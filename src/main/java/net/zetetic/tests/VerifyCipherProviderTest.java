package main.java.net.zetetic.tests;

import net.sqlcipher.database.SQLiteDatabase;
import main.java.net.zetetic.QueryHelper;

public class VerifyCipherProviderTest extends SQLCipherTest {

    @Override
    public boolean execute(SQLiteDatabase database) {
        String provider = QueryHelper.singleValueFromQuery(database,
                "PRAGMA cipher_provider;");
        return provider.equals("openssl");
    }

    @Override
    public String getName() {
        return "Verify Cipher Provider Test";
    }
}
