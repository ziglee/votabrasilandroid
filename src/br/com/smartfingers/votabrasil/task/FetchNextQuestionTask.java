package br.com.smartfingers.votabrasil.task;

import android.os.AsyncTask;
import br.com.smartfingers.votabrasil.MyApplication;
import br.com.smartfingers.votabrasil.activity.NextQuestionFetchable;
import br.com.smartfingers.votabrasil.entity.Question;
import br.com.smartfingers.votabrasil.service.QuestionService;

public class FetchNextQuestionTask extends AsyncTask<String, String, Question> {

	private NextQuestionFetchable activity;
	
	public FetchNextQuestionTask(NextQuestionFetchable activity) {
		this.activity = activity;
	}
	
	@Override
	protected Question doInBackground(String... uuids) {
		return QuestionService.getNextQuestion(MyApplication.uuid);
	}
	
	@Override
	protected void onPostExecute(Question result) {
		activity.executeAfterFetchNextQuestion(result);
	}
}
