package br.com.smartfingers.votabrasil.service;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import br.com.smartfingers.votabrasil.entity.Question;

public class QuestionService {

	public static Question getNextQuestion() {
		try{
			JSONObject object = RestJsonClient.connect("http://votabrasilweb.appspot.com/rest/question/next");
	        return Question.fromResponse(object);
        }catch (JSONException e){
        	Log.e("VotaBrasil", "Error finding next question", e);
		}
        return null;
	}
}
