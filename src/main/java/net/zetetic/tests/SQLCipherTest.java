package main.java.net.zetetic.tests;

import android.util.Log;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteDatabaseHook;
import main.java.net.zetetic.ZeteticApplication;

import java.io.File;

public abstract class SQLCipherTest {

    public abstract boolean execute(SQLiteDatabase database);
    public abstract String getName();
    public String TAG = getClass().getSimpleName();
    private TestResult result;

    private SQLiteDatabase database;
    private File databasePath;

    protected void internalSetUp() {
        Log.i(TAG, "Before prepareDatabaseEnvironment");
        ZeteticApplication.getInstance().prepareDatabaseEnvironment();
        Log.i(TAG, "Before getDatabasePath");
        databasePath = ZeteticApplication.getInstance().getDatabasePath(ZeteticApplication.DATABASE_NAME);
        Log.i(TAG, "Before createDatabase");
        database = createDatabase(databasePath);
        Log.i(TAG, "Before setUp");
        setUp();
    }

    public TestResult run() {

        result = new TestResult(getName(), false);
        try {
            internalSetUp();
            result.setResult(execute(database));
            internalTearDown();
        } catch (Exception e) {
            Log.v(ZeteticApplication.TAG, e.toString());
        }
        return result;
    }

    protected void setMessage(String message){
        result.setMessage(message);
    }

    private void internalTearDown(){
        tearDown(database);
        SQLiteDatabase.releaseMemory();
        database.close();
        if(databasePath != null && databasePath.exists()){
            databasePath.delete();
        }
    }
    
    protected SQLiteDatabase createDatabase(File databasePath){
        Log.i(TAG, "Before ZeteticApplication.getInstance().createDatabase");
        return ZeteticApplication.getInstance().createDatabase(databasePath, new SQLiteDatabaseHook() {
            @Override
            public void preKey(SQLiteDatabase database) {
                createDatabasePreKey(database);
            }

            @Override
            public void postKey(SQLiteDatabase database) {
                createDatabasePostKey(database);
            }
        });
    }

    protected void setUp(){};
    protected void tearDown(SQLiteDatabase database){};
    protected void createDatabasePreKey(SQLiteDatabase database){};
    protected void createDatabasePostKey(SQLiteDatabase database){};

    protected void log(String message){
        Log.i(TAG, message);
    }
}
