package br.com.smartfingers.votabrasil.activity;

import br.com.smartfingers.votabrasil.entity.Question;

public interface NextQuestionFetchable {
	void executeAfterFetchNextQuestion(Question result, Exception exception);
}
