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
		Question entity = new Question();
		entity.title = jsonObject.getString("title");
		entity.id = jsonObject.getLong("id");
		
		if (jsonObject.has("answer")) {
			entity.answer = jsonObject.getString("answer");
		}
		
		if (jsonObject.has("content")) {
			entity.content = jsonObject.getString("content");
		}
		
		if (jsonObject.has("yes")) {
			entity.yes = jsonObject.getLong("yes");
		} else {
			entity.yes = 0L;
		}
		
		if (jsonObject.has("no")) {
			entity.no = jsonObject.getLong("no");
		} else {
			entity.no = 0L;
		}
		
		if (jsonObject.has("index")) {
			entity.index = jsonObject.getInt("index");
		} else {
			entity.index = 0;
		}
		
		return entity;
	}
}
