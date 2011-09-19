package br.com.smartfingers.votabrasil.service;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import br.com.smartfingers.votabrasil.entity.Question;

public interface QuestionService {

	Question getNextQuestion(String uuid) throws JSONException, ClientProtocolException, IOException;
	Boolean postVote(String uuid, Long questionId, String answer) throws ClientProtocolException, JSONException, IOException;
	Question[] getQuestions(String uuid) throws ClientProtocolException, JSONException, IOException;
	Question getQuestionById(Long id, String uuid) throws ClientProtocolException, JSONException, IOException;
	Long getVotesCount() throws ClientProtocolException, JSONException, IOException;
}
