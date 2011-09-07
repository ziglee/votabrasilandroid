package br.com.smartfingers.votabrasil.task;

import android.os.AsyncTask;
import br.com.smartfingers.votabrasil.activity.VoteActivity;
import br.com.smartfingers.votabrasil.entity.Question;
import br.com.smartfingers.votabrasil.service.QuestionService;

public class FetchNextQuestionTask extends AsyncTask<String, String, Question> {

	private VoteActivity activity;
	
	public FetchNextQuestionTask(VoteActivity activity) {
		this.activity = activity;
	}
	
	@Override
	protected Question doInBackground(String... ids) {
		return QuestionService.getNextQuestion();
	}
	
	@Override
	protected void onPostExecute(Question result) {
		activity.executeAfterAsyncTask(result);
	}
}
