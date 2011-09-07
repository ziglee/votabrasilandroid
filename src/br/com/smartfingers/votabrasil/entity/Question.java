package br.com.smartfingers.votabrasil.entity;

import org.json.JSONException;
import org.json.JSONObject;


public class Question {
	
	public Long id;
	public String title;
	public String content;
	
	public static Question fromResponse(JSONObject jsonObject)  throws JSONException {
		JSONObject jsonValue = jsonObject.getJSONObject("question");
		Question entity = new Question();
		entity.title = jsonValue.getString("title");
		entity.content = jsonValue.getString("content");
		entity.id = jsonValue.getLong("id");
		return entity;
	}
}
