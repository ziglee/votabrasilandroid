package br.com.smartfingers.votabrasil.service;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.smartfingers.votabrasil.entity.Question;

public class QuestionService {

	private static final String BASE_URL = "http://votabrasilweb.appspot.com/rest";
	
	public static Question getNextQuestion(String uuid) throws JSONException, ClientProtocolException, IOException {
		String url = BASE_URL + "/question/next/" + uuid;
		JSONObject object = RestJsonClient.connect(url);
        Question entity = Question.fromResponse(object.getJSONObject("question"));
        
        if (entity.yes == null) {
        	entity.yes = 0L;
        }
        
        if (entity.no == null) {
        	entity.no = 0L;
        }
		return entity;
	}

	public static Boolean postVote(String uuid, Long questionId, String answer) throws ClientProtocolException, JSONException, IOException {
		StringBuilder url = new StringBuilder(BASE_URL + "/vote");
		url.append("/" + questionId);
		url.append("/" + uuid);
		url.append("/" + answer);
		JSONObject object = RestJsonClient.connect(url.toString());
        return object.getBoolean("success");
	}

	public static Question[] getQuestions(String uuid) throws ClientProtocolException, JSONException, IOException {
		String url = BASE_URL + "/question/titles/" + uuid;
		JSONObject object = RestJsonClient.connect(url);
		JSONArray records = object.getJSONArray("records");
		int length = records.length();
		Question[] questions = new Question[length];
		for (int x = 0; x < length; x++) {
			questions[x] = Question.fromResponse(records.getJSONObject(x));
		}
		return questions;
	}

	public static Question getQuestionById(Long id, String uuid) throws ClientProtocolException, JSONException, IOException {
		String url = BASE_URL + "/question/"+ id +"/" + uuid;
		JSONObject object = RestJsonClient.connect(url);
        Question entity = Question.fromResponse(object);
        
        if (entity.yes == null) {
        	entity.yes = 0L;
        }
        
        if (entity.no == null) {
        	entity.no = 0L;
        }
		return entity;
	}

	public static Long getVotesCount() throws ClientProtocolException, JSONException, IOException {
		String url = BASE_URL + "/vote/count";
		JSONObject object = RestJsonClient.connect(url);
		return object.getLong("count");
	}
}
