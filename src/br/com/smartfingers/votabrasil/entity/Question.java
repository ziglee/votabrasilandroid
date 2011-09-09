package br.com.smartfingers.votabrasil.entity;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class Question implements Serializable {
	
	private static final long serialVersionUID = 542997662544604778L;
	
	public Long id;
	public String title;
	public String content;
	public Integer index;
	public Long yes;
	public Long no;
	public String answer;
	
	public static Question fromResponse(JSONObject jsonObject)  throws JSONException {
		JSONObject jsonValue = jsonObject.getJSONObject("question");
		Question entity = new Question();
		entity.title = jsonValue.getString("title");
		entity.content = jsonValue.getString("content");
		entity.id = jsonValue.getLong("id");
		
		if (jsonValue.has("answer")) {
			entity.answer = jsonValue.getString("answer");
		}
		
		if (jsonValue.has("yes")) {
			entity.yes = jsonValue.getLong("yes");
		}
		
		if (jsonValue.has("no")) {
			entity.no = jsonValue.getLong("no");
		}
		
		return entity;
	}
}
