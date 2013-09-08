package com.pennapps.personalikeys;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Personalikeys extends Activity {
	int p;
	int e;
	int r;
	int m;
	int a;
	int swl;
	
	TextDataOpenHelper dbHelper = new TextDataOpenHelper(Personalikeys.this);
	
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		try {
			dbHelper.createDataBase();
		} catch (IOException e1) {
			throw new Error("database failed to open");
		}
		dbHelper.openDataBaseRead();
		
		p = dbHelper.getScore("p");
		e = dbHelper.getScore("e");
		r = dbHelper.getScore("r");
		m = dbHelper.getScore("m");
		a = dbHelper.getScore("a");
		swl = dbHelper.getScore("swl");
		
		dbHelper.close();
		
		setContentView(R.layout.activity_personalikeys);
		
		TextView p_text = (TextView) findViewById(R.id.textView6);
		TextView e_text = (TextView) findViewById(R.id.textView7);
		TextView r_text = (TextView) findViewById(R.id.textView8);
		TextView m_text = (TextView) findViewById(R.id.textView9);
		TextView a_text = (TextView) findViewById(R.id.textView13);
		TextView swl_text = (TextView) findViewById(R.id.textView11);
		
		if(p_text == null) throw new Error("text view is null");
		p_text.setText(String.valueOf(p));
		e_text.setText(String.valueOf(e));
		r_text.setText(String.valueOf(r));
		m_text.setText(String.valueOf(m));
		a_text.setText(String.valueOf(a));
		swl_text.setText(String.valueOf(swl));
		
		
		
	}
	
}
