package br.com.smartfingers.votabrasil.service;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import br.com.smartfingers.votabrasil.entity.Question;

public class TestQuestionService implements QuestionService {

	private static final Question[] questions = new Question[3];
	
	static {
		Question entity = new Question();
        entity.id = 1L;
        entity.index = 1;
        entity.title = "Gorjeta de 20% para garçons, entre 23h e 6h";
        entity.content = "Você é a favor da proposta que autoriza a cobrança de gorjeta no valor de 20% sobre a conta, para garçons, entre 23h e 6h?";
    	entity.yes = 32L;
    	entity.no = 9L;
    	entity.answer = "yes";
		questions[0] = entity;
		
		entity = new Question();
        entity.id = 2L;
        entity.index = 2;
        entity.title = "Estacionamento gratuito nos shoppings, com valor mínimo de compra";
        entity.content = "Você é a favor do projeto que garante estacionamento gratuito nos shoppings, condicionado a um valor mínimo de compra?";
    	entity.yes = 3182L;
    	entity.no = 142L;
		questions[1] = entity;
		
		entity = new Question();
        entity.id = 3L;
        entity.index = 3;
        entity.title = "Modificação na Lei dos Crimes Hediondos";
        entity.content = "Você é a favor do projeto que inclui os atos de corrupção na Lei dos Crimes Hediondos, que aplica punições mais severas aos condenados? alsd alskdjf alsie roiwuer owieuro wieuro wieuroiweur owieuorwiue";
    	entity.yes = 0L;
    	entity.no = 31L;
		questions[2] = entity;
	}
	
	public Question getNextQuestion(String uuid) throws JSONException, ClientProtocolException, IOException {
		for(Question question : questions) {
			if (question.answer == null) {
				return question;
			}
		}
		return null;
	}

	public Boolean postVote(String uuid, Long questionId, String answer) throws ClientProtocolException, JSONException, IOException {
		for(Question question : questions) {
			if (questionId.equals(question.id)) {
				question.answer = answer;
				if (answer.equalsIgnoreCase("yes")) {
					question.yes++;
				} else {
					question.no++;
				}
			}
		}
        return Boolean.TRUE;
	}

	public Question[] getQuestions(String uuid) throws ClientProtocolException, JSONException, IOException {
		return questions;
	}

	public Question getQuestionById(Long id, String uuid) throws ClientProtocolException, JSONException, IOException {
		for(Question question : questions) {
			if (id.equals(question.id)) {
				return question;
			}
		}
		return null;
	}

	public Long getVotesCount() throws ClientProtocolException, JSONException, IOException {
		long count = 0;
		for(Question question : questions) {
			count += question.yes + question.no;
		}
		return count;
	}
}
