package com.pennapps.personalikeys;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class TextDataOpenHelper extends SQLiteOpenHelper{

	 //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com/pennapps/personalikeys/databases/";
 
    private static String DB_NAME = "TextData";
 
    private SQLiteDatabase myDataBase; 
 
    private final Context context;
	
	public TextDataOpenHelper(Context context){
		super(context, DB_NAME, null, 1);
		
		this.context = context;
	}
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	
	 /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException{
 
    	boolean dbExist = checkDataBase();
 
    	if(dbExist) {/* do nothing - database already exist*/}
    	
    	else{ 
    		//By calling this method an empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
        	this.getReadableDatabase();
 
        	try {
 
    			copyDataBase();
 
    		} catch (IOException e) {
 
        		throw new Error("Error copying database");
 
        	}
    	}
 
    }
 
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
 
    	SQLiteDatabase checkDB = null;
 
    	try{
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    	}catch(SQLiteException e){
 
    		//database does't exist yet.
 
    	}
 
    	if(checkDB != null){
 
    		checkDB.close();
 
    	}
 
    	return checkDB != null ? true : false;
    }
 
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
 
    	//Open your local db as the input stream
    	InputStream myInput = context.getAssets().open(DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    }
 
    public void openDataBase() throws SQLException{
 
    	//Open the database
        String path = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
 
    }
 
    @Override
	public synchronized void close() {
 
    	    if(myDataBase != null)
    		    myDataBase.close();
 
    	    super.close();
 
	}
    
    public void addRow(ContentValues values){
    	if(myDataBase != null){
    		 myDataBase.insert("ScoredInput", null, values);
    	}

    }
    
    public double[] getScores() throws Exception{
    	if(myDataBase != null){
    		String[] columns = {"pos_p", "neg_p", "pos_e", "neg_e", "pos_r", "neg_r", "pos_m", "neg_m", "pos_a", "neg_a", "pos_swl", "neg_swl"};
    		Cursor response = myDataBase.query("ScoredInput", columns, "_id=1", new String[0], "", "", "");
    		double[] scores = new double[response.getColumnCount()];
    		for(int i = 0; i < scores.length; i++){
    			scores[i] = response.getDouble(i);
    		}
    		return scores;
    	}
    	else{
    		throw new Exception("Database not opened");
    	}
    }
 
        // Add your public helper methods to access and get content from the database.
       // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
       // to you to create adapters for your views.

}
