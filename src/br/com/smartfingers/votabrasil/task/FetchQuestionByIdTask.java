package br.com.smartfingers.votabrasil.task;

import android.os.AsyncTask;
import br.com.smartfingers.votabrasil.MyApplication;
import br.com.smartfingers.votabrasil.activity.QuestionListActivity;
import br.com.smartfingers.votabrasil.entity.Question;
import br.com.smartfingers.votabrasil.service.QuestionService;

public class FetchQuestionByIdTask extends AsyncTask<Long, String, Question> {

	private QuestionListActivity activity;
	private Exception exception;
	
	public FetchQuestionByIdTask(QuestionListActivity activity) {
		this.activity = activity;
	}
	
	@Override
	protected Question doInBackground(Long... ids) {
		try {
			return QuestionService.getQuestionById(ids[0], MyApplication.uuid);
		} catch (Exception e) {
			exception = e;
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Question result) {
		activity.executeAfterFetchQuestionById(result, exception);
	}
}
