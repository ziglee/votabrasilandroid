package br.com.smartfingers.votabrasil.task;

import android.os.AsyncTask;
import br.com.smartfingers.votabrasil.MyApplication;
import br.com.smartfingers.votabrasil.activity.QuestionListActivity;
import br.com.smartfingers.votabrasil.entity.Question;
import br.com.smartfingers.votabrasil.service.QuestionService;

public class FetchQuestionsTask extends AsyncTask<String, String, Question[]> {

	private QuestionListActivity activity;
	private Exception exception;
	private QuestionService service;
	
	public FetchQuestionsTask(QuestionService service, QuestionListActivity activity) {
		this.activity = activity;
		this.service = service;
	}
	
	@Override
	protected Question[] doInBackground(String... ids) {
		try {
			return service.getQuestions(MyApplication.uuid);
		} catch (Exception e) {
			exception = e;
		}
		return new Question[0];
	}
	
	@Override
	protected void onPostExecute(Question[] result) {
		activity.executeAfterFetchQuestions(result, exception);
	}
}
