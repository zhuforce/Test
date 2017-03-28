package main.java.net.zetetic.tests;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import main.java.net.zetetic.UserEntity;
import main.java.net.zetetic.ZeteticApplication;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteStatement;

/**
 * QueryTenThousandDataTest
 * 
 * It collected two questions:
 * 1.In the Samsung Galaxy Note 5, Initially insert 14000 rows of data, the first time to query all the data, it is normal.
 * When I execute more than one query all the data, the exception occurs.
 * The exception follows:
 * Fatal signal 11 (SIGSEGV), code 1, fault addr 0x70bef5dc in tid 26296 (AsyncTask #2)
 * 
 * 2.Regardless of any device, it consume 4 to 8 seconds when query 14000 rows of data.
 * When I do not use SQLCipher to query 14000 rows of data, it takes only 200 to 400 milliseconds
 * 
 * Would you be able to tell me how to solve the second problem which query slowly? Sincere thanks.
 * @author force
 *
 */
public class QueryTenThousandDataTest extends SQLCipherTest {
	    @Override
	    public boolean execute(SQLiteDatabase database) {
	    	
	    	createTable(database, true); 
	    	
//	    	insertData(database);
	        
	        Cursor cursor = database.rawQuery("SELECT * FROM UserInfo", new String[]{});
	        log("Query ten thousand row data cursor move");
	        long beforeCursorMove = System.nanoTime();
            List<UserEntity> userList = new ArrayList<UserEntity>();
            try {
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    UserEntity userEntity = readEntity(cursor, 0);
                    userList.add(userEntity);
                }
                long afterCursorMove = System.nanoTime();
                log(String.format("Query thousand row data userList size:%d", userList.size()));
                log(String.format("Complete cursor operation time:%d ms",
                        toMilliseconds(beforeCursorMove, afterCursorMove)));
            } finally {
                cursor.close();
            }
            
	        return true;
	    }

	    @Override
	    public String getName() {
	        return "query ten thousand rows Test";
	    }

	    @Override
	    protected SQLiteDatabase createDatabase(File databasePath) {
	    	databasePath = ZeteticApplication.getInstance().getDatabasePath("my_user.db");
	        return SQLiteDatabase.openOrCreateDatabase(databasePath, "123", null);
	    }
	    
	    private long toMilliseconds(long before, long after){
	        return (after - before)/1000000L;
	    }
	    
	    
	    public void createTable(SQLiteDatabase db, boolean ifNotExists) {
	        String constraint = ifNotExists? "IF NOT EXISTS ": "";
	        db.execSQL("CREATE TABLE " + constraint + "'UserInfo' (" + 
	                "'aaaa' INTEGER PRIMARY KEY AUTOINCREMENT ," + 
	                "'bbbb' INTEGER NOT NULL UNIQUE," + 
	                "'cccc' INTEGER NOT NULL ," + 
	                "'dddd' TEXT NOT NULL ," + 
	                "'eeee' TEXT NOT NULL ," +  
	                "'ffff' TEXT NOT NULL ," +  
	                "'gggg' TEXT NOT NULL ," +  
	                "'hhhh' TEXT NOT NULL ," +  
	                "'iiii' TEXT NOT NULL ," +  
	                "'jjjj' INTEGER NOT NULL ," + 
	                "'kkkk' INTEGER NOT NULL ," + 
	                "'llll' INTEGER NOT NULL ," + 
	                "'mmmm' INTEGER NOT NULL ," + 
	                "'nnnn' TEXT NOT NULL );"); 
	    }
	    
	    public void insertData(SQLiteDatabase database) {
	    
	    	try {
                String sql = "INSERT INTO UserInfo ( aaaa, bbbb, cccc, dddd, eeee, ffff, gggg, hhhh, iiii, jjjj, kkkk, llll, mmmm, nnnn ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";  
                database.beginTransaction();  
                SQLiteStatement stmt = database.compileStatement(sql); 
                
                for (int index = 0; index < 14000; index++) {
                	 stmt.bindLong(1, Long.valueOf(index + ""));  
                	 stmt.bindDouble(2, index);  
                	 stmt.bindDouble(3, 1);
                	 stmt.bindString(4, "tom");
                	 stmt.bindString(5, "lucy");
                	 stmt.bindString(6, "force");
                	 stmt.bindString(7, "http");
                	 stmt.bindString(8, "0201111");
                	 stmt.bindString(9, "email");
                	 stmt.bindDouble(10, 222); 
                	 stmt.bindDouble(11, 1); 
                	 stmt.bindDouble(12, 333); 
                	 stmt.bindDouble(13, 444); 
                	 stmt.bindString(14, "short"); 
                	 
                	 stmt.execute();  
                	 stmt.clearBindings();  
                }
	    	} finally {
                database.setTransactionSuccessful();  
                database.endTransaction();
	    	}
	    }
	    
	    public UserEntity readEntity(Cursor cursor, int offset) {
	        UserEntity entity = new UserEntity( //
	                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), 
	                cursor.getInt(offset + 1), 
	                cursor.getInt(offset + 2), 
	                cursor.getString(offset + 3), 
	                cursor.getString(offset + 4), 
	                cursor.getString(offset + 5), 
	                cursor.getString(offset + 6), 
	                cursor.getString(offset + 7), 
	                cursor.getString(offset + 8), 
	                cursor.getInt(offset + 9), 
	                cursor.getInt(offset + 10), 
	                cursor.getInt(offset + 11), 
	                cursor.getInt(offset + 12), 
	                cursor.getString(offset + 13) 
	        );
	        return entity;
	    }

}
