package br.com.smartfingers.votabrasil.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import br.com.smartfingers.votabrasil.entity.Question;

public class QuestionService {

	public static Question getNextQuestion(String uuid) {
		try{
			String url = "http://votabrasilweb.appspot.com/rest/question/next/" + uuid;
			JSONObject object = RestJsonClient.connect(url);
	        Question entity = Question.fromResponse(object.getJSONObject("question"));
	        
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

	public static Question[] getQuestions(String uuid) {
		String url = "http://votabrasilweb.appspot.com/rest/question/titles/" + uuid;
		JSONObject object = RestJsonClient.connect(url);
		try {
			JSONArray records = object.getJSONArray("records");
			int length = records.length();
			Question[] questions = new Question[length];
			for (int x = 0; x < length; x++) {
				questions[x] = Question.fromResponse(records.getJSONObject(x));
			}
			return questions;
		} catch (JSONException e) {
			Log.e("VotaBrasil", "Error fetching questions", e);
		}
        
		return new Question[0];
	}

	public static Question getQuestionById(Long id, String uuid) {
		try{
			String url = "http://votabrasilweb.appspot.com/rest/question/"+ id +"/" + uuid;
			JSONObject object = RestJsonClient.connect(url);
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

	public static Long getVotesCount() {
		try{
			String url = "http://votabrasilweb.appspot.com/rest/vote/count";
			JSONObject object = RestJsonClient.connect(url);
			return object.getLong("count");
        }catch (JSONException e){
        	Log.e("VotaBrasil", "Error finding next question", e);
		}
        return 0L;
	}
}
