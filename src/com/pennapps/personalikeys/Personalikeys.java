package com.pennapps.personalikeys;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Personalikeys extends Activity {
	double[] scores;
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
		scores = dbHelper.getScores();
		
		dbHelper.close();
		
		setContentView(R.layout.activity_sample2);
		
		TextView p_text = (TextView) findViewById(R.id.textView6);
		TextView e_text = (TextView) findViewById(R.id.textView7);
		TextView r_text = (TextView) findViewById(R.id.textView8);
		TextView m_text = (TextView) findViewById(R.id.textView9);
		TextView a_text = (TextView) findViewById(R.id.textView13);
		TextView swl_text = (TextView) findViewById(R.id.textView11);
		
		if(p_text == null) throw new Error("text view is null");
		p_text.setText(String.valueOf(Math.round(scores[0] - scores[1])));
		e_text.setText(String.valueOf(Math.round(scores[2] - scores[3])));
		r_text.setText(String.valueOf(Math.round(scores[4] - scores[5])));
		m_text.setText(String.valueOf(Math.round(scores[6] - scores[7])));
		a_text.setText(String.valueOf(Math.round(scores[8] - scores[9])));
		swl_text.setText(String.valueOf(Math.round(scores[10] - scores[11])));
		
		
		
	}
	
}
