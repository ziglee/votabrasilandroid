package br.com.smartfingers.votabrasil.task;

import android.os.AsyncTask;
import br.com.smartfingers.votabrasil.MyApplication;
import br.com.smartfingers.votabrasil.activity.QuestionListActivity;
import br.com.smartfingers.votabrasil.entity.Question;
import br.com.smartfingers.votabrasil.service.QuestionService;

public class FetchQuestionsTask extends AsyncTask<String, String, Question[]> {

	private QuestionListActivity activity;
	private Exception exception;
	
	public FetchQuestionsTask(QuestionListActivity activity) {
		this.activity = activity;
	}
	
	@Override
	protected Question[] doInBackground(String... ids) {
		try {
			return QuestionService.getQuestions(MyApplication.uuid);
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
