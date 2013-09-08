package com.pennapps.personalikeys;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class TextDataOpenHelper extends SQLiteOpenHelper{

	 //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com/pennapps/personalikeys/databases/";
 
    private static String DB_NAME = "Personalikeys";
 
    private SQLiteDatabase myDataBase; 
 
    private final Context context;
    private final ContextWrapper wrapper;
	
	public TextDataOpenHelper(Context context){
		super(context, DB_NAME, null, 1);
		
		this.context = context;
		wrapper = new ContextWrapper(context);
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
        	File filePath = wrapper.getDatabasePath("TextData");
        

        	copyDataBase(filePath);
    	}
 
    }
 
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
 
    	SQLiteDatabase checkDB = null;
 
    	try{
    		File filePath = wrapper.getDatabasePath("TextData");
    		checkDB = SQLiteDatabase.openDatabase(filePath.getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
 
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
    private void copyDataBase(File outPath){
 
    	//Open your local db as the input stream
    	InputStream myInput;
    	try{
    		myInput = context.getAssets().open(DB_NAME);
    	}catch(IOException e){
    		throw new Error("could not open from assets");
    	}
 
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput;
		try {
			myOutput = new FileOutputStream(outPath);
		} catch (FileNotFoundException e1) {
			throw new Error("could not find output file");
		}
    	
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	try{
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
    	}catch(IOException e){
    		throw new Error("problem writin to output");
    	}
    	
    	try{
    	//Close the streams
    	myOutput.flush();
    	}catch(IOException e){
    		throw new Error("problem flushing");
    	}
    	try {
			myOutput.close();
		} catch (IOException e) {
			throw new Error("couldn't close output");
		}
    	try {
			myInput.close();
		} catch (IOException e) {
			throw new Error("couldn't close input");
		}
 
    }
 
   
    public void openDataBaseRead() throws SQLException{
 
    	//Open the database
        File filePath = wrapper.getDatabasePath("TextData");
    	myDataBase = SQLiteDatabase.openDatabase(filePath.getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
    	if(myDataBase == null) throw new Error(filePath.getAbsolutePath());
 
    }
    
    public void openDataBaseWrite() throws SQLException{
    	//Open the database
        File filePath = wrapper.getDatabasePath("TextData");
    	myDataBase = SQLiteDatabase.openDatabase(filePath.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
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
    
    public double[] getScores(){
    	if(myDataBase != null){
    		String[] columns = {"pos_p", "neg_p", "pos_e", "neg_e", "pos_r", "neg_r", "pos_m", "neg_m", "pos_a", "neg_a", "pos_swl", "neg_swl"};
    		Cursor response = myDataBase.query("ScoredInput", columns, "", new String[0], "", "", "");
    		if(response.getColumnCount() == 0) return new double[0];
    		double[] scores = new double[response.getColumnCount()];
    		response.moveToPosition(0);
    		for(int i = 0; i < scores.length; i++){
    			scores[i] = response.getDouble(i);
    		}
    		return scores;
    	}
    	else return null;
    }
    
    public void addScores(String w){
    	if(myDataBase != null){
    		Cursor c = myDataBase.query("Lexica", new String[] {"label"}, 
    				"=" + w, null, null, null, null);
    		String labels = c.getString(0);
    		for(int i = 1; !c.isNull(i); i++){
    			labels += "," + c.getString(i);
    		}
    		ContentValues content = new ContentValues();
    		content.put("score", "score+1");
    		myDataBase.update("Scores",content,"in (" + labels + ")", null);
    	}
    }
 
        // Add your public helper methods to access and get content from the database.
       // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
       // to you to create adapters for your views.

}
