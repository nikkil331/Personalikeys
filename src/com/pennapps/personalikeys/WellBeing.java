package com.pennapps.personalikeys;

import java.io.IOException;

import com.google.api.client.auth.oauth.OAuthParameters;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.prediction.*;
import com.google.api.services.prediction.Prediction.Builder;
import com.google.api.services.prediction.Prediction.Hostedmodels.Predict;
import com.google.api.services.prediction.model.*;

public class WellBeing {
	// Set up the HTTP transport and JSON factory
	HttpTransport httpTransport = new NetHttpTransport();
	JsonFactory jsonFactory = new JacksonFactory();
	HttpRequestInitializer httpRequestInitialzer = new OAuthParameters();
	
	String text;
	private final String projnum = "554730971451";
	private final String projname = "storied-glazing-331";
	private final String modelname = "permaswl";
	
	public WellBeing(String text){
		System.out.println("did it get here");

		this.text = text;

		Prediction prediction = new Prediction(httpTransport,
				jsonFactory, httpRequestInitialzer);
		Input i = new Input().set("csvInstance", text);
		try {
			System.out.println(prediction.trainedmodels().predict(projnum,modelname,i).execute().toPrettyString());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
}
