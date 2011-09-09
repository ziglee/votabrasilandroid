package br.com.smartfingers.votabrasil.service;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import br.com.smartfingers.votabrasil.entity.Question;

public class QuestionService {

	public static Question getNextQuestion() {
		try{
			JSONObject object = RestJsonClient.connect("http://votabrasilweb.appspot.com/rest/question/next");
	        Question entity = Question.fromResponse(object);
	        
	        if (entity.yes == null) {
	        	entity.yes = 0L;
	        }
	        
	        if (entity.no == null) {
	        	entity.no = 0L;
	        }
			return entity;
        }catch (JSONException e){
        	Log.e("VotaBrasil", "Error finding next question", e);
		}
        return null;
	}

	public static Question postVote(String uuid, Long questionId, String answer) {
		try{
			StringBuilder url = new StringBuilder("http://votabrasilweb.appspot.com/rest/vote");
			url.append("/" + questionId);
			url.append("/" + uuid);
			url.append("/" + answer);
			JSONObject object = RestJsonClient.connect(url.toString());
	        return Question.fromResponse(object);
        }catch (JSONException e){
        	Log.e("VotaBrasil", "Error finding next question", e);
		}
        return null;
	}
}
